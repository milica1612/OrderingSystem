package controller;

import static spark.Spark.post;

import java.util.ArrayList;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.put;

import com.google.gson.Gson;

import beans.Comment;
import beans.Customer;
import beans.Restaurant;
import service.CommentService;
import service.RestaurantService;
import spark.Session;

public class CommentController {
	private CommentService commentService;
	private RestaurantService restaurantService;
	private static Gson gson = new Gson();
	
	public CommentController(CommentService commentService, RestaurantService restaurantService) {
		super();
		this.commentService = commentService;
		this.restaurantService = restaurantService;
		
		post("/comments/commentAndRate", (req, res) -> {
			res.type("application/json");
			
			try {
				Session session = req.session(true);
				Customer logged = session.attribute("logged");
				if(logged == null) {
					return null;
				}
				Comment comment = gson.fromJson(req.body(), Comment.class);
				comment.setCustomer(logged);
				String rand = "";
				String chars = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
			  	for (int i = 0; i < 10; i++) {
			      rand += chars.toCharArray()[new Random().nextInt(chars.length())];
			  	}
			  	
			  	comment.setCode(rand);
				Comment created = commentService.create(comment);
				Double rating = commentService.calculateRating(comment.getRestaurant());
				Restaurant restaurant = comment.getRestaurant();
				restaurant.setRating(rating);
				restaurantService.setRating(restaurant);
				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("/comments/getAllApproved/:restaurant", (req, res) -> {
			res.type("application/json");
			try {	
				ArrayList<Comment> comments = commentService.getAllApprovedForRestaurant(req.params("restaurant"));
				return gson.toJson(comments);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("/comments/getAll/:restaurant", (req, res) -> {
			res.type("application/json");
			try {	
				ArrayList<Comment> comments = commentService.getAllForRestaurant(req.params("restaurant"));
				return gson.toJson(comments);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/comments/approve", (req, res) -> {
			res.type("application/json");
			try {	
				Comment comment = gson.fromJson(req.body(), Comment.class);
				System.out.println(comment.getContent());
				Comment newComment = commentService.approve(comment);
				return gson.toJson(newComment);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		put("/comments/decline", (req, res) -> {
			res.type("application/json");
			try {	
				Comment comment = gson.fromJson(req.body(), Comment.class);
				Comment newComment = commentService.decline(comment);
				return gson.toJson(newComment);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
	
}
