package beans;

import java.util.Date;
import java.util.HashMap;

public class Order {

	private String code;
	private HashMap<Item, Integer> items;
	private Restaurant restaurant;
	private Date dateAndTime;
	private Double total;
	private Customer customer;
	private OrderStatus orderStatus;
	
	public Order() {
		
	}
	
	public Order(String code, HashMap<Item, Integer> items, Restaurant restaurant, Date dateAndTime, Double total,
			Customer customer, OrderStatus orderStatus) {
		super();
		this.code = code;
		this.items = items;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.total = total;
		this.customer = customer;
		this.orderStatus = orderStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HashMap<Item, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<Item, Integer> items) {
		this.items = items;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
