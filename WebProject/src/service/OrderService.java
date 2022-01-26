package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonElement;

import beans.Customer;
import beans.Cart;
import beans.CartItem;
import beans.Order;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.OrderDAO;

public class OrderService {

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private CartDAO cartDAO;
	private ImageService imageService;

	public OrderService(OrderDAO orderDAO, CustomerDAO customerDAO, CartDAO cartDAO, ImageService imageService) {
		super();
		this.orderDAO = orderDAO;
		this.customerDAO = customerDAO;
		this.imageService = imageService;
		this.cartDAO = cartDAO;
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
							if(c.getPoints() < 0) {
								c.setPoints(0);
							}
							orders.remove(o);
							break;
						}
					}
					c.setOrders(orders);
					allOrders.remove(order);
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

	public ArrayList<Order> getByDate(Date dateFrom, Date dateTo, String username) {
		Customer customer = customerDAO.getByUsername(username);
		ArrayList<Order> allOrders = customer.getOrders();
		ArrayList<Order> result = new ArrayList<Order>();
		if(allOrders != null) {
			for(Order order : allOrders) {
				if(order.getDateAndTime().after(dateFrom) && order.getDateAndTime().before(dateTo)) {
					result.add(order);
				}
			}
		}
		return result;
	}
}
