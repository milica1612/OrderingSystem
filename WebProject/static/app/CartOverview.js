Vue.component("cartOverview", {

    data() {
        return {
			cart: {
			},
			current_item: {
				name: "",
				price: 0.0,
				description: "",
				quantity: "",
				photo: "",
				type: "",
				not_filled: false,
				oldName: "",
				img_name: "",
				restaurant: "",
			},
			quantityItem: 1,
			order: {},
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
	methods: {
		setItem(cartItm){
			this.current_item = cartItm.item,
			this.quantityItem = cartItm.quantity
		},
		saveNewQuantity(){
			if(this.quantityItem != null){
				let params = {
					item: {
						name: this.current_item.name,
						price: this.current_item.price,
						description: this.current_item.description,
						quantity: this.current_item.quantity,
						photo: this.current_item.photo,
						type: this.current_item.type,
						restaurant: this.current_item.restaurant,				
					},
					quantity: this.quantityItem				
					}
				if(this.quantityItem < 1){
					alert("Quantity has to be more then 0!");
				}else{
					axios.post('/carts/editItemQuantity', JSON.stringify(params))
					.then(response => {
						console.log(response);
						location.reload()
					}).catch(err => {
	                    console.log(err);
	                });
				}
			}
		},
		removeItem(){
			if(this.quantityItem != null){
				let params = {
					item: {
						name: this.current_item.name,
						price: this.current_item.price,
						description: this.current_item.description,
						quantity: this.current_item.quantity,
						photo: this.current_item.photo,
						type: this.current_item.type,
						restaurant: this.current_item.restaurant,				
					},
					quantity: this.quantityItem				
					}
				axios.post('/carts/removeItem', JSON.stringify(params))
				.then(response => {
					console.log(response);
					location.reload()
				}).catch(err => {
                    console.log(err);
                });
			}
			if(this.cart.items.lenght == 0){
				localStorage.removeItem("cartRestaurant")
			}
		},
		createOrder(){
				let params = {
						cart: this.cart,
					}
				axios.post('/orders/create', JSON.stringify(params))
				.then(response => {
					localStorage.removeItem("cartRestaurant")
					this.order = response.data
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
		    <p id="title" class="text-center">MY CART</p>
		</div> </br>
		<div class="container" id="cart_view" v-for="cartItem in cart.items" :key="cartItem.name">
			<div>
				<img v-bind:src= "cartItem.item.photo" alt="" id="item_logo" class="rounded float-start" style="margin-top: 15px"></br>
				<label  class="restaurant_name">{{cartItem.item.name}}</label></br>
				<label  class="restaurant_data">{{cartItem.item.price}} din.</label></br>
				<label  class="restaurant_data">{{cartItem.item.description}}</label></br>
				<label  class="restaurant_data">{{cartItem.item.quantity}}</label></br>
				<label  class="restaurant_data">QUANTITY: {{cartItem.quantity}}</label>
			</div>
			<button class="btn_manager" type="button" data-bs-toggle="modal" data-bs-target="#modal" v-on:click="setItem(cartItem)">Edit</button></br>
			<hr style="height:10px">
		</div>
		<div class="container">
			<hr style="height:3px">
			<div class="text-center">
				<button id="btn_order" class="btn btn-warning" type="button"  @click="createOrder">ORDER</button>
			</div>
			<label id="cart_total" class="rounded float-end">TOTAL: {{cart.total}} din.</label> 
		</div>
		
			<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLiveLabel" style="display: none;" aria-hidden="true">
          				<div class="modal-dialog" id="modal_content">
            				<div class="modal-content">
                					<button type="button" style="margin-left: 430px" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              						<div class="modal-body">
 										<img v-bind:src= "current_item.photo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 5px"></br>
										<div id="item_content">
										<label  class="restaurant_name">{{current_item.name}}</label></br>
										<label  class="restaurant_name">{{current_item.price}} din.</label></br>
										<label  class="restaurant_name">{{current_item.description}}</label></br>
										</div>
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Quantity: </label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="number" class="form control-plaintext" min="1" v-model="quantityItem">
											</div>
  										</div>
										<div class="modal-footer">
	                						<button type="button" class="btn_modal" data-bs-dismiss="modal" @click="removeItem">Cancel Item</button>
	                						<button type="submit" class="btn_modal" :key="button_text" @click="saveNewQuantity">Save New Quantity</button>
	              						</div>
              					</div>
              					
            				</div>
          				</div>
			</div>
	</div>
	`
});