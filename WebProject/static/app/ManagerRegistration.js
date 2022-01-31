Vue.component("managerRegistration", {

    data() {
        return {
            userType: "1",
            username: "",
            password: "",
            password2: "",
            firstName: "",
            lastName: "",
            gender: "",
            dateOfBirth: "",
            passwords_not_same: "",
            not_filled: "",
            taken: "",
            name: ""
        }
    },
    methods: {
        register() {

            if(this.userType != "" && this.username != "" && this.password != "" && this.password2 != "" && this.firstName != "" &&
                this.lastName != ""&& this.dateOfBirth != "" && this.gender !="") {
                if(this.password == this.password2) {
                    let params = {
                        userType: this.userType,
                        username: this.username,
                        password: this.password,
                        name: this.firstName,
                        lastName: this.lastName,
                        gender: this.gender,
                        dateOfBirth: this.dateOfBirth
                    }
                        axios.post('managers/registration', JSON.stringify(params)
                        ).then(response => {
                            console.log(response);

                            this.name = localStorage.getItem("restaurant")
                            axios.put('/managers/restaurant/' + this.name, JSON.stringify(this.username)).then(
                                response => {
                                    console.log(response)
                                    localStorage.removeItem("restaurant");
									 this.$router.push("/");
                                })
                        }).catch(err => {
                            console.log(err);
                        });

                }
            }
        },
    },
	mounted () {
        if(localStorage.getItem("role") != 'ADMIN'){
            this.$router.push("/")
        }
    },
    computed:{
        passwordsNotSame(){
            if(this.password != this.password2) {
                this.passwords_not_same = true
                return true
            }
            this.passwords_not_same = false
            return false
        },
        notFilled(){
            if(this.userType == "" || this.username == "" || this.password == "" || this.password2 == "" || this.firstName == "" ||
                this.lastName == ""|| this.dateOfBirth == "" || this.gender =="") {
                this.not_filled = true
                return true
            }
            this.not_filled = false
            return false
        },
        isUsernameTaken() {
            axios.get('users/' + this.username).then(response => {
                this.taken = response.data
                console.log(response)

            }).catch(err => {
                console.log(err)
            });
            if(this.taken == ""){
                return false
            }
            return true

        },
        notOK(){
            if(this.passwords_not_same == true || this.not_filled == true || this.taken != ""){
                return true
            }
            return false
        }
    },

    template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">MANAGER REGISTRATION</p>
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
				  	<option value="0" >Male</option>
				  	<option value="1">Female</option>
				  	<option value="2">Other</option>
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
				    <p class="error" v-if="isUsernameTaken">Username already taken!</p>
			    </div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button" @click="register" :disabled="notOK">REGISTER</button>
 				</div>
			
		</div>
	</div>
	
	`});