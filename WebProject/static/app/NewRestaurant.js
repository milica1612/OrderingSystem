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
            manager: {},
            managers: null,
            button_text: ""
        }
    },
    methods:{
        create(){
            if(this.managers != null){
                let params = {
                    name: this.name,
                    type: this.restaurant_type,
                    logo: this.logo,
                    location:{
                        address:{
                            city: this.city,
                            street: this.street,
                            streetNumber: this.streetNumber,
                            zipcode: this.zipcode
                        },
                        latitude: this.latitude,
                        longitude: this.longitude
                    }

                }
                axios.post('/restaurants/create', JSON.stringify(params)).then(
                    response => {
                        console.log(response)
                        axios.put('/managers/restaurant/' + this.name, JSON.stringify(this.manager)).then(
                            response => {
                                console.log(response)
                        })
                    }
                )

            }
        }
    },
    mounted(){
        axios.get('/managers/getAllAvailable').then(
            response =>{
                this.managers = response.data
                if(this.managers == null){
                    this.button_text = "CREATE AND REGISTER NEW MANAGER"
                }else{
                    this.button_text = "CREATE"
                }
                this.$forceUpdate()
            }
        ).catch()
    },
    computed:{
        availableManagers(){
            if(this.managers == null){
                return false
            }
            return true
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
				  <input type="file" class="form-control" v-model="logo" accept="image/*">
				</div>
				<div v-if="availableManagers">
				<label>Manager</label>
				<select  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="manager">
                     <option v-for="manager in managers" :value="manager">{{manager.name}} {{manager.lastName}}</option>
				  </select>	
				 </div>
				
				<div class="text-center" id="err_div">
				    <p class="error" v-if="passwordsNotSame">Password and confirm password should match!</p>
				    <p class="error" v-if="notFilled">All fields should be filled!</p>
				    <p class="error" v-if="isUsernameTaken">Username already taken!</p>
			    </div>
				</div>
                <div class="d-grid gap-2 col-6 mx-auto"">
                    <button id="btn" class="btn btn-warning" type="button" @click="create" :key="button_text">{{this.button_text}}</button>
                </div>
		</div>
	</div>
	
	`
});