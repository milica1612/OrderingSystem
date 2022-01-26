package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.CartItem;
import beans.Order;
import beans.Restaurant;
import beans.User;
import dto.OrderSearchDTO;
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
				newOrder.setTotal(newOrder.getCart().getTotal());
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
		
		post("/orders/getByPrice", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");				
				OrderSearchDTO orderDTO = gson.fromJson(req.body(), OrderSearchDTO.class);

				ArrayList<Order> orders = orderService.getByPrice(orderDTO.getPriceFrom(), orderDTO.getPriceTo(), logged.getUsername());
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		post("/orders/getByDate", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				OrderSearchDTO orderDTO = gson.fromJson(req.body(), OrderSearchDTO.class);
				
				ArrayList<Order> orders = orderService.getByDate(orderDTO.getDateFrom(), orderDTO.getDateTo(), logged.getUsername());
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		put("/orders/filtrate/type/:filter", (req,res) -> {
			res.type("application/json");
			try {
				String param = req.params("filter");
				ArrayList<Order> results = gson.fromJson(req.body(), new TypeToken<ArrayList<Order>>(){}.getType());
				ArrayList<Order> filtratedOrders = orderService.filtrateOrdersByRestaurantType(param, results);
				return gson.toJson(filtratedOrders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/orders/filtrate/status/:filter", (req,res) -> {
			res.type("application/json");
			try {
				String param = req.params("filter");
				ArrayList<Order> results = gson.fromJson(req.body(), new TypeToken<ArrayList<Order>>(){}.getType());
				ArrayList<Order> filtratedOrders = orderService.filtrateOrdersByOrderStatus(param, results);
				return gson.toJson(filtratedOrders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
}
