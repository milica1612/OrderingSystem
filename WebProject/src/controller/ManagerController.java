package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import beans.Customer;
import beans.Deliverer;
import beans.Gender;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import beans.UserType;
import dto.RegistrationDTO;
import dto.UserDTO;
import service.CustomerService;
import service.DelivererService;
import service.ManagerService;
import service.RestaurantService;
import service.UserService;
import spark.Session;

public class ManagerController {

	
	private ManagerService managerService;
	private CustomerService customerService;
	private DelivererService delivererService;
	private UserService userService;
	private RestaurantService restaurantService;
	private static Gson gson = new Gson();
	
	public ManagerController(ManagerService managerService, UserService userService, CustomerService customerService, DelivererService delivererService, RestaurantService restaurantService) {
		this.managerService = managerService;
		this.userService = userService;
		this.customerService = customerService;
		this.delivererService = delivererService;
		this.restaurantService = restaurantService;
		
		post("/managers/registration", (req,res) -> {
			res.type("application/json");
			
			try {
				RegistrationDTO dto = gson.fromJson(req.body(), RegistrationDTO.class);
				Manager newManager = new Manager();
				newManager.setUsername(dto.getUsername());
				newManager.setName(dto.getName());
				newManager.setLastName(dto.getLastName());
				newManager.setPassword(dto.getPassword());
				newManager.setGender(Gender.values()[Integer.valueOf(dto.getGender())]);
				Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDateOfBirth());  
				newManager.setDateOfBirth(date);
				newManager.setUserType(UserType.MANAGER);
				
				if(customerService.getAll() != null) {
					for (Customer customer : customerService.getAll()) {
						if(customer.getUsername().equals(newManager.getUsername())) {
							return "Username taken";
						}
					}
				}
				if(userService.getAllUsers() != null) {
					for (User user : userService.getAllUsers()) {
						if(user.getUsername().equals(newManager.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(delivererService.getAll() != null) {
					for (Deliverer deliverer : delivererService.getAll()) {
						if(deliverer.getUsername().equals(newManager.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(managerService.getAll() != null) {
					for (Manager manager : managerService.getAll()) {
						if(manager.getUsername().equals(newManager.getUsername())) {	
							return "Username taken";
						}
					}
				}
				
				Manager created = managerService.create(newManager);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		
		get("/managers/getAllAvailable", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				ArrayList<Manager> managers = managerService.getAllAvailable();
				return gson.toJson(managers);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		get("/managers/restaurant", (req,res) -> {
			res.type("application/json");
			try {
			Session session = req.session(true);
			User logged = session.attribute("logged");
			Restaurant restaurant = managerService.getRestaurant(logged.getUsername());
			
			return gson.toJson(restaurant);
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		put("/managers/restaurant/:name", (req,res) -> {
			res.type("application/json");
			try {
				String name = req.params("name");
				String manager = gson.fromJson(req.body(), String.class);
				System.out.println(manager.toString());
				Restaurant restaurant = restaurantService.getByName(name);
				Manager newManager = managerService.addRestaurant(manager, restaurant);	
				
				return gson.toJson(newManager);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		
		
	}
}
