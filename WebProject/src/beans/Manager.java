package beans;

import java.util.Date;

public class Manager extends User{

	private Restaurant restaurant;

	public Manager() {
		super();
	}

	public Manager(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			UserType userType) {
		super(username, password, name, lastName, gender, dateOfBirth, userType);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}	
	
}
