Vue.component("changePassword", {

    data() {
        return {
            user: {},
            old_password: "",
            new_password: "",
            new_password2: "",
            not_filled: true,
            password_correct: false,
            passwords_not_same: false
        }
    },
    methods: {
        save() {
            axios.put('users/changePassword', JSON.stringify(this.new_password)
            ).then(response => {
                console.log(response);
                this.$router.push("/");
            }).catch(err => {
                console.log(err);
            });
        }
    },
    mounted() {
        if(localStorage.getItem("username") == null){
            this.$router.push("/")
        }else {
            axios.get('/users/logged')
                .then(response => {
                    this.user = response.data
                })
        }
    },
    computed:{
        notFilled(){
            if(this.old_password == "" || this.new_password == "" || this.new_password2 == "") {
                this.not_filled = true
                return true
            }
            this.not_filled = false
            return false
        },
        oldPasswordNotOK(){
            if(this.old_password == this.user.password){
                this.password_correct = true
                return false
            }
            this.password_correct = false
            return true
        },
        notOK(){
            if(this.not_filled == true || this.password_correct == false || this.passwords_not_same == true){
                return true
            }
            return false
        },
        passwordsNotSame(){
            if(this.new_password != this.new_password2) {
                this.passwords_not_same = true
                return true
            }
            this.passwords_not_same = false
            return false
        },
    },
    template:`
    <div class="reg">
		<div class="container">
		    <p id="title" class="text-center">CHANGE PASSWORD</p>
			<div id="form" class="container">
				<div>
				  <label>Current Password</label>
				  <input type="password" class="form-control" v-model="old_password" :disabled="disabled_edit">
				</div>
				<div>
				  <label>New Password</label>
				  <input type="password" class="form-control" v-model="new_password" :disabled="disabled_edit">
				</div>
				<div>
				  <label>Repeat New Password</label>
				  <input type="password" class="form-control" v-model="new_password2" :disabled="disabled_edit">
				</div>
				<div class="text-center" id="err_div">
				    <p class="error" v-if="notFilled">All fields should be filled!</p>
				     <p class="error" v-if="oldPasswordNotOK">Current password is incorrect!</p>
				     <p class="error" v-if="passwordsNotSame">Password and confirm password should match!</p>
			    </div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button" @click="save" :disabled="notOK">SAVE</button>
 				</div>
		</div>
	</div>
    `
});