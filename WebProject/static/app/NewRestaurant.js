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
            logo: ""
        }
    },
    methods:{

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
				  <input type="text" class="form-control" v-model="street_number">
				  <label>Zipcode</label>
				  <input type="text" class="form-control" v-model="zipcode">
				  <label>Latitude</label>
				  <input type="text" class="form-control" v-model="latitude">
				  <label>Longitude</label>
				  <input type="text" class="form-control" v-model="longitude">
				</div>
				<div>
				  <label>Logo</label>
				  <input type="file" class="form-control" v-model="logo" accept="image/*">
				</div>
				<div>
				<label>Manager</label>
				<select id="gender_select"  class="form-select form-select-sm" aria-label=".form-select-sm example" v-model="gender">
				  	<option value="0" >Male</option>
				  	<option value="1">Female</option>
				  	<option value="2">Other</option>
				  </select>	
				 </div>
				
				<div class="text-center" id="err_div">
				    <p class="error" v-if="passwordsNotSame">Password and confirm password should match!</p>
				    <p class="error" v-if="notFilled">All fields should be filled!</p>
				    <p class="error" v-if="isUsernameTaken">Username already taken!</p>
			    </div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto">
  					<button id="btn" class="btn btn-warning" type="button" @click="register" :disabled="notOK">CREATE</button>
 				</div>
			
		</div>
	</div>
	
	`
});