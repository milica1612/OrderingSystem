Vue.component("usersOverview", {

    data() {
        return {
           	users: [],
 			sort: {
        		key: '',
        		isAsc: false
      		}
        }
	},
	
	mounted () {
          axios.get('/users/getAllUsers')
          	.then(response => {
				 if (response.data != null) {
					this.users = response.data
				    console.log(this.users);
			 }
		   })
    },
	computed: {
		sortedItems () {
      		const list = this.users.slice();
			console.log(list); 
      		if (this.sort.key !="") {
        		list.sort((a, b) => {
          		a = a[this.sort.key]
          		b = b[this.sort.key]
          		return (a === b ? 0 : a > b ? 1 : -1) * (this.sort.isAsc ? 1 : -1)
        	});
      	}
      return list;
    }
	},
	
	methods: {
		sortedClass (key) {
     		return this.sort.key === key ? `sorted ${this.sort.isAsc ? 'asc' : 'desc' }` : '';
    	},
    	sortBy (key) {
      		this.sort.isAsc = this.sort.key === key ? !this.sort.isAsc : false;
      		this.sort.key = key;
    	}
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
	        <th :class="sortedClass('username')" @click="sortBy('username')">Username</th>
	        <th :class="sortedClass('name')" @click="sortBy('name')">First Name</th>
	        <th :class="sortedClass('lastName')" @click="sortBy('lastName')">Last Name</th>
	        <th :class="sortedClass('userType')" @click="sortBy('userType')">User Type</th>
	        <th :class="sortedClass('points')" @click="sortBy('points')">Customer Points</th>
	      </tr>
	    </thead>
		<tbody>
	      <tr
	        v-for="user in sortedItems"
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