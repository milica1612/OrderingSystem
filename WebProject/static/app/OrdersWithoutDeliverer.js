Vue.component("ordersWithoutDeliverer", {
	
	data() {
        return {
		orders: [],
		btn_txt_res: "Sort By Restaurant",
		btn_txt_price: "Sort By Price",
		btn_txt_date: "Sort By Date",	
		searchParam: "",
		searchPriceFrom: "",
		searchPriceTo: "",
		searchDateFrom: "",
		searchDateTo: "",
		sort: {
				key: '',
				isAsc: false
			},
		types: [],
	}
    },
	mounted() {
		if(localStorage.getItem("role") != 'DELIVERER'){
			this.$router.push("/")
		}else {
			axios.get('/orders/getWithoutDeliverer')
				.then(response => {
					this.orders = response.data
				})
		}
	},
	methods: {	
		deliverOrder(ord){
				let params = {
					code: ord.code,
					cart: ord.cart,
					restaurant: ord.restaurant,
					dateAndTime: ord.dateAndTime,
					orderStatus: ord.orderStatus,
					customer: ord.customer,
					total: ord.total
				}
				axios.put('/orders/changeStatus/waitingForDeliveryApproval', JSON.stringify(params))
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
		    <p id="title" class="text-center">ALL ORDERS</p>
		</div> </br>
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
				<button class="btn_manager" type="button" data-bs-toggle="modal" data-bs-target="#modal" v-on:click="deliverOrder(order)">Deliver Order</button></br>
				<hr style="height:10px">
			</div>
	</div>
	`
});