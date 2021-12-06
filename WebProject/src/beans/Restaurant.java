package beans;

import java.util.ArrayList;

public class Restaurant {

	private String name;
	private String type;
	private ArrayList<Item> items;
	private Boolean isOpen; 
	private Location location;
	private String logo;
	
	public Restaurant() {
		
	}
	
	public Restaurant(String name, String type, ArrayList<Item> items, Boolean isOpen, Location location, String logo) {
		super();
		this.name = name;
		this.type = type;
		this.items = items;
		this.isOpen = isOpen;
		this.location = location;
		this.logo = logo;
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
	
	
}
