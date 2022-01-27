Vue.component("managerDeliveryRequests", {

    data() {
        return {
            orders: [],
            sort: {
                key: '',
                isAsc: false
            },
            restaurant: {},
        }
    },
    mounted() {
        axios.get('/managers/restaurant')
            .then(response => {
                this.restaurant = response.data
                axios.get('/orders/getByRestaurant/' + this.restaurant.name)
                    .then(response => {
                        this.orders = response.data
                    })
            })
    },
    methods: {
        changeOrderStatusToInDelivery(o){
            axios.put('/orders/changeStatus/inDelivery', JSON.stringify(o))
                .then(response => {
                    console.log(response)
                    location.reload()
                });
        }
    },
    computed:{},
    template: `
	<div class="reg">
		<div class="container">
		    <p id="title" class="text-center">DELIVERY REQUESTS</p>
		</div> </br>
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
				<button class="btn_manager" type="button" v-on:click="changeOrderStatusToInDelivery(order)">Accept Request</button></br>
				<hr style="height:10px">
			</div>
	</div>
	`
});