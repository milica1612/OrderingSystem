Vue.component("restaurantPage", {

    data() {
        return {
			restaurant: {
				name: ""
			},
			manager_restaurant: {
				name: ""
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
			},
			quantityItem: 1
		}

    },
	mounted () {
		axios.get('/restaurantPage/' + this.$route.query.name)
          .then(response => {
				 	this.restaurant = response.data
					let name = this.restaurant.name 
					localStorage.setItem("restaurant", name)
					console.log(name);
					
		   })
		if(localStorage.getItem("role") == 'MANAGER'){
			axios.get('/managers/restaurant')
				.then(response => {
					this.manager_restaurant = response.data

				})
		}
	},
	methods: {
		setItem(itm){
			this.current_item = itm,
			this.oldName = itm.name
		},
		setFiles: function(event){
            const file = event.target.files[0];
            this.createBase64Image(file);
            this.photo = URL.createObjectURL(file);
        },
        createBase64Image(file){
            const reader= new FileReader();

            reader.onload = (e) =>{
                let img = e.target.result;
                this.photo = img;
            }
            reader.readAsDataURL(file);
        },
		save(){
			if(this.current_item.name != "" && this.current_item.price != 0.0 && this.current_item.type != ""){
		            let params = {
						name: this.current_item.name,
						price: this.current_item.price,
						description: this.current_item.description,
						quantity: this.current_item.quantity,
						photo: this.photo,
						type: this.current_item.type,				
					}
					axios.put('/restaurantPage/editItem/' + localStorage.getItem("restaurant") + '/' 
								+ this.oldName, JSON.stringify(params))
						.then(response => {
							 console.log(response);
            }).catch(err => {
                    console.log(err);
                });
            }
			
		},
		addToCart(){
			if(this.quantityItem != null){
				let params = {
						name: this.current_item.name,
						price: this.current_item.price,
						description: this.current_item.description,
						quantity: this.current_item.quantity,
						photo: this.photo,
						type: this.current_item.type,				
					}
				axios.post('/carts/addItemToCart/' + this.quantityItem, JSON.stringify(params))
				.then(response => {
					console.log(response);
					alert(this.quantityItem + "x" + this.current_item.name + " added to your cart!");
				}).catch(err => {
                    console.log(err);
                });
			}
		}
	},
	computed:{
		notFilled(){
          if(this.name == "" || this.price == 0.0 || this.type == "" || this.photo == ""){
		             this.not_filled = true
                return true
           }
            this.not_filled = false
            return false
        },
		isManagerLogged(){
			if(localStorage.getItem('role') == 'MANAGER'){
					if (this.restaurant.name == this.manager_restaurant.name) {
						return true
					}
			}
			return false
		},
		isCustomerLogged(){
			if(localStorage.getItem('role') == 'CUSTOMER'){
				return true
			}
			return false
		}
	},
	template: `
	<div class="reg">
		<div class="container" id="restaurant_info">
			<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			  <button class="btn_manager" type="button" v-if="isManagerLogged">Add new item</button>
			</div>
			<img v-bind:src= "restaurant.logo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 5px">
			<label  class="restaurant_name">{{restaurant.name}}</label></br>
			<label class="restaurant_data">{{restaurant.type}}</label></br> 
			<label class="restaurant_data" v-if="restaurant.isOpen == true">OPEN</label>
			<label class="restaurant_data" v-else>CLOSE</label></br>	
			<label class="restaurant_data">{{restaurant.location.address.street}} {{restaurant.location.address.streetNumber}}</label></br>
			<label class="restaurant_data">{{restaurant.location.address.city}} {{restaurant.location.address.zipcode}}</label></br>  
			<label class="restaurant_data">{{restaurant.rating}} &#10027;</label></br>
		</div> </br>
		<div class="container" id="restaurant_items">
			<div class="row">
				<div class="col-2" v-for="item in restaurant.items" :key="item.name">
					<img v-bind:src= "item.photo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 5px"></br>
					<label  class="restaurant_name">{{item.name}}</label></br>
					<label  class="restaurant_name">{{item.price}} din.</label></br>
					<label  class="restaurant_name">{{item.description}}</label></br>
					
					<button type="button" class="btn_search_res" data-bs-toggle="modal" data-bs-target="#modal" v-on:click="setItem(item)" v-if="isManagerLogged">
			       		Edit Item
					</button>
					
					<button type="button" class="btn_search_res" data-bs-toggle="modal" data-bs-target="#modal2" v-on:click="setItem(item)" v-if="isCustomerLogged">
			       		Order Item
					</button>
					
        			<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLiveLabel" style="display: none;" aria-hidden="true">
          				<div class="modal-dialog" id="modal_content">
            				<div class="modal-content">
                					<button type="button" style="margin-left: 430px" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              						<div class="modal-body">
 										<div class="mb-3 row">
											<label class="col-sm-3 col-form-label">Name</label>
											<div class="col-sm-9" style="margin-top: -6px">
									  			<input type="text" class="form-control" v-model="current_item.name">
											</div>
  										</div>
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Price</label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="number" class="form control-plaintext" v-model="current_item.price" min="1">
											</div>
  										</div>
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Type</label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="text" class="form-control" v-model="current_item.type">
											</div>
  										</div>
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Description</label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="text" class="form-control" v-model="current_item.description">
											</div>
  										</div>
  										<div class="mb-3 row">							
											<label  class="col-sm-3 col-form-label">Quantity</label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="text" class="form-control" v-model="current_item.quantity">
											</div>
  										</div>
										<label class="col-sm-3 col-form-label">Photo</label>
											<img v-bind:src= "current_item.photo" alt="" id="restaurant_logo" class="rounded float-start" style="width: 60px; height: 60px"></br>
				  							<input type="file" class="form-control" accept="image/*" @change="setFiles" v-model="img_name">
										</div>
										<div class="text-center" id="err_div">
										    <p class="error" v-if="notFilled">Name, type, price and photo field should be filled!</p>
											<p class="error" v-else></p>
									    </div>
									<div class="modal-footer">
                						<button type="button" class="btn_modal" data-bs-dismiss="modal">Close</button>
                						<button type="submit" class="btn_modal" @click="save" :key="button_text">Save changes</button>
              						</div>
              					</div>
              					
            				</div>
          				</div>

					<div class="modal fade" id="modal2" tabindex="-1" aria-labelledby="exampleModalLiveLabel" style="display: none;" aria-hidden="true">
          				<div class="modal-dialog" id="modal_content">
            				<div class="modal-content">
                					<button type="button" style="margin-left: 430px" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              						<div class="modal-body">
 										<img v-bind:src= "item.photo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 5px"></br>
										<label  class="restaurant_name">{{current_item.name}}</label></br>
										<label  class="restaurant_name">{{current_item.price}} din.</label></br>
										<label  class="restaurant_name">{{current_item.description}}</label></br>
										
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Quantity: </label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="number" class="form control-plaintext" min="1" v-model="quantityItem">
											</div>
  										</div>
										<div class="modal-footer">
	                						<button type="button" class="btn_modal" data-bs-dismiss="modal">Close</button>
	                						<button type="submit" class="btn_modal" @click="addToCart" :key="button_text">Add to card</button>
	              						</div>
              					</div>
              					
            				</div>
          				</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	`});
 