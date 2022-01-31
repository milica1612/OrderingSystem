Vue.component("newItem", {

    data() {
        return {
			name: "",
			price: 0.0,
			type: "",
			photo:"",
			description: "",
			quantity: "",
			not_filled: false,
			img_name: "",
        }
    },
	mounted () {
		if(localStorage.getItem("role") != 'MANAGER'){
			this.$router.push("/")
		}
	},
	methods: {
        create(){
				if(this.name != "" && this.price != 0.0 && this.type != "" && this.photo != ""){
		        let params = {
	                name: this.name,
					price: this.price,
	                type: this.type,
					photo: this.photo,
					description: this.description,
					quantity: this.quantity,
					restaurant: localStorage.getItem("restaurant"),
	            }
	            axios.post('/restaurantPage/addNewItem/' + localStorage.getItem("restaurant"), JSON.stringify(params)).then(
	                response => {
						if(response.data != null){
	                    console.log(response)
	                   	window.location.href = "#/restaurantPage?name=" + localStorage.getItem("restaurant");
						}else{
							alert('Item name already taken!');
						}
	                }
	            ) 
			}else{
					alert('Name, type, price and photo field should be filled!')
				}
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
	},
	template: `
	<div class="reg">
		<p id="title" class="text-center">NEW ITEM</p>
			<div id="form" class="container">
				<div>
				  <label>Name</label>
				  <input type="text" class="form-control" v-model="name">
				</div>
				<div>
				  <label>Price</label>
				  <input type="number" class="form-control" v-model="price" min="1">
				</div>
				<div>
				  <label>Type</label>
				 <select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="type">
				  	<option value="0" >FOOD</option>
				  	<option value="1">DRINK</option>
				  </select>	
				</div>
				<div>
				  <label>Photo</label>
				  <input type="file" class="form-control" accept="image/*" @change="setFiles" v-model="img_name">
				</div>
				<div>
				  <label>Description</label>
				  <input type="text" class="form-control" v-model="description">
				</div>
				<div>
				  <label>Quantity</label>
				  <input type="text" class="form-control" v-model="quantity">
				</div>
				<div class="text-center" id="err_div">
				    <p class="error" v-if="notFilled">Name, type, price and photo field should be filled!</p>
					<p class="error" v-else></p>
			    </div>
                <div class="d-grid gap-2 col-6 mx-auto"">
                    <button id="btn" class="btn btn-warning" type="button" @click="create" :key="button_text">CREATE</button>
                </div>
		</div>
	</div>
	`});
 