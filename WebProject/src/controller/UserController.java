package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Customer;
import beans.Gender;
import beans.Order;
import beans.User;
import beans.UserType;
import dto.LoginDTO;
import dto.RegistrationDTO;
import dto.UserDTO;
import service.CustomerService;
import service.DelivererService;
import service.ManagerService;
import service.UserService;

import static spark.Spark.post;
import static spark.Spark.put;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static spark.Spark.get;
import spark.Session;

public class UserController {
	private CustomerService customerService;
	private ManagerService managerService;
	private DelivererService delivererService;
	private UserService userService;
	private static Gson gson = new Gson();
	
	public UserController(CustomerService customerService, UserService userService, ManagerService managerService, DelivererService delivererService) {
		super();
		
		this.customerService = customerService;
		this.userService = userService;
		this.managerService = managerService;
		this.delivererService = delivererService;
		
		post("/users/login", (req, res) -> {
			res.type("application/json");
			try {
				User logged = userService.login(gson.fromJson(req.body(), LoginDTO.class));
				if(logged == null) {
					logged = customerService.login(gson.fromJson(req.body(), LoginDTO.class));
				}
				if(logged == null) {
					logged = managerService.login(gson.fromJson(req.body(), LoginDTO.class));
				}
				if(logged == null){
					logged = delivererService.login(gson.fromJson(req.body(), LoginDTO.class));
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
				if(logged == null) {
					return gson.toJson( new User());
				}
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
				all.addAll(managerService.getAll());
				all.addAll(delivererService.getAll());
				return gson.toJson(all);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/users/getByName/:name", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<User> users = userService.getUsersByName(req.params("name"));
				return gson.toJson(users);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/users/getByLastName/:lastName", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<User> users = userService.getUsersByLastName(req.params("lastName"));
				return gson.toJson(users);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});

		get("/users/getByUsername/:username", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<User> users = userService.getUsersByUsername(req.params("username"));
				return gson.toJson(users);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		put("/users/filtrate/:filter", (req,res) -> {
			res.type("application/json");
			try {
				String param = req.params("filter");
				UserType usertype = UserType.values()[Integer.valueOf(param)];
				System.out.println(usertype.toString());
				ArrayList<User> results = gson.fromJson(req.body(), new TypeToken<ArrayList<User>>(){}.getType());
				ArrayList<User> filtratedUsers = userService.filtrateUsers(usertype, results);
				return gson.toJson(filtratedUsers);
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
				if(newUser == null) {
					newUser = managerService.editManager(logged, userDTO);
				}
				if(newUser == null){
					newUser = delivererService.editDeliverer(logged, userDTO);
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
					user = managerService.getUserByUsername(req.params("username"));
				}
				if(user == null) {
					user = delivererService.getUserByUsername(req.params("username"));
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
		
		get("/users/logout", (req, res) -> {
			res.type("application/json");
			Session session = req.session(true);
			User logged = session.attribute("logged");
			
			if (logged != null) {
				session.invalidate();
			}
			return true;
		});
		
		put("/users/changePassword", (req,res) -> {
			res.type("application/json");
			
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				System.out.println(logged.getUsername());
				String newPassword = gson.fromJson(req.body(), String.class);
				User newUser = userService.changePassword(logged, newPassword);	
				if(newUser == null) {
					newUser = managerService.changePassword(logged, newPassword);
				}
				if(newUser == null) {
					newUser = customerService.changePassword(logged, newPassword);
				}
				if(newUser == null) {
					newUser = delivererService.changePassword(logged, newPassword);
				}
				session.attribute("logged", newUser);
				return gson.toJson(newUser);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/users/deleteUser/:username", (req,res) -> {
			res.type("application/json");
			
			try {
				User user = userService.deleteUser(req.params("username"));
				if(user == null) {
					user = managerService.deleteUser(req.params("username"));
				}
				if(user == null) {
					user = customerService.deleteUser(req.params("username"));
				}
				if(user == null) {
					user = delivererService.deleteUser(req.params("username"));
				}
				return gson.toJson(user);
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
	}
}
