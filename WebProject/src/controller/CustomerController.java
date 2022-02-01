package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import beans.Customer;
import beans.Deliverer;
import beans.Gender;
import beans.Manager;
import beans.User;
import beans.UserType;
import dto.RegistrationDTO;
import service.CustomerService;
import service.DelivererService;
import service.ManagerService;
import service.UserService;
import spark.Session;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerController {
	
	private CustomerService customerService;
	private ManagerService managerService;
	private DelivererService delivererService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public CustomerController(CustomerService customerService, UserService userService, ManagerService managerService, DelivererService delivererService) {
		this.customerService = customerService;
		this.userService = userService;
		this.managerService = managerService;
		this.delivererService = delivererService;
		
		post("/customers/registration", (req,res) -> {
			res.type("application/json");
			
			try {
				RegistrationDTO dto = gson.fromJson(req.body(), RegistrationDTO.class);
				Customer newCustomer = new Customer();
				newCustomer.setUsername(dto.getUsername());
				newCustomer.setName(dto.getName());
				newCustomer.setLastName(dto.getLastName());
				newCustomer.setPassword(dto.getPassword());
				newCustomer.setGender(Gender.values()[Integer.valueOf(dto.getGender())]);
				Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDateOfBirth());  
				newCustomer.setDateOfBirth(date);
				newCustomer.setUserType(UserType.CUSTOMER);
				newCustomer.setPoints(0);
				if(customerService.getAll() != null) {
					for (Customer customer : customerService.getAll()) {
						if(customer.getUsername().equals(newCustomer.getUsername())) {
							return "Username taken";
						}
					}
				}
				if(userService.getAllUsers() != null) {
					for (User user : userService.getAllUsers()) {
						if(user.getUsername().equals(newCustomer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(delivererService.getAll() != null) {
					for (Deliverer deliverer : delivererService.getAll()) {
						if(deliverer.getUsername().equals(newCustomer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(managerService.getAll() != null) {
					for (Manager manager : managerService.getAll()) {
						if(manager.getUsername().equals(newCustomer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				
				Customer created = customerService.create(newCustomer);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("/customers/canComment/:restaurant", (req, res) -> {
			res.type("application/json");
			try {	
				Session session = req.session(true);
				Customer logged = session.attribute("logged");
				if(logged == null) {
					return gson.toJson(false);
				}
				return gson.toJson(customerService.canComment(logged.getUsername(), req.params("restaurant")));	
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/customers/filtrateByType/:filter", (req,res) -> {
			res.type("application/json");
			try {
				
				String param = req.params("filter");
				ArrayList<Customer> results = gson.fromJson(req.body(), new TypeToken<ArrayList<Customer>>(){}.getType());
				ArrayList<Customer> filtratedUsers = customerService.filtrateByCustomerType(param, results);
				return gson.toJson(filtratedUsers);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
}
