package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Order;
import dao.OrderDAO;

public class OrderService {

	private OrderDAO orderDAO;
	private ImageService imageService;

	public OrderService(OrderDAO orderDAO, ImageService imageService) {
		super();
		this.orderDAO = orderDAO;
		this.imageService = imageService;
	}

	public ArrayList<Order> getAll() {
		return orderDAO.getAll();
	}

	public Order create(Order newOrder) throws IOException {
		return orderDAO.create(newOrder);
	}
	
}
