package beans;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User{

	private ArrayList<Order> orders = new ArrayList<Order>();
	private Cart cart = new Cart();
	private int points;
	private CustomerType customerType;
	
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			UserType userType) {
		super(username, password, name, lastName, gender, dateOfBirth, userType);
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
}
