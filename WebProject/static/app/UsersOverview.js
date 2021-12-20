Vue.component("usersOverview", {

    data() {
        return {
           users: null
        }
	},
	
	mounted () {
          axios.get('/users/getAllUsers')
          	.then(response => {
				 if (response.data != null) {
					this.users = response.data
			 }
		   })
    },
	methods: {
		
	},
	
	template:`
	<div class="reg">
		<div class="container">
		    <p id="title" class="text-center">LIST OF USERS</p>
	</div>
	<div>
		<table id="table_id" class="center">
	    <thead class="thead-dark">
	      <tr class="clickable-row">
	        <th>Username</th>
	        <th>First Name</th>
	        <th>Last Name</th>
	        <th>User Type</th>
	      </tr>
	    </thead>
		<tbody>
	      <tr
	        v-for="user in users"
	        :key="user.username"
	      >
	        <td class="td_class">{{ user.username }}</td>
	        <td class="td_class">{{ user.name }}</td>
	        <td class="td_class">{{ user.lastName}}</td>
	        <td class="td_class">{{ user.userType }}</td>
	      </tr>
	    </tbody>
	  </table>
	</div>
	</div>
    `
});