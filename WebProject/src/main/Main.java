package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import controller.CommentController;
import controller.CartController;
import controller.CustomerController;
import controller.DelivererController;
import controller.ManagerController;
import controller.UserController;
import dao.CommentDAO;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dao.UserDAO;
import service.CommentService;
import service.CartService;
import service.CustomerService;
import service.DelivererService;
import service.ImageService;
import service.ManagerService;
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
		
		ManagerDAO managerDAO = new ManagerDAO("./files/managers.json");
		ManagerService managerService = new ManagerService(managerDAO);
		
		DelivererDAO delivererDAO = new DelivererDAO("./files/deliverers.json");
		DelivererService delivererService = new DelivererService(delivererDAO);
		
		ImageService imageService = new ImageService();
		RestaurantDAO restaurantDAO = new RestaurantDAO("./files/restaurants.json");
		RestaurantService restaurantService = new RestaurantService(restaurantDAO, imageService);
		
		CommentDAO commentDAO = new CommentDAO("./files/comments.json");
		CommentService commentService = new CommentService(commentDAO);
		
		CartDAO cartDAO = new CartDAO("./files/carts.json");
		CartService cartService = new CartService(cartDAO, imageService);

		
		UserController userController = new UserController(customerService, userService, managerService, delivererService);
		CustomerController customerController = new CustomerController(customerService, userService, managerService, delivererService);
		ManagerController managerController = new ManagerController(managerService, userService, customerService, delivererService, restaurantService);
		DelivererController delivererController = new DelivererController(delivererService, userService, managerService, customerService);
		RestaurantController restaurantController = new RestaurantController(restaurantService);
		CommentController commentController = new CommentController(commentService);
		CartController cartController = new CartController(cartService);
	}

}
