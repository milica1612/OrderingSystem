Vue.component("homePage", {

    data() {
        return {
			restaurants: [],
			restaurant: null,
			searchName: "",
			searchType: "",
			searchLocation: "",
			searchRating: "",
			selected: {},
			types: [],
			user: {},
			filter: "",
			btn_txt_name: "Sort By Name",
			btn_txt_loc: "Sort By Location",
			btn_txt_rate: "Sort By Rating",
			sort: {
				key: '',
				isAsc: false
			},
			comment: "",
			rate: 5,
			can_comment: false
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
			axios.get('/restaurants/getTypes').then(
				response =>{
					this.types = response.data
				}
			).catch()
		axios.get('/users/logged')
			.then(response => {
				this.user = response.data
			})
	},
	computed: {
		isNotFilled(){
			if(this.comment == ""){
				return true
			}else if(this.rate < 1 || this.rate > 5){
				return  true
			}else{
				return false
			}
		},
		isCustomerLogged() {
			if (localStorage.getItem('role') == 'CUSTOMER') {
				return true
			}
			return false
		},
		isAdminLogged(){
			if(localStorage.getItem('role') == 'ADMIN'){
				return true
			}
			return false
		}
	},
	methods: {
		viewRestaurant : function (restaurant) {
		window.location.href = "#/restaurantPage?name=" + restaurant.name;
		},
		searchByName(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else{
				axios.get('restaurants/getByName/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		search(){
			if(this.searchName == "" && this.searchLocation == "" && this.searchRating == "" && this.searchType == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
						}
					})
			}
			else{
				let params = {
					name : this.searchName,
					type: this.searchType,
					location: this.searchLocation,
					rating: this.searchRating

				}
				axios.put('restaurants/search', JSON.stringify(params)).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByLocation(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else{
				axios.get('restaurants/getByLocation/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
		},
		searchByRating(){
			if(this.searchParam == ""){
				axios.get('/restaurants/getAll')
					.then(response => {
						if (response.data != null) {
							this.restaurants = response.data
							console.log(this.users);
						}
					})
			}
			else if (this.searchParam >=1 && this.searchParam<=5){
				axios.get('restaurants/getByRating/' + this.searchParam).then(response => {
					this.restaurants = response.data
					console.log(response)

				}).catch(err => {
					console.log(err)
				});
			}
			else{
				alert("Please enter a number between 1 and 5!")
			}
		},
		filtrateOpen(){
			axios.put('/restaurants/filtrate/open', JSON.stringify(this.restaurants)).then(response => {
				this.restaurants = response.data
				console.log(response)
			})
		},
		filtrateByType(filter){
			axios.put('/restaurants/filtrate/type/' + filter, JSON.stringify(this.restaurants)
			).then(response => {
				this.restaurants = response.data
				console.log(response)

			}).catch(err => {
				console.log(err)
			});
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
			const list = this.restaurants.slice();
			console.log(list);
			if (this.sort.key !="") {
				list.sort((a, b) => {
					a = a[this.sort.key]
					b = b[this.sort.key]
					return (a === b ? 0 : a > b ? 1 : -1) * (this.sort.isAsc ? 1 : -1)
				});
			}
			this.restaurants = list
			if (this.sort.isAsc){
			this.btn_txt_name = "Sort By Name desc"
				this.btn_txt_loc = "Sort By Location desc"
				this.btn_txt_rate = "Sort By Rating desc"
			}else{
				this.btn_txt_name = "Sort By Name asc"
				this.btn_txt_loc = "Sort By Location asc"
				this.btn_txt_rate = "Sort By Rating asc"
			}
			this.$forceUpdate()
		},
		commentAndRate(){
			let params = {
				rating: this.rate,
				content: this.comment,
				restaurant: this.selected
			}
			axios.post('/comments/commentAndRate', JSON.stringify(params))
				.then(response => {
				console.log(response)
			})
			location.reload()
		},
		setSelected(selected_res){
			this.selected = selected_res
			axios.get('/customers/canComment/' + selected_res.name)
				.then(response => {
					console.log(response)
					this.can_comment = response.data
				})
		},
		deleteRestaurant(restaurant){
			axios.put('/restaurants/delete/' + restaurant.name)
				.then(response => {
						console.log(response)
						location.reload()
						if(response.data == ""){
							alert("Cannot delete restaurant that has undelivered orders!")
						}
					})
		}
	},
	template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">Choose a restaurant:</p>
			 </div>
		<div id="search_id" class="container">
			<table>
			<tr>
			<td><input type="search"  placeholder="Name..." v-model="searchName"></td>
			<td><input type="search"  placeholder="Location..." v-model="searchLocation"></td>
			
			<td class="select_search">
			<select  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="searchType">
					<option value=""></option>
                     <option v-for="type in types" :value="type">{{type}}</option>
			</select>
			</td>
			<td class="select_search">
			<select class="form-select form-select-sm"  aria-label=".form-select-sm example" v-model="searchRating">
				<option value=""></option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			</td>
			<td><button class="btn_search_res" type="button" v-on:click="search">Search</button></td>
			</tr>
			<tr>
			<td colspan="2"><label>Filtrate</label></td>
			<td></td>
			<td colspan="2"><label>Filtrate By Type</label></td>
			<tr>
			<td colspan="2">
			<button class="btn_filtrate" type="button" v-on:click="filtrateOpen">Open restaurants</button>
			</td>
			<td colspan="2">
			<select  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="filter"
			 @change="filtrateByType(filter)">
                     <option v-for="type in types" :value="type">{{type}}</option>
			</select>	
			</td>
			</tr>
			</table>
			<table>
			<tr>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('name')" @click="sortBy('name')">{{this.btn_txt_name}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('location')" @click="sortBy('name')">{{this.btn_txt_loc}}</button>
			</td>
			<td>
				<button class="btn_sort" type="button" :class="sortedClass('rating')" @click="sortBy('name')">{{this.btn_txt_rate}}</button>
			</td>
			</tr>
			</table>
		</div>
		<br>
		<div class="container" id="restaurant_view" v-for="r in restaurants" :key="r.name">
		<div v-on:click="viewRestaurant(r)">
			<img v-bind:src= "r.logo" alt="" id="restaurant_logo" class="rounded float-start" style="margin-top: 20px">
			<label class="restaurant_name">{{r.name}}</label></br>
        	<label class="restaurant_data">{{r.type}}</label></br> 
			<label class="restaurant_data">{{r.location.address.street}} {{r.location.address.streetNumber}}</label></br>
			<label class="restaurant_data">{{r.location.address.city}} {{r.location.address.zipcode}}</label></br>  
			<label class="restaurant_data">{{r.rating}}  &#10027;</label></br>
			<label class="restaurant_data" v-if="r.isOpen == true">OPEN</label>
			<label class="restaurant_data" v-else>CLOSED</label>
			<br>
		</div>
			<button class="btn_manager" type="button" data-bs-toggle="modal" data-bs-target="#modall" v-on:click="setSelected(r)" v-if="isCustomerLogged">Comment and rate</button>
			<button  class="btn_manager" type="button" v-if="isAdminLogged" v-on:click="deleteRestaurant(r)" >Delete</button>
			<hr style="height:10px">
        </div>
        <div class="modal fade" id="modall" tabindex="-1" aria-labelledby="exampleModalLiveLabel" style="display: none;" aria-hidden="true" >
          				<div class="modal-dialog" id="modal_content" v-if="can_comment">
            				<div class="modal-content">
                					<button type="button" style="margin-left: 430px" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              						<div class="modal-body">
										
										<div class="mb-3 row">
											<label  class="col-sm-3 col-form-label">Comment: </label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="text" class="form control-plaintext" v-model="comment">
											</div>
											<label  class="col-sm-3 col-form-label">Rate: </label></br>
											<div class="col-sm-9" style="margin-top: -6px">
												<input type="number" min="1" max="5" class="form control-plaintext" v-model="rate">
											</div>
  										</div>
										<div class="modal-footer">
	                						<button type="button" class="btn_modal" data-bs-dismiss="modal">Close</button>
	                						<button type="submit" class="btn_modal"  @click="commentAndRate" data-bs-dismiss="modal" :disabled="isNotFilled">Comment And Rate</button>
	              						</div>
              					</div>
              					
            				</div>
          				</div>
          				<div  class="modal-dialog" id="modal_content" v-else>
          				<div class="modal-content">
          					Cannot comment restaurant from which you haven't ordered	
          					<div class="modal-footer">
	                			<button type="button" class="btn_modal" data-bs-dismiss="modal">Close</button>
	              			</div>
	              		</div>
          				</div>
					</div>
	</div>
	`});
  