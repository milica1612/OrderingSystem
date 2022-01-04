Vue.component("createOrder", {

    data() {
        return {
			restaurants: [],
			restaurant: null
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
			window.location.href = "#/restaurantPage?name=" + restaurant.name;
		}
	},
	computed:{},
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">Choose a restaurant:</p>
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
  