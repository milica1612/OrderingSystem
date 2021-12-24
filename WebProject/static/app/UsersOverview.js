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
	<div id="search_id" class="container">
		<table>
			<tr>
		  		<td><input type="search" placeholder="Search..."></td>
				<td><button class="btn_search" type="button">Search By Name</button></td>
				<td><button class="btn_search" type="button">Search By Last Name</button></td>
				<td><button class="btn_search" type="button">Search By Username</button></td>
			</tr>
		
			<tr><td colspan="2">
			<label>Filtrate</label>
			<select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example">
				<option value="0">ADMIN</option>
				<option value="1">MANAGER</option>
				<option value="2">CUSTOMER</option>
				<option value="3">DELIVERER</option>s
			</select>
			</td>
			<td colspan="2">
			<label>Filtrate customers</label>
			<select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example">
				<option value="0">GOLD</option>
			</select>
			</td>
			</tr>
		</table>	
  	</div>
	<div>
		<table id="table_id" class="center">
	    <thead class="thead-dark">
	      <tr class="clickable-row">
	        <th>Username</th>
	        <th>First Name</th>
	        <th>Last Name</th>
	        <th>User Type</th>
	        <th>Customer Points</th>
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
			<td class="td_class">{{user.points}}</td>
	      </tr>
	    </tbody>
	  </table>
	</div>
	</div>
    `
});