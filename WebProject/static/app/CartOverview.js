Vue.component("cartOverview", {

    data() {
        return {
			cart: {
			}
		}
    },
	mounted() {
		axios.get('/carts/getByCustomer')
          .then(response => {
				 	this.cart = response.data
					let name = this.cart.customer 
					localStorage.setItem("cart", name)
					console.log(name);
		   })
	},
	methods: {},
	computed:{},
	template: `
	<div class="reg">
		<div class="container">
		    <p id="title" class="text-center">MY CART</p>
		</div> </br>
		<div class="container" id="cart_view" v-for="cartItem in cart.items" :key="cartItem.name">
			<img v-bind:src= "cartItem.item.photo" alt="" id="item_logo" class="rounded float-start" style="margin-top: 5px"></br>
			<label  class="restaurant_name">{{cartItem.item.name}}</label></br>
			<label  class="restaurant_data">{{cartItem.item.price}} din.</label></br>
			<label  class="restaurant_data">{{cartItem.item.description}}</label></br>
			<label  class="restaurant_data">{{cartItem.item.quantity}}</label>
			<hr style="height:10px">
		</div>
		<hr style="height:5px">
	</div>
	`
});