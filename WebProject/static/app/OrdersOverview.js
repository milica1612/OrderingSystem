Vue.component("ordersOverview", {
	
	data() {
        return {
		orders: []
}
    },
	mounted() {
		axios.get('/orders/getByCustomer')
          .then(response => {
				 	this.orders = response.data
		   })
	},
	methods: {
	},
	computed:{},
	template: `
	<div class="reg">
		<div class="container" id="restaurant_info">
					<div v-for="order in orders" :key="order.code">
						<label  class="restaurant_name">CODE: {{order.code}};  </label>
						<label  class="restaurant_name">RESTAURANT: {{order.restaurant}};  </label>
						<label  class="restaurant_name">TIME: {{order.dateAndTime}};  </label>
						<label  class="restaurant_name">STATUS: {{order.orderStatus}};  </label>
						</br>
						<div class="container" id="cart_view" v-for="cartItem in order.cart.items" :key="cartItem.item.name">
							<img v-bind:src= "cartItem.item.photo" alt="" id="item_logo" class="rounded float-start" style="margin-top: 15px"></br>
							<label  class="restaurant_name">{{cartItem.item.name}}</label></br>
							<label  class="restaurant_data">{{cartItem.item.price}} din.</label></br>
							<label  class="restaurant_data">{{cartItem.item.description}}</label></br>
							<label  class="restaurant_data">{{cartItem.item.quantity}}</label></br>
							<label  class="restaurant_data">QUANTITY: {{cartItem.quantity}}</label>
						</div> </br>
						<label class="restaurant_name">TOTAL: {{order.cart.total}} din;  </label> 
						<hr style="height:10px">
					</div>
		</div>
	</div>
	`
});