Vue.component("delivererOrderOverview", {
	
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
		axios.get('/orders/getByDeliverer')
          .then(response => {
				 	this.orders = response.data
		   })
		axios.get('/restaurants/getTypes').then(
				response =>{
					this.types = response.data
				}
			).catch()
	},
	methods: {	
		searchByRestaurant(){
			if(this.searchParam == ""){
				axios.get('/orders/getByDeliverer')
					.then(response => {
						if (response.data != null) {
							this.orders = response.data
						}
					})
			}
			else{
				axios.get('orders/getByRestaurant/' + this.searchParam).then(response => {
					this.orders = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByDate(){
			if(this.searchDateFrom == "" && this.searchDateTo == ""){
				axios.get('/orders/getByDeliverer')
					.then(response => {
						if (response.data != null) {
							this.orders = response.data
						}
					})
			}
			else{
				if(this.searchDateFrom == ""){
					let params = {
					dateTo: this.searchDateTo
					}
					axios.post('orders/getByDate', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
				else if(this.searchDateTo == ""){
					let params = {
						dateFrom: this.searchDateFrom,
					}
					axios.post('orders/getByDate', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
				else {
					let params = {
					dateFrom: this.searchDateFrom,
					dateTo: this.searchDateTo
					}
					axios.post('orders/getByDate', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
			}
		},
		searchByPrice(){
			if(this.searchPriceFrom == "" && this.searchPriceTo == ""){
				axios.get('/orders/getByDeliverer')
					.then(response => {
						if (response.data != null) {
							this.orders = response.data
						}
					})
			}
			else{
				if(this.searchPriceFrom == ""){
					let params = {
					priceTo: this.searchPriceTo
					}
					axios.post('orders/getByPrice', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
				else if(this.searchPriceTo == ""){
					let params = {
						priceFrom: this.searchPriceFrom,
					}
					axios.post('orders/getByPrice', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
				else {
					let params = {
					priceFrom: this.searchPriceFrom,
					priceTo: this.searchPriceTo
					}
					axios.post('orders/getByPrice', JSON.stringify(params))
					.then(response => {
						this.orders = response.data
						console.log(response)
	
					}).catch(err => {
						console.log(err)
					});
				}
			}
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
			const list = this.orders.slice();
			console.log(list);
			if (this.sort.key !="") {
				list.sort((a, b) => {
					a = a[this.sort.key]
					b = b[this.sort.key]
					return (a === b ? 0 : a > b ? 1 : -1) * (this.sort.isAsc ? 1 : -1)
				});
			}
			this.orders = list
			if (this.sort.isAsc){
			this.btn_txt_res = "Sort By Restaurant desc"
				this.btn_txt_price = "Sort By Price desc"
				this.btn_txt_date = "Sort By Date desc"
			}else{
				this.btn_txt_res = "Sort By Restaurant asc"
				this.btn_txt_price = "Sort By Price asc"
				this.btn_txt_date = "Sort By Date asc"
			}
			this.$forceUpdate()
		},
		
		filtrateByType(filter){
			axios.put('/orders/filtrate/type/' + filter, JSON.stringify(this.orders)
			).then(response => {
				this.orders = response.data
				console.log(response)

			}).catch(err => {
				console.log(err)
			});
		},
		filtrateByStatus(filter2){
			axios.put('/orders/filtrate/status/' + filter2, JSON.stringify(this.orders)
			).then(response => {
				this.orders = response.data
				console.log(response)

			}).catch(err => {
				console.log(err)
			});
		},
		setOrderToDelivered(ord){
				let params = {
					code: ord.code,
					cart: ord.cart,
					restaurant: ord.restaurant,
					dateAndTime: ord.dateAndTime,
					orderStatus: ord.orderStatus,
					customer: ord.customer,
				}
				axios.put('/orders/changeStatus/delivered', JSON.stringify(params))
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
			<td><input type="search"  placeholder="Search by restaurant..." v-model="searchParam"></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByRestaurant">Search By Restaurant</button></td>
			</tr>
			<tr>
			<td><input type="number"  placeholder="From..." min="1" v-model="searchPriceFrom"></td>
			<td><input type="number"  placeholder="To..." min="1" v-model="searchPriceTo"></td>
			<td><button class="btn_search_res" type="button"  v-on:click="searchByPrice">Search By Price</button></td>
			</tr>
			<tr>
			<td><input type="date" data-date-format="mm/dd/yyyy" v-model="searchDateFrom"></td>
			<td><input type="date" data-date-format="mm/dd/yyyy" v-model="searchDateTo"></td>
			<td><button class="btn_search_res" type="button" v-on:click="searchByDate">Search By Date</button></td>
			</tr>
			<tr>
			<td><label>Filtrate by order status</label>
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="filter2"
			@change="filtrateByStatus(filter2)">
				<option value="PROCESSING">PROCESSING</option>
				<option value="PREPARING">PREPARING</option>
				<option value="WAITING_FOR_DELIVERY">WAITING_FOR_DELIVERY</option>
				<option value="WAITING_FOR_DELIVERY_APPROVAL">WAITING_FOR_DELIVERY_APPROVAL</option>
				<option value="IN_TRANSPORT">IN_TRANSPORT</option>
				<option value="DELIVERED">DELIVERED</option>
				<option value="CANCELED">CANCELED</option>
			</select>
			</td>
			<td><label>Filtrate By Type</label>
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="filter"
			 @change="filtrateByType(filter)">
                     <option v-for="type in types" :value="type">{{type}}</option>
			</select>	
			</td>
			</tr>
			</table>
			<table>
			<tr>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('restaurant')" @click="sortBy('restaurant')">{{this.btn_txt_res}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button"  :class="sortedClass('total')" @click="sortBy('total')" >{{this.btn_txt_price}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button"  :class="sortedClass('dateAndTime')" @click="sortBy('dateAndTime')">{{this.btn_txt_date}}</button>
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
				<button class="btn_manager" type="button" data-bs-toggle="modal" data-bs-target="#modal" v-on:click="setOrderToDelivered(order)">Delivered</button></br>
				<hr style="height:10px">
			</div>
	</div>
	`
});