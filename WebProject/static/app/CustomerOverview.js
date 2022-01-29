Vue.component("customerOverview", {

    data() {
        return {
            users: [],
            filtratedUsers: [],
            filter: "",
            filter2: "",
            sort: {
                key: '',
                isAsc: false
            },
            searchParam: '',
            restaurant: {}
        }
    },
    mounted () {
        axios.get('/managers/restaurant')
            .then(response => {
                this.restaurant = response.data
                axios.get('/orders/getCustomers/' + this.restaurant.name)
                    .then(response => {
                        this.users = response.data
                    })
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
        },
        searchUsersByName(){
            if(this.searchParam == ""){
                axios.get('/users/getAllUsers')
                    .then(response => {
                        if (response.data != null) {
                            this.users = response.data
                            console.log(this.users);
                        }
                    })
            }
            else{
                axios.get('users/getByName/' + this.searchParam).then(response => {
                    this.users = response.data
                    console.log(response)

                }).catch(err => {
                    console.log(err)
                });
            }
        },
        searchUsersByLastName(){
            if(this.searchParam == ""){
                axios.get('/users/getAllUsers')
                    .then(response => {
                        if (response.data != null) {
                            this.users = response.data
                            console.log(this.users);
                        }
                    })
            }
            else{
                axios.get('users/getByLastName/' + this.searchParam).then(response => {
                    this.users = response.data
                    console.log(response)

                }).catch(err => {
                    console.log(err)
                });
            }
        },
        searchUsersByUsername(){
            if(this.searchParam == ""){
                axios.get('/users/getAllUsers')
                    .then(response => {
                        if (response.data != null) {
                            this.users = response.data
                            console.log(this.users);
                        }
                    })
            }
            else{
                axios.get('users/getByUsername/' + this.searchParam).then(response => {
                    this.users = response.data
                    console.log(response)

                }).catch(err => {
                    console.log(err)
                });
            }
        },
        filtrateUsers(filter){
            axios.put('users/filtrate/' + filter, JSON.stringify(this.users)
            ).then(response => {
                this.users = response.data
                console.log(response)

            }).catch(err => {
                console.log(err)
            });
        },
        filtrateByCustomerType(filter2){
            axios.put('customers/filtrateByType/' + filter2, JSON.stringify(this.users)
            ).then(response => {
                this.users = response.data
                console.log(response)

            }).catch(err => {
                console.log(err)
            });
        }
    },
    template:`
	<div class="reg">
		<div class="container">
		    <p id="title" class="text-center">LIST OF CUSTOMERS</p>
	</div>	
	<div>
		<table id="table_id" class="center">
	    <thead class="thead-dark">
	      <tr class="clickable-row">
	        <th :class="sortedClass('username')" @click="sortBy('username')">Username</th>
	        <th :class="sortedClass('name')" @click="sortBy('name')">First Name</th>
	        <th :class="sortedClass('lastName')" @click="sortBy('lastName')">Last Name</th>
	        <th :class="sortedClass('customerType')" @click="sortBy('customerType')">Customer Type</th>
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
	        <td class="td_class">{{ user.customerType.name }}</td>
			<td class="td_class">{{user.points}}</td>
	      </tr>
	    </tbody>
	  </table>
	</div>
	</div>
    `
});