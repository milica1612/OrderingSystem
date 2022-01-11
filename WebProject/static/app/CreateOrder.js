Vue.component("createOrder", {

    data() {
        return {
			restaurants: [],
			restaurant: null,
			searchParam: ""
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
	},
	methods: {
		viewRestaurant : function (restaurant) {
			window.location.href = "#/restaurant?id=" + restaurant.name;
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
			<td><button class="btn_search" type="button" v-on:click="searchByName">Search By Name</button></td>
			<td><button class="btn_search" type="button" v-on:click="searchByLocation">Search By Location</button></td>
			<td><button class="btn_search" type="button" v-on:click="searchByRating">Search By Rating</button></td>
			</tr>
			</table>
		</div>
		<br>
      				<div class="container" id="restaurant_view" v-for="r in restaurants" :key="r.name" v-on:click="viewRestaurant(r)">
						<img v-bind:src= "r.logo" alt="" id="restaurant_logo" class="rounded float-start">
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
  