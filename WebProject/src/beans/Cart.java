package beans;

import java.util.HashMap;
import java.util.HashSet;

public class Cart {

	private HashMap<Item, Integer> items = new HashMap<Item, Integer>();
	private String customer;
	private Double total;
	
	public Cart() {
	}
	
	
	public Cart(HashMap<Item, Integer> items, String customer, Double total) {
		super();
		this.items = items;
		this.customer = customer;
		this.total = total;
	}


	public Cart(String username) {
		this.customer = username;
	}


	public HashMap<Item, Integer> getItems() {
		return items;
	}


	public void setItems(HashMap<Item, Integer> items) {
		this.items = items;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
	
}
