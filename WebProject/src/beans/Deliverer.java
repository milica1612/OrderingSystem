package beans;

import java.util.ArrayList;
import java.util.Date;

public class Deliverer extends User {

	private ArrayList<Order> orders = new ArrayList<Order>();
	
	public Deliverer() {
		super();
	}

	public Deliverer(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			UserType userType) {
		super(username, password, name, lastName, gender, dateOfBirth, userType);
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	
}
