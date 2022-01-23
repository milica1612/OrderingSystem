package beans;

import java.util.Date;

public class Order {

	private String code;
	private Cart cart;
	private String restaurant;
	private Date dateAndTime;
	private OrderStatus orderStatus;
	private String customer;
	public Order() {
		
	}
	
	public Order(String code, Cart cart, String restaurant, Date dateAndTime, OrderStatus orderStatus,
			String customer) {
		super();
		this.code = code;
		this.cart = cart;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.orderStatus = orderStatus;
		this.customer = customer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
