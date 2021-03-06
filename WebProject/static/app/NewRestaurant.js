Vue.component("newRestaurant", {
    data() {
        return {
            name: "",
            restaurant_type: "",
            city: "",
            street: "",
            street_number: "",
            zipcode: "",
            longitude: "",
            latitude: "",
            logo: "",
            manager: null,
            managers: null,
            button_text: "",
            img_name: "",
            taken: "",
            not_filled: false
        }
    },
    methods:{
        create(){

                let params = {
                    name: this.name,
                    type: this.restaurant_type,
                    logo: this.logo,
                    location:{
                        address:{
                            city: this.city,
                            street: this.street,
                            streetNumber: this.street_number,
                            zipcode: this.zipcode
                        },
                        latitude: this.latitude,
                        longitude: this.longitude
                    }

                }
                axios.post('/restaurants/create', JSON.stringify(params)).then(
                    response => {
                        console.log(response)
                        if (this.managers != null && this.managers.length != 0) {
                            axios.put('/managers/restaurant/' + this.name, JSON.stringify(this.manager.username)).then(
                                response => {
                                    console.log(response)
									 this.$router.push("/");
                                })
                        }else{
                            localStorage.setItem("restaurant", this.name)
                            this.$router.push("/managerRegistration");
                        }
                    }
                )


        },
        setFiles: function(event){
            const file = event.target.files[0];
            this.createBase64Image(file);
            this.logo = URL.createObjectURL(file);
        },
        createBase64Image(file){
            const reader= new FileReader();

            reader.onload = (e) =>{
                let img = e.target.result;
                this.logo = img;
            }
            reader.readAsDataURL(file);
        },
    },
    mounted(){
	 	if(localStorage.getItem("role") != 'ADMIN'){
            this.$router.push("/")
        }else{
			axios.get('/managers/getAllAvailable').then(
	            response =>{
	                this.managers = response.data
	                if(this.managers.length == 0){
	                    this.button_text = "CREATE AND REGISTER NEW MANAGER"
	                }else{
	                    this.button_text = "CREATE"
	                }
	                this.$forceUpdate()
	            }
	        ).catch()
 		}
    },
    computed:{
        availableManagers(){
            if(this.managers == null || this.managers.length == 0){
                return false
            }
            return true
        },
        isNameTaken() {
            axios.get('restaurants/' + this.name).then(response => {
                this.taken = response.data
                console.log(response)

            }).catch(err => {
                console.log(err)
            });
            if(this.taken == ""){
                return false
            }
            return true

        },
        notFilled(){
            if(this.managers.length == 0) {
                if (this.name == "" || this.restaurant_type == "" || this.city == "" || this.street == "" ||
                    this.street_number == "" || this.zipcode == "" || this.longitude == "" || this.latitude == ""
                    || this.logo == "") {
                    this.not_filled = true
                    return true
                }else{
                    this.not_filled = false
                    return false
                }
            }else {
                if(this.manager == null){
                    this.not_filled = true
                    return true
                }else{
                    this.not_filled = false
                    return false
                }
            }
            }
        ,
        notOK(){
            if(this.not_filled == true || this.isNameTaken == true){
                return true
            }
            return false
        }
    },
    template: `
	<div class="reg">
		<div class="container">
			<p id="title" class="text-center">NEW RESTAURANT</p>
			<div id="form" class="container">
				<div>
				  <label>Name</label>
				  <input type="text" class="form-control" v-model="name">
				</div>
				<div>
				  <label>Restaurant Type</label>
				  <input type="text" class="form-control" v-model="restaurant_type">
				</div>
				<div>
				  <label>City</label>
				  <input type="text" class="form-control" v-model="city">
				  <label>Street</label>
				  <input type="text" class="form-control" v-model="street">
				  <label>Street number</label>
				  <input type="number" class="form-control" v-model="street_number">
				  <label>Zipcode</label>
				  <input type="number" class="form-control" v-model="zipcode">
				  <label>Latitude</label>
				  <input type="number" class="form-control" v-model="latitude">
				  <label>Longitude</label>
				  <input type="number" class="form-control" v-model="longitude">
				</div>
				<div>
				  <label>Logo</label>
				  <input type="file" class="form-control" accept="image/*" @change="setFiles" v-model="img_name">
				</div>
				<div v-if="availableManagers">
				<label>Manager</label>
				<select  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="manager">
                     <option v-for="manager in managers" :value="manager">{{manager.name}} {{manager.lastName}}</option>
				  </select>	
				 </div>
				
				<div class="text-center" id="err_div">
				    <p class="error" v-if="notFilled">All fields should be filled!</p>
				    <p class="error" v-if="isNameTaken">Name already taken!</p>
			    </div>
				</div>
                <div class="d-grid gap-2 col-6 mx-auto"">
                    <button id="btn" class="btn btn-warning" type="button" @click="create" :disabled="notOK" :key="button_text">{{this.button_text}}</button>
                </div>
		</div>
	</div>
	
	`
});