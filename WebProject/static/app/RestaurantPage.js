Vue.component("restaurantPage", {

    data() {
        return {
			restaurant : null
        }
    },
	mounted () {
		axios.get('/restaurantPage/' + this.$route.query.name)
          .then(response => {
				 	this.restaurant = response.data 
					console.log(this.restaurant);
		   })
	},
	methods: {
		
	},
	computed:{},
	template: `
	<div class="reg">
		<div class="container" id="restaurant_info">
			<img v-bind:src= "restaurant.logo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 5px">
			<label  class="restaurant_name">{{restaurant.name}}</label></br>
			<label class="restaurant_data">{{restaurant.type}}</label></br> 
			<label class="restaurant_data" v-if="restaurant.isOpen == true">OPEN</label>
			<label class="restaurant_data" v-else>CLOSE</label></br>	
			<label class="restaurant_data">{{restaurant.location.address.street}} {{restaurant.location.address.streetNumber}}</label></br>
			<label class="restaurant_data">{{restaurant.location.address.city}} {{restaurant.location.address.zipcode}}</label></br>  
			<label class="restaurant_data">{{restaurant.rating}} &#10027;</label></br>
		</div>
	</div>
	`});
 