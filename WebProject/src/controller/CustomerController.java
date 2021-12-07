package controller;

import com.google.gson.Gson;

import beans.Customer;
import beans.User;
import beans.UserType;
import service.CustomerService;
import service.UserService;

import static spark.Spark.post;
import spark.Session;

public class CustomerController {
	
	private CustomerService customerService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public CustomerController(CustomerService customerService, UserService userService) {
		this.customerService = customerService;
		this.userService = userService;
		
		post("/customers/registration", (req,res) -> {
			res.type("application/json");
			
			try {
				Customer newCustomer = gson.fromJson(req.body(), Customer.class);
				newCustomer.setUserType(UserType.CUSTOMER);
				newCustomer.getCart().setCustomer(newCustomer.getUsername());
				
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
				
				Customer created = customerService.create(newCustomer);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
}
