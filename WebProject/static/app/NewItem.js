Vue.component("newItem", {

    data() {
        return {
			name: "",
			price: 0.0,
			type: "",
			photo:"",
			description: "",
			quantity: "",
			img_name: ""
        }
    },
	mounted () {
		
	},
	methods: {
        create(){
            let params = {
                name: this.name,
				price: this.price,
                type: this.type,
				photo: this.photo,
				description: this.description,
				quantity: this.quantity
            }
            axios.post('/restaurantPage/addNewItem/' + localStorage.getItem("restaurant"), JSON.stringify(params)).then(
                response => {
                    console.log(response)
                   	window.location.href = "#/restaurantPage?name=" + localStorage.getItem("restaurant");
                }
            ) 
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
	computed:{},
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
                <div class="d-grid gap-2 col-6 mx-auto"">
                    <button id="btn" class="btn btn-warning" type="button" @click="create" :key="button_text">CREATE</button>
                </div>
		</div>
	</div>
	`});
 