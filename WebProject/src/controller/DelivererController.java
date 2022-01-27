package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Gender;
import beans.Manager;
import beans.User;
import beans.UserType;
import dto.OrderRequest;
import dto.RegistrationDTO;
import service.CustomerService;
import service.DelivererService;
import service.ManagerService;
import service.UserService;

public class DelivererController {

	
	private ManagerService managerService;
	private CustomerService customerService;
	private DelivererService delivererService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public DelivererController(DelivererService delivererService, UserService userService, ManagerService managerService, CustomerService customerService) {
		this.delivererService = delivererService;
		this.userService = userService;
		this.managerService = managerService;
		this.customerService = customerService;
		
		post("/deliverers/registration", (req,res) -> {
			res.type("application/json");
			
			try {
				RegistrationDTO dto = gson.fromJson(req.body(), RegistrationDTO.class);
				System.out.println(dto.getDateOfBirth());
				System.out.println(dto.getGender());
				Deliverer newDeliverer = new Deliverer();
				newDeliverer.setUsername(dto.getUsername());
				newDeliverer.setName(dto.getName());
				newDeliverer.setLastName(dto.getLastName());
				newDeliverer.setPassword(dto.getPassword());
				newDeliverer.setGender(Gender.values()[Integer.valueOf(dto.getGender())]);
				Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDateOfBirth());  
				newDeliverer.setDateOfBirth(date);
				newDeliverer.setUserType(UserType.DELIVERER);
				
				if(customerService.getAll() != null) {
					for (Customer customer : customerService.getAll()) {
						if(customer.getUsername().equals(newDeliverer.getUsername())) {
							return "Username taken";
						}
					}
				}
				if(userService.getAllUsers() != null) {
					for (User user : userService.getAllUsers()) {
						if(user.getUsername().equals(newDeliverer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(delivererService.getAll() != null) {
					for (Deliverer deliverer : delivererService.getAll()) {
						if(deliverer.getUsername().equals(newDeliverer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				if(managerService.getAll() != null) {
					for (Manager manager : managerService.getAll()) {
						if(manager.getUsername().equals(newDeliverer.getUsername())) {	
							return "Username taken";
						}
					}
				}
				
				Deliverer created = delivererService.create(newDeliverer);

				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("/deliverers/getAllRequests/:name", (req, res) -> {
			res.type("application/json");
			try {	
				ArrayList<OrderRequest> requests = delivererService.getAllRequests(req.params("name"));
				return gson.toJson(requests);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		
	}
}
