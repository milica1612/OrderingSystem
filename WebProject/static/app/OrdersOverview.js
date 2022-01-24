Vue.component("ordersOverview", {
	
	data() {
        return {
		orders: [],
		btn_txt_name: "Sort By Name",
		btn_txt_price: "Sort By Price",
		btn_txt_date: "Sort By Date",	
	}
    },
	mounted() {
		axios.get('/orders/getByCustomer')
          .then(response => {
				 	this.orders = response.data
		   })
	},
	methods: {	
		cancelOrder(ord){
				let params = {
					code: ord.code,
					cart: ord.cart,
					restaurant: ord.restaurant,
					dateAndTime: ord.dateAndTime,
					orderStatus: ord.orderStatus,
					customer: ord.customer,
				}
				axios.post('/orders/cancelOrder', JSON.stringify(params))
				.then(response => {
					console.log(response);
					location.reload()
				}).catch(err => {
                    console.log(err);
                });
		}
	},
	computed:{},
	template: `
	<div class="reg">
		<div class="container">
		    <p id="title" class="text-center">MY ORDERS</p>
		</div> </br>
		<div id="search_id" class="container">
			<table>
			<tr>
			<td><input type="search"  placeholder="Search..."></td>
			<td><button class="btn_search_res" type="button">Search By Restaurant</button></td>
			<td><button class="btn_search_res" type="button">Search By Price</button></td>
			<td><button class="btn_search_res" type="button">Search By Date</button></td>
			</tr>
			<tr>
			<td colspan="2"><label>Filtrate by order status</label></td>
			<td colspan="2"><label>Filtrate By Type</label></td>
			</tr>
			</table>
			<table>
			<tr>
			<td>
				<button class="btn_sort" type="button" >{{this.btn_txt_name}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button"  >{{this.btn_txt_price}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button">{{this.btn_txt_date}}</button>
			</td>
			</tr>
			</table>
		</div>
		<br>
			<div class="container" id="restaurant_view" v-for="order in orders" :key="order.code">
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
				</div> </br></br>
				<label class="restaurant_name">TOTAL: {{order.cart.total}} din;  </label> 
				<button class="btn_manager" type="button" data-bs-toggle="modal" data-bs-target="#modal" v-if="order.orderStatus=='PROCESSING'" v-on:click="cancelOrder(order)">Cancel Order</button></br>
				<hr style="height:10px">
			</div>
	</div>
	`
});