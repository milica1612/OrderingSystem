Vue.component("registration", {

    data() {
        return {
			username: null,
			password: null,
            password2: null,
            firstName: null,
            lastName: null,
            gender: null,
            dateOfBirth: null
        }
    },
	methods: {
		
	},
	
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">REGISTRATION</p>
			<div id="form" class="container">
				<div>
				  <label>First Name</label>
				  <input type="text" class="form-control">
				</div>
				<div>
				  <label>Last Name</label>
				  <input type="text" class="form-control">
				</div>
				<div>
				  <label>Gender</label>
			  	  <select class="form-select form-select-sm" aria-label=".form-select-sm example">
					<option selected></option>
				  	<option value="1">Male</option>
				  	<option value="2">Female</option>
				  	<option value="3">Other</option>
				  </select>			
				</div>
				<div>
				  <label>Date Of Birth</label>
				 <input type="date" data-date-format="mm/dd/yyyy">
				</div>
				<div>
				  <label>Username</label>
				  <input type="text" class="form-control">
				</div>
				<div>
				  <label>Password</label>
				  <input type="password" class="form-control">
				</div>
				<div>
				  <label>Confirm Password</label>
				  <input type="password" class="form-control">
				</div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button">REGISTER</button>
 				</div>
			
		</div>
	</div>
	
	`});