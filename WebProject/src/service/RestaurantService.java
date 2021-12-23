package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Customer;
import beans.Restaurant;
import dao.RestaurantDAO;

public class RestaurantService {
	private RestaurantDAO restaurantDAO;

	public RestaurantService(RestaurantDAO restaurantDAO) {
		super();
		this.restaurantDAO = restaurantDAO;
	}

	public ArrayList<Restaurant> getAll() {
		return restaurantDAO.getAll();
	}

	public Restaurant create(Restaurant newRestaurant) throws IOException {
		return restaurantDAO.create(newRestaurant);
	}
	
	
}
