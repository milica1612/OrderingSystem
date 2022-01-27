package service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import beans.Customer;
import beans.Cart;
import beans.CartItem;
import beans.Order;
import beans.OrderStatus;
import beans.Restaurant;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dto.OrderSearchDTO;

public class OrderService {

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private CartDAO cartDAO;
	private RestaurantDAO restaurantDAO;
	private ImageService imageService;

	public OrderService(OrderDAO orderDAO, CustomerDAO customerDAO, CartDAO cartDAO, RestaurantDAO restaurantDAO, ImageService imageService) {
		super();
		this.orderDAO = orderDAO;
		this.customerDAO = customerDAO;
		this.imageService = imageService;
		this.cartDAO = cartDAO;
		this.restaurantDAO = restaurantDAO;
	}

	public ArrayList<Order> getAll() {
		return orderDAO.getAll();
	}

	public Order create(Order newOrder) throws IOException {
		Order created = orderDAO.create(newOrder);
		double points = newOrder.getCart().getTotal()/1000*133;
		ArrayList<Customer> customers = customerDAO.getAll();
		for(Customer customer: customers) {
			if(customer.getUsername().equals(newOrder.getCart().getCustomer())) {
				customer.setPoints(customer.getPoints() + points);
				ArrayList<Order> orders = customer.getOrders();
				orders.add(created);
				customer.setOrders(orders);
				break;
			}
		}
		
		ArrayList<Cart> carts = cartDAO.getAll();
		for(Cart cart : carts) {
			if(cart.getCustomer().equals(newOrder.getCart().getCustomer())) {
				carts.remove(cart);
				break;
			}
		}
		
		cartDAO.saveAll(carts);
		customerDAO.saveAll(customers);
		return created;
	}

	public ArrayList<Order> getByCustomer(String username) {
		Customer customer = customerDAO.getByUsername(username);
		return customer.getOrders();
	}

	public Order removeOrder(String username, Order order) throws IOException {
		ArrayList<Order> allOrders = orderDAO.getAll();
		ArrayList<Customer> customers = customerDAO.getAll();
		double points = order.getCart().getTotal()/1000*133*4;
		for(Customer c: customers) {
				if(c.getUsername().equals(username)) {
					ArrayList<Order> orders = c.getOrders();
					for(Order o: orders) {
						if(o.getCode().equals(order.getCode())){
							c.setPoints(c.getPoints() - points);
							o.setOrderStatus(OrderStatus.CANCELED);
							if(c.getPoints() < 0) {
								c.setPoints(0);
							}
							break;
						}
					}
					c.setOrders(orders);
				}
			}
		
		orderDAO.saveAll(allOrders);
		customerDAO.saveAll(customers);
		return order;
	}

	public ArrayList<Order> getByRestaurant(String restaurant, String username) {
		Customer customer = customerDAO.getByUsername(username);
		ArrayList<Order> allOrders = customer.getOrders();
		ArrayList<Order> result = new ArrayList<Order>();
		if(allOrders != null) {
			for(Order order : allOrders) {
				if(order.getRestaurant().toLowerCase().contains(restaurant.toLowerCase().trim())) {
					result.add(order);
				}
			}
		}
	
		return result;
	}

	public ArrayList<Order> getByPrice(Double priceFrom, Double priceTo, String username) {
		
		if(priceTo == 0) {
			priceTo = 50000.00;
		}
		
		Customer customer = customerDAO.getByUsername(username);
		ArrayList<Order> allOrders = customer.getOrders();
		ArrayList<Order> result = new ArrayList<Order>();
		if(allOrders != null) {
			for(Order order : allOrders) {
				if(order.getCart().getTotal() >= priceFrom && order.getCart().getTotal() <= priceTo) {
					result.add(order);
				}
			}
		}
		return result;
	}

	public ArrayList<Order> getByDate(String dateFrom, String dateTo, String username) throws ParseException {
		
		if(dateFrom == null) {
			dateFrom = "2000-01-01";
		}
		if(dateTo == null) {
			dateTo = "2100-01-01";
		}
		Date from = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
		Date to = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo); 
		
		Customer customer = customerDAO.getByUsername(username);
		ArrayList<Order> allOrders = customer.getOrders();
		ArrayList<Order> result = new ArrayList<Order>();
		if(allOrders != null) {
			for(Order order : allOrders) {
				if(order.getDateAndTime().after(from) && order.getDateAndTime().before(to)) {
					result.add(order);
				}
			}
		}
		return result;
	}

	public ArrayList<Order> filtrateOrdersByRestaurantType(String param, ArrayList<Order> results) throws JsonSyntaxException, IOException {
		
		ArrayList<Order> orders = new ArrayList<Order>();
		for(Order order : results) {
			Restaurant restaurant = restaurantDAO.getRestaurant(order.getRestaurant());
			if(restaurant.getType().toLowerCase().contains(param.toLowerCase().trim())) {
				orders.add(order);
			}
		}
		
		return orders;
	}

	public ArrayList<Order> filtrateOrdersByOrderStatus(String param, ArrayList<Order> results) {
		
		ArrayList<Order> orders = new ArrayList<Order>();
		for(Order order : results) {
			if(order.getOrderStatus().toString().equals(param)) {
				orders.add(order);
			}
		}
		
		return orders;
	}

	public ArrayList<Order> getAllForRestaurant(String params) {
		ArrayList<Order> orders = getAll();
		ArrayList<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if(order.getRestaurant().equals(params)) {
				result.add(order);
			}
		}
	return result;
	}

	public ArrayList<Order> getForRestaurantByDate(String params, OrderSearchDTO orderDTO) throws ParseException {
		if(orderDTO.getDateFrom() == null) {
			orderDTO.setDateFrom("2000-01-01");
		}
		if(orderDTO.getDateTo() == null) {
			orderDTO.setDateTo("2100-01-01");
		}
		Date from = new SimpleDateFormat("yyyy-MM-dd").parse(orderDTO.getDateFrom());
		Date to = new SimpleDateFormat("yyyy-MM-dd").parse(orderDTO.getDateTo()); 
		
		ArrayList<Order> orders = getAll();
		ArrayList<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if(order.getRestaurant().equals(params)) {
				if(order.getDateAndTime().after(from) && order.getDateAndTime().before(to)) {
					result.add(order);
				}
			}
		}
	return result;
	}

	public ArrayList<Order> getForRestaurantByPrice(String params, OrderSearchDTO orderDTO) {
		if(orderDTO.getPriceTo() == 0) {
			orderDTO.setPriceTo(50000.00);
		}
		ArrayList<Order> orders = getAll();
		ArrayList<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if(order.getRestaurant().equals(params)) {
				System.out.println(orderDTO.getPriceFrom());
				System.out.println(orderDTO.getPriceTo());
				if(order.getTotal() >= orderDTO.getPriceFrom() && order.getTotal() <= orderDTO.getPriceTo()) {
					result.add(order);
				}
			}
		}
	return result;
		
	}

	public Order updateStatus(Order o) throws IOException {
		ArrayList<Order> orders = getAll();
		for (Order order : orders) {
			if(order.getCode().equals(o.getCode())) {
				order.setOrderStatus(o.getOrderStatus());
				break;
			}
		}
		orderDAO.saveAll(orders);
		return o;
	}	
}
