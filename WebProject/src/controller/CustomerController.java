package controller;

import com.google.gson.Gson;

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

import static spark.Spark.post;

import java.text.SimpleDateFormat;
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
				System.out.println(dto.getDateOfBirth());
				System.out.println(dto.getGender());
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
		
		
	}
}
