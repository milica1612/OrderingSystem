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
	        <li><a v-if="role == 'ADMIN' | role == 'MANAGER' | role == 'CUSTOMER' | role == 'DELIVERER'" href="#/userProfile">My Profile</a></li>
	        <li><a v-if="role == 'ADMIN' | role == 'MANAGER' | role == 'CUSTOMER' | role == 'DELIVERER'" href="#/login" @click ="logOut">Log Out</a></li>
	      </ul>
	    </div>
  	</nav>
	`
	});