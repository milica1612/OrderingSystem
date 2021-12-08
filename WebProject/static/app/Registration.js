Vue.component("Registration", {

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
	<div id="reg">
		<div class="container">
			<p id="title" class="text-center" >REGISTRATION</p>
			<div id="form">
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Email address</label>
				  <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">
				</div>
			</div>
		</div>
	</div>
	
	`});