Vue.component("userProfile", {

    data() {
        return {
            user: {},
            disabled_edit: true,
            enabled_edit: false,
        }
    },
    methods: {
        enable() {
            this.disabled_edit = false
            this.enabled_edit = true
        },
        save() {
            this.disabled_edit = true
            this.enabled_edit = false

            if (this.user.username != "" && this.user.name != "" && this.user.lastName != "" && this.user.dateOfBirth != "" && this.user.gender != "") {
                let params = {
                    username: this.user.username,
                    name: this.user.name,
                    lastName: this.user.lastName,
                    gender: this.user.gender,
                    dateOfBirth: this.user.dateOfBirth
                }
                axios.put('users/edit', JSON.stringify(params)
                ).then(response => {
                    console.log(response);
                    if (response.data == "") {

                    } else {
                        localStorage.setItem("username", response.data.username);
                    }
                }).catch(err => {
                    console.log(err);
                });
            }
        }
    },
    mounted() {
        axios.get('/users/logged')
            .then(response => {
                this.user = response.data
            })
    },
    template:`
    <div class="reg">
		<div class="container">
		    <p id="title" class="text-center">PERSONAL INFORMATION</p>
			<div id="form" class="container">
				<div>
				  <label>First Name</label>
				  <input type="text" class="form-control" v-model="user.name" :disabled="disabled_edit">
				</div>
				<div>
				  <label>Last Name</label>
				  <input type="text" class="form-control" v-model="user.lastName" :disabled="disabled_edit">
				</div>
				<div>
				  <label>Gender</label>
			  	  <select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="user.gender" :disabled="disabled_edit">
				  	<option value="0" >Male</option>
				  	<option value="1">Female</option>
				  	<option value="2">Other</option>
				  </select>			
				</div>
				<div>
				  <label>Date Of Birth</label>
				 <input type="date" data-date-format="mm/dd/yyyy" v-model="user.dateOfBirth" :disabled="disabled_edit">
				</div>
				<div>
				  <label>Username</label>
				  <input type="text" class="form-control" v-model="user.username" :disabled="disabled_edit">
				</div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button" @click="enable" v-if="disabled_edit">EDIT</button>
  					<button id="btn" class="btn btn-warning" type="button" @click="save" v-if="enabled_edit">SAVE</button>
 				</div>
		</div>
	</div>
    `
    });