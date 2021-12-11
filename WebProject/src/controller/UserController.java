package controller;

import com.google.gson.Gson;

import beans.User;
import dto.LoginDTO;
import service.CustomerService;
import service.UserService;

import static spark.Spark.post;
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
					return "Invalid credentials";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
	}
	
	
}
