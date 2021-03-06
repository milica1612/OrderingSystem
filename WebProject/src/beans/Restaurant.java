package beans;

import java.util.ArrayList;

public class Restaurant {

	private String name;
	private String type;
	private ArrayList<Item> items;
	private Boolean isOpen; 
	private Location location;
	private String logo;
	private double rating;
	private Boolean isDeleted = false;
	
	public Restaurant() {
		
	}
	
	public Restaurant(String name, String type, ArrayList<Item> items, Boolean isOpen, Location location, 
			String logo, double rating, Boolean isDeleted) {
		super();
		this.name = name;
		this.type = type;
		this.items = items;
		this.isOpen = isOpen;
		this.location = location;
		this.logo = logo;
		this.rating = rating;
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
