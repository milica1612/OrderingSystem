Vue.component("createOrder", {

    data() {
        return {
			restaurants: [],
			restaurant: null,
			searchParam: "",
			types: [],
			filter: "",
			btn_txt_name: "Sort By Name",
			btn_txt_loc: "Sort By Loaction",
			btn_txt_rate: "Sort By Rating",
			sort: {
				key: '',
				isAsc: false
			},
        }
    },
	mounted () {
		axios.get('/restaurants/getAll')
          	.then(response => {
				 if (response.data != null) {
					this.restaurants = response.data
				    console.log(this.restaurants);
			 }
		   })
			axios.get('/restaurants/getTypes').then(
				response =>{
					this.types = response.data
				}
			).catch()
	},
	computed: {
	},
	methods: {
		viewRestaurant : function (restaurant) {
		window.location.href = "#/restaurantPage?name=" + restaurant.name;
		},
		searchByName(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else{
				axios.get('restaurants/getByName/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByType(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else{
				axios.get('restaurants/getByType/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByLocation(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else{
				axios.get('restaurants/getByLocation/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByRating(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else if (this.searchParam >=1 && this.searchParam<=5){
				axios.get('restaurants/getByRating/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
			else{
				alert("Please enter a number between 1 and 5!")
			}
		},
		filtrateOpen(){
			axios.put('/restaurants/filtrate/open', JSON.stringify(this.restaurants)).then(response => {
				this.restaurants = response.data
				console.log(response)
			})
		},
		filtrateByType(filter){
			axios.put('/restaurants/filtrate/type/' + filter, JSON.stringify(this.restaurants)
			).then(response => {
				this.restaurants = response.data
				console.log(response)

			}).catch(err => {
				console.log(err)
			});
		},
		sortedClass (key) {
			return this.sort.key === key ? `sorted ${this.sort.isAsc ? 'asc' : 'desc' }` : '';
		},
		sortBy (key) {
			this.sort.isAsc = this.sort.key === key ? !this.sort.isAsc : false;
			this.sort.key = key;
			this.sortedItems()
		},
		sortedItems () {
			const list = this.restaurants.slice();
			console.log(list);
			if (this.sort.key !="") {
				list.sort((a, b) => {
					a = a[this.sort.key]
					b = b[this.sort.key]
					return (a === b ? 0 : a > b ? 1 : -1) * (this.sort.isAsc ? 1 : -1)
				});
			}
			this.restaurants = list
			if (this.sort.isAsc){
			this.btn_txt_name = "Sort By Name desc"
				this.btn_txt_loc = "Sort By Location desc"
				this.btn_txt_rate = "Sort By Rating desc"
			}else{
				this.btn_txt_name = "Sort By Name asc"
				this.btn_txt_loc = "Sort By Location asc"
				this.btn_txt_rate = "Sort By Rating asc"
			}
			this.$forceUpdate()
		}
	},
	computed:{},
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">Choose a restaurant:</p>
			 </div>
		<div id="search_id" class="container">
			<table>
			<tr>
			<td><input type="search"  placeholder="Search..." v-model="searchParam"></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByName">Search By Name</button></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByType">Search By Type</button></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByLocation">Search By Location</button></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByRating">Search By Rating</button></td>
			</tr>
			<tr>
			<td colspan="2"><label>Filtrate</label></td>
			<td></td>
			<td colspan="2"><label>Filtrate By Type</label></td>
			<tr>
			<td colspan="2">
			<button class="btn_filtrate" type="button" v-on:click="filtrateOpen">Open restaurants</button>
			</td>
			<td></td>
			<td colspan="2">
			<select  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="filter"
			 @change="filtrateByType(filter)">
                     <option v-for="type in types" :value="type">{{type}}</option>
			</select>	
			</td>
			</tr>
			</table>
			<table>
			<tr>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('name')" @click="sortBy('name')">{{this.btn_txt_name}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('location')" @click="sortBy('name')">{{this.btn_txt_loc}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('rating')" @click="sortBy('name')">{{this.btn_txt_rate}}</button>
			</td>
			</tr>
			</table>
		</div>
		<br>
      				<div class="container" id="restaurant_view" v-for="r in restaurants" :key="r.name" v-on:click="viewRestaurant(r)">
						<img v-bind:src= "r.logo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 20px">
       					<label class="restaurant_name">{{r.name}}</label></br>
			        	<label class="restaurant_data">{{r.type}}</label></br> 
						<label class="restaurant_data">{{r.location.address.street}} {{r.location.address.streetNumber}}</label></br>
						<label class="restaurant_data">{{r.location.address.city}} {{r.location.address.zipcode}}</label></br>  
						<label class="restaurant_data">{{r.rating}}  &#10027;</label></br>
						<label class="restaurant_data" v-if="r.isOpen == true">OPEN</label>
						<label class="restaurant_data" v-else>CLOSE</label>
						<hr style="height:10px">
			        </div>
	</div>
	`});
  