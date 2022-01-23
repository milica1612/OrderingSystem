package controller;

import static spark.Spark.post;

import com.google.gson.Gson;

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
	}
}
