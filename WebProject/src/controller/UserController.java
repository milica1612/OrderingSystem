package controller;

import com.google.gson.Gson;

import beans.User;
import dto.LoginDTO;
import dto.UserDTO;
import service.CustomerService;
import service.UserService;

import static spark.Spark.post;
import static spark.Spark.put;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static spark.Spark.get;
import spark.Session;

public class UserController {
	private CustomerService customerService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public UserController(CustomerService customerService, UserService userService) {
		super();
		this.customerService = customerService;
		this.userService = userService;
		
		post("/users/login", (req, res) -> {
			res.type("application/json");
			try {
				User logged = userService.login(gson.fromJson(req.body(), LoginDTO.class));
				if(logged == null) {
					logged = customerService.login(gson.fromJson(req.body(), LoginDTO.class));
				}
				if (logged != null) {
					Session session = req.session(true);
					session.attribute("logged", logged);
					return gson.toJson(logged);
				} else {
					return "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
		
		get("/users/logged", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				UserDTO userDTO = new UserDTO(logged.getUsername(), logged.getPassword(),logged.getName(), logged.getLastName(),
						logged.getGender().ordinal(), dateFormat.format(logged.getDateOfBirth())) ;
				return gson.toJson(userDTO);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
		
		get("/users/getAllUsers", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<User> all = userService.getAllUsers();
				all.addAll(customerService.getAll());
				return gson.toJson(all);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});

		put("/users/edit", (req,res) -> {
			res.type("application/json");
			
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				UserDTO userDTO = gson.fromJson(req.body(), UserDTO.class);
				User newUser = userService.editUser(logged, userDTO);	
				if(newUser == null) {
					newUser = customerService.editCustomer(logged, userDTO);
				}
				session.attribute("logged", newUser);
				return gson.toJson(newUser);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/users/:username", (req, res) -> {
			res.type("application/json");
			try {
				User user = userService.getUserByUsername(req.params("username"));
				if(user == null) {
					user = customerService.getUserByUsername(req.params("username"));
				}
				if(user == null) {
					return "";
				}else {
					return gson.toJson(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
	}
	
	
}
