package service;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonElement;

import beans.Customer;
import beans.CartItem;
import beans.Order;
import dao.CustomerDAO;
import dao.OrderDAO;

public class OrderService {

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private ImageService imageService;

	public OrderService(OrderDAO orderDAO, CustomerDAO customerDAO, ImageService imageService) {
		super();
		this.orderDAO = orderDAO;
		this.customerDAO = customerDAO;
		this.imageService = imageService;
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
		
		customerDAO.saveAll(customers);
		return created;
	}

	public ArrayList<Order> getByCustomer(String username) {
		Customer customer = customerDAO.getByUsername(username);
		return customer.getOrders();
	}
	
}
