Vue.component("registration", {

    data() {
        return {
			username: "",
			password: "",
            password2: "",
            firstName: "",
            lastName: "",
            gender: "",
            dateOfBirth: "",
            not_filled: ""
        }
    },
	methods: {
        register() {

            if(this.username != "" && this.password != "" && this.password2 != "" && this.firstName != "" &&
                this.lastName != ""&& this.dateOfBirth != "" && this.gender !="") {
                if(this.password == this.password2) {
                    let params = {
                        username: this.username,
                        password: this.password,
                        firstName: this.firstName,
                        lastName: this.lastName,
                        gender: this.gender,
                        dateOfBirth: this.dateOfBirth
                    }
                    axios.post('customers/registration', JSON.stringify(params)
                    ).then(response => {
                        console.log(response);
                        if (response.data == "") {

                        } else {

                            //this.$router.push("events")
                        }
                    }).catch(err => {
                        console.log(err);
                    });
                }
            }
        },
	},
    computed:{
        passwordsNotSame(){
            if(this.password != this.password2) {
                return true
            }
            return false
        },
        notFilled(){
            if(this.username == "" || this.password == "" || this.password2 == "" || this.firstName == "" ||
                this.lastName == ""|| this.dateOfBirth == "" || this.gender =="") {
                return true
            }
            return false
        },
    },
	
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">REGISTRATION</p>
			<div id="form" class="container">
				<div>
				  <label>First Name</label>
				  <input type="text" class="form-control" v-model="firstName">
				</div>
				<div>
				  <label>Last Name</label>
				  <input type="text" class="form-control" v-model="lastName">
				</div>
				<div>
				  <label>Gender</label>
			  	  <select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="gender">
				  	<option value="1" >Male</option>
				  	<option value="2">Female</option>
				  	<option value="3">Other</option>
				  </select>			
				</div>
				<div>
				  <label>Date Of Birth</label>
				 <input type="date" data-date-format="mm/dd/yyyy" v-model="dateOfBirth">
				</div>
				<div>
				  <label>Username</label>
				  <input type="text" class="form-control" v-model="username">
				</div>
				<div>
				  <label>Password</label>
				  <input type="password" class="form-control" v-model="password">
				</div>
				<div>
				  <label>Confirm Password</label>
				  <input type="password" class="form-control" v-model="password2">
				</div>
				<div class="text-center" id="err_div">
				    <p class="error" v-if="passwordsNotSame">Password and confirm password should match!</p>
				    <p class="error" v-if="notFilled">All fields should be filled!</p>
			    </div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button" @click="register">REGISTER</button>
 				</div>
			
		</div>
	</div>
	
	`});