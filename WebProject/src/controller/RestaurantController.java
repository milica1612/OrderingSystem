package controller;

import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import beans.Customer;
import beans.Gender;
import beans.Restaurant;
import beans.User;
import beans.UserType;
import dto.RegistrationDTO;
import service.RestaurantService;

public class RestaurantController {
	
	private RestaurantService restaurantService;
	private static Gson gson = new Gson();
	
	public RestaurantController(RestaurantService restaurantService) {
		super();
		this.restaurantService = restaurantService;
		
		post("/restaurants/add", (req,res) -> {
			res.type("application/json");
			
			try {
				Restaurant newRestaurant = gson.fromJson(req.body(), Restaurant.class);
				if(restaurantService.getAll() != null) {
					for (Restaurant restaurant : restaurantService.getAll()) {
						if(restaurant.getName().equals(newRestaurant.getName())) {
							return "Username taken";
						}
					}
				}
				
				Restaurant created = restaurantService.create(newRestaurant);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
	
}
