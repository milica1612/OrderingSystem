package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Customer;
import beans.Gender;
import beans.Item;
import beans.Manager;
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
		
		post("/restaurants/create", (req,res) -> {
			res.type("application/json");
			
			try {
				Restaurant newRestaurant = gson.fromJson(req.body(), Restaurant.class);
				newRestaurant.setIsOpen(true);
				newRestaurant.setRating(5);
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
		
		get("/restaurants/getAll", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Restaurant> all = restaurantService.getAll();
				ArrayList<Restaurant> result = new ArrayList<Restaurant>();
				for (Restaurant restaurant : all) {
					if(restaurant.getIsOpen().equals(true)) {
						result.add(restaurant);
					}
				}
				for(Restaurant r : all) {
					if(r.getIsOpen().equals(false)) {
						result.add(r);
					}
				}
				return gson.toJson(result);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/restaurants/getByName/:name", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Restaurant> restaurants = restaurantService.getRestaurantsByName(req.params("name"));
				return gson.toJson(restaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/restaurantPage/:name", (req, res) -> {
			res.type("application/json");
			try {
				return gson.toJson(restaurantService.getRestaurant(req.params("name")));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		post("/restaurants/create", (req,res) -> {
			res.type("application/json");
			
			try {
				Restaurant newRestaurant = gson.fromJson(req.body(), Restaurant.class);
				newRestaurant.setIsOpen(true);
				newRestaurant.setRating(5);
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
		
		post("/restaurantPage/addNewItem/:name", (req, res) -> {
			res.type("application/json");
			
			try {
				Item item = gson.fromJson(req.body(), Item.class);
				String restaurantName = req.params("name");	
				System.out.println(restaurantName);
				Item created = restaurantService.addNewItem(restaurantName, item);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
				
		get("/restaurants/getByType/:type", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Restaurant> restaurants = restaurantService.getRestaurantsByType(req.params("type"));
				return gson.toJson(restaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/restaurants/getByLocation/:location", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Restaurant> restaurants = restaurantService.getRestaurantsByLocation(req.params("location"));
				return gson.toJson(restaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/restaurants/getByRating/:rating", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Restaurant> restaurants = restaurantService.getRestaurantsByRating(req.params("rating"));
				return gson.toJson(restaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/restaurants/getTypes", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<String> types = restaurantService.getRestaurantTypes();
				return gson.toJson(types);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		put("/restaurants/filtrate/type/:filter", (req,res) -> {
			res.type("application/json");
			try {
				String param = req.params("filter");
				ArrayList<Restaurant> results = gson.fromJson(req.body(), new TypeToken<ArrayList<Restaurant>>(){}.getType());
				ArrayList<Restaurant> filtratedRestaurants = restaurantService.filtrateRestauntantsByType(param, results);
				return gson.toJson(filtratedRestaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/restaurants/filtrate/open", (req,res) -> {
			res.type("application/json");
			try {
				String param = req.params("filter");
				ArrayList<Restaurant> results = gson.fromJson(req.body(), new TypeToken<ArrayList<Restaurant>>(){}.getType());
				ArrayList<Restaurant> filtratedRestaurants = restaurantService.filtrateOpenRestauntants(results);
				return gson.toJson(filtratedRestaurants);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	
	get("/restaurants/:name", (req, res) -> {
		res.type("application/json");
		try {
			Restaurant restaurant = restaurantService.getByName(req.params("name"));
			if(restaurant == null) {
				return "";
			}else {
				return gson.toJson(restaurant);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	});
	}
}
