package service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import beans.Customer;
import beans.CustomerType;
import beans.Deliverer;
import beans.Cart;
import beans.CartItem;
import beans.Order;
import beans.OrderStatus;
import beans.Restaurant;
import dao.CartDAO;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.OrderDAO;
import dao.RestaurantDAO;
import dto.OrderSearchDTO;

public class OrderService {

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private CartDAO cartDAO;
	private RestaurantDAO restaurantDAO;
	private DelivererDAO delivererDAO;
	private ImageService imageService;

	public OrderService(OrderDAO orderDAO, CustomerDAO customerDAO, CartDAO cartDAO, RestaurantDAO restaurantDAO, DelivererDAO delivererDAO, ImageService imageService) {
		super();
		this.orderDAO = orderDAO;
		this.customerDAO = customerDAO;
		this.imageService = imageService;
		this.cartDAO = cartDAO;
		this.restaurantDAO = restaurantDAO;
		this.delivererDAO = delivererDAO;
	}

	public ArrayList<Order> getAll() {
		return orderDAO.getAll();
	}

	public Order create(Order newOrder) throws IOException {
		double points = newOrder.getCart().getTotal()/1000*133;
		ArrayList<Customer> customers = customerDAO.getAll();
		for(Customer customer: customers) {
			if(customer.getUsername().equals(newOrder.getCart().getCustomer())) {
				
				customer.setPoints(customer.getPoints() + points);
				if(customer.getPoints() >= 200 && customer.getPoints() < 400) {
					customer.setCustomerType(new CustomerType("Bronze", 0.95, 200));
				}else if(customer.getPoints() >= 400 && customer.getPoints() < 650) {
					customer.setCustomerType(new CustomerType("Silver", 0.90, 400));
				}
				else if(customer.getPoints() >= 650) {
					customer.setCustomerType(new CustomerType("Gold", 0.85, 650));
				}
				ArrayList<Order> orders = customer.getOrders();
				orders.add(newOrder);
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
		
		Order created = orderDAO.create(newOrder);
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
		
		for (Order o : allOrders) {
			if(o.getCode().equals(order.getCode())) {
				o.setOrderStatus(OrderStatus.CANCELED);
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
		
		ArrayList<Order> result = new ArrayList<Order>();
		Customer customer = customerDAO.getByUsername(username);
		if(customer != null) {
			ArrayList<Order> allOrders = customer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(order.getCart().getTotal() >= priceFrom && order.getCart().getTotal() <= priceTo) {
						result.add(order);
					}
				}
			}
		}else {
			Deliverer deliverer = delivererDAO.getByUsername(username);	
			ArrayList<Order> allOrders = deliverer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(order.getCart().getTotal() >= priceFrom && order.getCart().getTotal() <= priceTo) {
						result.add(order);
					}
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
		
		ArrayList<Order> result = new ArrayList<Order>();
		Customer customer = customerDAO.getByUsername(username);
		if(customer != null) {
			ArrayList<Order> allOrders = customer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(order.getDateAndTime().after(from) && order.getDateAndTime().before(to)) {
						result.add(order);
					}
				}
			}
		}else {
			Deliverer deliverer = delivererDAO.getByUsername(username);	
			ArrayList<Order> allOrders = deliverer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(order.getDateAndTime().after(from) && order.getDateAndTime().before(to)) {
						result.add(order);
					}
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
		if(orders != null) {
			for (Order order : orders) {
				if(order.getRestaurant().equals(params)) {
					result.add(order);
				}
			}
		}
	return result;
	}
	
	public boolean canRestaurantBeDeleted(String params) {
		ArrayList<Order> orders = getAll();
		if(orders != null) {
			for (Order order : orders) {
				if(order.getRestaurant().equals(params)) {
					if(order.getOrderStatus() != OrderStatus.DELIVERED && order.getOrderStatus() != OrderStatus.CANCELED)
						return false;
				}
			}
		}
	return true;
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
		
		ArrayList<Customer> customers = customerDAO.getAll();
		for (Customer customer : customers) {
			if(customer.getUsername().equals(o.getCart().getCustomer())) {
				for (Order order : customer.getOrders()) {
					if(order.getCode().equals(o.getCode())) {
						order.setOrderStatus(o.getOrderStatus());
						break;
					}
				}
			}
		}
		customerDAO.saveAll(customers);
		return o;
	}
	
	public Order updateStatusDeliverer(Order o, String username) throws IOException {
		
		ArrayList<Deliverer> deliverers = delivererDAO.getAll();
		ArrayList<Order> orders = getAll();
		
		for (Order order : orders) {
			if(order.getCode().equals(o.getCode())) {
				order.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY_APPROVAL);
				break;
			}
		}
		for(Deliverer deliverer: deliverers) {
			if(deliverer.getUsername().equals(username)) {
				ArrayList<Order> delOrders = deliverer.getOrders();
				o.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY_APPROVAL);
				delOrders.add(o);
				break;
			}
		}
		ArrayList<Customer> customers = customerDAO.getAll();
		for (Customer customer : customers) {
			if(customer.getUsername().equals(o.getCart().getCustomer())) {
				for (Order order : customer.getOrders()) {
					if(order.getCode().equals(o.getCode())) {
						order.setOrderStatus(OrderStatus.WAITING_FOR_DELIVERY_APPROVAL);
						break;
					}
				}
			}
		}
		customerDAO.saveAll(customers);
		delivererDAO.saveAll(deliverers);
		orderDAO.saveAll(orders);
		return o;
	}
	
	public Order updateToDelivered(Order o, String username) throws IOException {
		
		ArrayList<Deliverer> deliverers = delivererDAO.getAll();
		ArrayList<Order> orders = getAll();
		
		for (Order order : orders) {
			if(order.getCode().equals(o.getCode())) {
				order.setOrderStatus(OrderStatus.DELIVERED);
				break;
			}
		}
		for(Deliverer deliverer: deliverers) {
			if(deliverer.getUsername().equals(username)) {
				ArrayList<Order> delOrders = deliverer.getOrders();
				for (Order order : delOrders) {
					if(order.getCode().equals(o.getCode())) {
						order.setOrderStatus(OrderStatus.DELIVERED);
						break;
					}
				}
			}
		}
		
		ArrayList<Customer> customers = customerDAO.getAll();
		for (Customer customer : customers) {
			if(customer.getUsername().equals(o.getCart().getCustomer())) {
				for (Order order : customer.getOrders()) {
					if(order.getCode().equals(o.getCode())) {
						order.setOrderStatus(OrderStatus.DELIVERED);
						break;
					}
				}
			}
		}
		
		customerDAO.saveAll(customers);
		delivererDAO.saveAll(deliverers);
		orderDAO.saveAll(orders);
		return o;
	}

	public ArrayList<Order> getAllWithoutDeliverer() {
		ArrayList<Order> orders = getAll();
		ArrayList<Order> result = new ArrayList<Order>();
		for (Order order : orders) {
			if(order.getOrderStatus().equals(OrderStatus.WAITING_FOR_DELIVERY)) {
				result.add(order);
			}
		}
		return result;
	}

	public ArrayList<Order> getByDeliverer(String username) {
		Deliverer deliverer = delivererDAO.getByUsername(username);
		return deliverer.getOrders();
	}
	
	public ArrayList<Order> getUndeliveredOrders(String username){
		ArrayList<Order> result = new ArrayList<Order>();
		Customer customer = customerDAO.getByUsername(username);
		if(customer != null) {
			ArrayList<Order> allOrders = customer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(!order.getOrderStatus().equals(OrderStatus.DELIVERED)
							&& !order.getOrderStatus().equals(OrderStatus.CANCELED)) {
						result.add(order);
					}
				}
			}
		}else {
			Deliverer deliverer = delivererDAO.getByUsername(username);	
			ArrayList<Order> allOrders = deliverer.getOrders();
			if(allOrders != null) {
				for(Order order : allOrders) {
					if(!order.getOrderStatus().equals(OrderStatus.DELIVERED)){
						result.add(order);
					}
				}
			}
		}
			return result;
	}

	public ArrayList<Customer> getCustomers(String params) {
		ArrayList<Order> orders = getAllForRestaurant(params);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		boolean contains = false;
		for (Order order : orders) {
			Customer customer = customerDAO.getByUsername(order.getCart().getCustomer());
			for (Customer customer2 : customers) {
				if(customer2.getUsername().equals(customer.getUsername())) {
					contains = true;
					break;
				}
			}
				if(!contains) {
					customers.add(customer);
				}
				contains = false;
			
		}
		return customers;
	}
}
