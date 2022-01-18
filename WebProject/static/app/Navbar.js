Vue.component("navbar", {

    data() {
        return {
			role: ""
        }
    },
	mounted: function () {
		this.role = window.localStorage.getItem('role');
		console.log(this.role);
	},
	methods: {
		refresh(){
			this.$forceUpdate()
		},
		logOut(){
			localStorage.removeItem("role");
			localStorage.removeItem("username");
			axios.get('users/logout')
		}
	},
	template: `
	 <nav id = "nav">
	    <div class="nav-wrapper" >
	      <ul id="nav-mobile" class="right hide-on-med-and-down">
	        <li><a v-if="role != 'ADMIN' && role != 'MANAGER' && role != 'CUSTOMER' && role != 'DELIVERER'" href="#/login">Sign In</a></li>
			<li><a v-if="role == 'ADMIN'" href="#/employeeRegistration">Register Employee</a></li>
			<li><a v-if="role == 'ADMIN'" href="#/newRestaurant">Register Restaurant</a></li>
			<li><a v-if="role == 'ADMIN'" href="#/usersOverview">All users</a></li>
			<li><a v-if="role == 'CUSTOMER'" href="#/createOrder">Create Order</a></li>
	        <li><a v-if="role == 'ADMIN' | role == 'MANAGER' | role == 'CUSTOMER' | role == 'DELIVERER'" href="#/userProfile">My Profile</a></li>
	        <li><a v-if="role == 'ADMIN' | role == 'MANAGER' | role == 'CUSTOMER' | role == 'DELIVERER'" href="#/changePassword">Change password</a></li>
	        <li><a v-if="role == 'ADMIN' | role == 'MANAGER' | role == 'CUSTOMER' | role == 'DELIVERER'" href="#/login" @click ="logOut">Log Out</a></li>
	      </ul>
	    </div>
  	</nav>
	`
	});