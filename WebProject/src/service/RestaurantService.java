package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import beans.Customer;
import beans.Restaurant;
import dao.RestaurantDAO;

public class RestaurantService {
	private RestaurantDAO restaurantDAO;
	private ImageService imageService;

	public RestaurantService(RestaurantDAO restaurantDAO, ImageService imageService) {
		super();
		this.restaurantDAO = restaurantDAO;
		this.imageService = imageService;
	}

	public ArrayList<Restaurant> getAll() {
		return restaurantDAO.getAll();
	}

	public Restaurant create(Restaurant newRestaurant) throws IOException {
		String filePath = "images/logo/" + newRestaurant.getName() + ".jpg";
		imageService.Base64DecodeAndSave(newRestaurant.getLogo(), filePath);
		newRestaurant.setLogo(filePath);
		return restaurantDAO.create(newRestaurant);
	}

	public Restaurant getByName(String name) throws JsonSyntaxException, IOException  {
		ArrayList<Restaurant> restaurants = getAll();
		if(restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if(restaurant.getName().equals(name)) {
					return restaurant;
				}
			}
		}
		return null;
	}

	public ArrayList<Restaurant> getRestaurantsByName(String params) {
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> result = new ArrayList<Restaurant>();
		if(restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if(restaurant.getName().toLowerCase().contains(params.toLowerCase().trim())) {
					result.add(restaurant);
				}
			}
		}
		return result;
	}

	public ArrayList<Restaurant> getRestaurantsByType(String params) {
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> result = new ArrayList<Restaurant>();
		if(restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if(restaurant.getType().toLowerCase().contains(params.toLowerCase().trim())) {
					result.add(restaurant);
				}
			}
		}
		return result;
	}

	public ArrayList<Restaurant> getRestaurantsByLocation(String params) {
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> result = new ArrayList<Restaurant>();
		if(restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if(restaurant.getLocation().getAddress().getCity().toLowerCase().contains(params.toLowerCase().trim())) {
					result.add(restaurant);
				}
			}
		}
		return result;
	}

	public ArrayList<Restaurant> getRestaurantsByRating(String params) {
		Double rating = Double.parseDouble(params);
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<Restaurant> result = new ArrayList<Restaurant>();
		if(restaurants != null) {
			for (Restaurant restaurant : restaurants) {
				if(restaurant.getRating() > rating) {
					result.add(restaurant);
				}
			}
		}
		return result;
	}

	public ArrayList<Restaurant> filtrateRestauntantsByType(String param, ArrayList<Restaurant> results) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		for (Restaurant restaurant : results) {
			if(restaurant.getType().toLowerCase().contains(param.toLowerCase().trim())) {
				restaurants.add(restaurant);
			}
		}
		return restaurants;
	}

	public ArrayList<Restaurant> filtrateOpenRestauntants(ArrayList<Restaurant> results) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		for (Restaurant restaurant : results) {
			if(restaurant.getIsOpen() == true) {
				restaurants.add(restaurant);
			}
		}
		return restaurants;
	}

	public Restaurant getRestaurant(String name) throws JsonSyntaxException, IOException {
		return restaurantDAO.getRestaurant(name);
	}

	public ArrayList<String> getRestaurantTypes() {
		ArrayList<Restaurant> restaurants = getAll();
		ArrayList<String> types = new ArrayList<String>();
		for (Restaurant restaurant : restaurants) {
			if(!types.contains(restaurant.getType())){
				types.add(restaurant.getType());
			}
		}
		return types;
	}
	
	
}
