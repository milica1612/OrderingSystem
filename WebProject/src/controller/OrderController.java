package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import beans.CartItem;
import beans.Order;
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
	}
}
