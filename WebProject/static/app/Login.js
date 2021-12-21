Vue.component("login", {

    data() {
        return {
			username: "",
			password: "",
			error_message: false,
			not_filled: false
        }
    },
	methods: {
		login() {
			if(this.username == "" || this.password == ""){
				this.not_filled = true
				this.error_message = false
			}else{
				this.not_filled = false
			}
			if(this.not_filled == false){
				let params = {
						username : this.username,
						password : this.password
				}
				axios.post('users/login',JSON.stringify(params)
				).then(response => {
					console.log(response);
					if (response.data == "") {
						//alert("Wrong username or password");
						this.error_message = true;
					}else {
						localStorage.setItem("username", response.data.username);
						localStorage.setItem("role", response.data.userType);
						this.error_message = false;
						this.$router.push("/userProfile");
					}
				}).catch(err => {
					console.log(err);
				});


			}
		},
	},
	computed:{
	},
	
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">LOG IN</p>
			<div id="form_login" class="container">
			    <div>
				  <label>Username</label>
				  <input type="text" class="form-control" v-model="username">
			    </div>
			    <div>
				  <label>Password</label>
				  <input type="password" class="form-control" v-model="password">
			    </div>
			</div>
			<div class="text-center" id="err_div">
				<p class="error" v-if="error_message">Invalid credentials!</p>
				<p class="error" v-if="not_filled">Please fill all fields</p>
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<div class="text-center">
  					<button id="btn_login" class="btn btn-warning" type="button" @click="login">LOG IN</button>
  				</div>
 			</div>
 			<div class="text-center" id="acc_div">
 				Don't have account? <a href="#/registration">Sign up</a>
 			</div>
		</div>
	</div>
	`
	});