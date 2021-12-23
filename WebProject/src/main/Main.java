package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import controller.CustomerController;
import controller.RestaurantController;
import controller.UserController;
import dao.CustomerDAO;
import dao.RestaurantDAO;
import dao.UserDAO;
import service.CustomerService;
import service.RestaurantService;
import service.UserService;

public class Main {

	private static Gson g = new Gson();
	
	public static void main(String[] args) throws Exception {
		port(8088);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		get("/test", (req, res) -> {
			return "Works";
		});
		
		UserDAO userDAO = new UserDAO("./files/users.json");
		UserService userService = new UserService(userDAO);
		
		CustomerDAO customerDAO = new CustomerDAO("./files/customers.json");
		CustomerService customerService = new CustomerService(customerDAO);
		UserController userController = new UserController(customerService, userService);
		CustomerController customerController = new CustomerController(customerService, userService);
		
		RestaurantDAO restaurantDAO = new RestaurantDAO("./files/restaurants.json");
		RestaurantService restaurantService = new RestaurantService(restaurantDAO);
		RestaurantController restaurantController = new RestaurantController(restaurantService);
	}

}
