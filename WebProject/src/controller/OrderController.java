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
import beans.Customer;
import beans.Order;
import beans.OrderStatus;
import beans.Restaurant;
import beans.User;
import dto.OrderRequest;
import dto.OrderSearchDTO;
import service.DelivererService;
import service.OrderService;
import spark.Session;

public class OrderController {

	private OrderService orderService;
	private DelivererService delivererService;
	private static Gson gson = new Gson();
	
	public OrderController(OrderService orderService, DelivererService delivererService) {
		super();
		this.orderService = orderService;
		this.delivererService = delivererService;
	
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
		
		get("/orders/getForRestaurant/:name", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Order> orders = orderService.getAllForRestaurant(req.params("name"));
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/orders/getCustomers/:name", (req, res) -> {
			res.type("application/json");
			try {
				ArrayList<Customer> customers = orderService.getCustomers(req.params("name"));
				return gson.toJson(customers);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/orders/getWithoutDeliverer", (req, res) -> {
			res.type("application/json");
			try {
				
				ArrayList<Order> orders = orderService.getAllWithoutDeliverer();
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		get("/orders/getByDeliverer", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				ArrayList<Order> orders = orderService.getByDeliverer(logged.getUsername());
				return gson.toJson(orders);
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
		
		put("orders/getForRestaurant/byDate/:name", (req, res) -> {
			res.type("application/json");
			try {
				OrderSearchDTO orderDTO = gson.fromJson(req.body(), OrderSearchDTO.class);
				ArrayList<Order> orders = orderService.getForRestaurantByDate(req.params("name"), orderDTO);
				return gson.toJson(orders);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
		put("orders/getForRestaurant/byPrice/:name", (req, res) -> {
			res.type("application/json");
			try {
				OrderSearchDTO orderDTO = gson.fromJson(req.body(), OrderSearchDTO.class);
				ArrayList<Order> orders = orderService.getForRestaurantByPrice(req.params("name"), orderDTO);
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
		
		put("/orders/changeStatus/preparing", (req,res) -> {
			res.type("application/json");
			try {
				Order order = gson.fromJson(req.body(), Order.class);
				order.setOrderStatus(OrderStatus.PREPARING);
				Order newOrder = this.orderService.updateStatus(order);
				return gson.toJson(newOrder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/orders/changeStatus/waitingForDelivery", (req,res) -> {
			res.type("application/json");
			try {
				Order order = gson.fromJson(req.body(), Order.class);
				order.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY);
				Order newOrder = this.orderService.updateStatus(order);
				return gson.toJson(newOrder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/orders/changeStatus/waitingForDeliveryApproval", (req,res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				Order order = gson.fromJson(req.body(), Order.class);
				Order newOrder = this.orderService.updateStatusDeliverer(order, logged.getUsername());
				return gson.toJson(newOrder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/orders/changeStatus/delivered", (req,res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				
				Order order = gson.fromJson(req.body(), Order.class);
				Order newOrder = this.orderService.updateToDelivered(order, logged.getUsername());
				return gson.toJson(newOrder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		put("/orders/changeStatus/inTransport", (req,res) -> {
			res.type("application/json");
			try {
				OrderRequest orderReq = gson.fromJson(req.body(), OrderRequest.class);
				orderReq.getOrder().setOrderStatus(OrderStatus.IN_TRANSPORT);
				Order newOrder = this.orderService.updateStatus(orderReq.getOrder());
				delivererService.changeOrderToInTransport(orderReq);
				return gson.toJson(newOrder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
	}
}
