package controller;

import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class ManagerController {

	
	private ManagerService managerService;
	private CustomerService customerService;
	private DelivererService delivererService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public ManagerController(ManagerService managerService, UserService userService, CustomerService customerService, DelivererService delivererService) {
		this.managerService = managerService;
		this.userService = userService;
		this.customerService = customerService;
		this.delivererService = delivererService;
		
		post("/managers/registration", (req,res) -> {
			res.type("application/json");
			
			try {
				RegistrationDTO dto = gson.fromJson(req.body(), RegistrationDTO.class);
				System.out.println(dto.getDateOfBirth());
				System.out.println(dto.getGender());
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
		
		
	}
}
