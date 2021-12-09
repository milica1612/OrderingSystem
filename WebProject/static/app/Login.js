Vue.component("login", {

    data() {
        return {
			username: null,
			password: null
        }
    },
	methods: {
		
	},
	
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">LOG IN</p>
			<div id="form_login" class="container">
			    <div>
				  <label>Username</label>
				  <input type="text" class="form-control">
			    </div>
			    <div>
				  <label>Password</label>
				  <input type="password" class="form-control">
			    </div>
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<div class="text-center">
  					<button id="btn_login" class="btn btn-warning" type="button">LOG IN</button>
  				</div>
 			</div>
 			<div class="text-center" id="acc_div">
 				Don't have account? <a href="Registration.js">Sign up</a>
 			</div>
		</div>
	</div>
	`
	});