package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import beans.CartItem;
import beans.Order;
import beans.Restaurant;
import beans.User;
import service.OrderService;
import spark.Session;

public class OrderController {

	private OrderService orderService;
	private static Gson gson = new Gson();
	
	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	
		get("/orders/getByCustomer", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
			
				return gson.toJson(orderService.getByCustomer(logged.getUsername()));	
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		post("/orders/create", (req,res) -> {
			res.type("application/json");
			
			try {
				Order newOrder = gson.fromJson(req.body(), Order.class);
				
				Session session = req.session(true);
				User logged = session.attribute("logged");		
				String customer = logged.getName() + " " + logged.getLastName();
				newOrder.setCustomer(customer);
				
				Order created = orderService.create(newOrder);
				return gson.toJson(created);
				
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		post("/orders/cancelOrder", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				Order order = gson.fromJson(req.body(), Order.class);	
				return gson.toJson(orderService.removeOrder(logged.getUsername(), order));
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("/orders/getByRestaurant/:name", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				ArrayList<Order> orders = orderService.getByRestaurant(req.params("name"), logged.getUsername());
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/orders/getByPrice/:from/:to", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				String from = req.params("from");
				Double priceFrom = Double.parseDouble(from);
				
				String to = req.params("to");
				Double priceTo = Double.parseDouble(to);
				
				ArrayList<Order> orders = orderService.getByPrice(priceFrom, priceTo, logged.getUsername());
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/orders/getByDate/:from/:to", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				String from = req.params("from");
				Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from); 
				
				String to = req.params("to");
				Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to); 
				
				ArrayList<Order> orders = orderService.getByDate(dateFrom, dateTo, logged.getUsername());
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
	}
}
