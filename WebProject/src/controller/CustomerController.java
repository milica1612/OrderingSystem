package controller;

import com.google.gson.Gson;

import beans.Customer;
import beans.Gender;
import beans.User;
import beans.UserType;
import dto.RegistrationDTO;
import service.CustomerService;
import service.UserService;

import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.Date;

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
