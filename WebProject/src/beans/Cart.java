package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Cart {

	private ArrayList<CartItem> items;
	private String customer;
	private Double total;
	
	public Cart() {
	}
	
	
	public Cart(ArrayList<CartItem> items, String customer, Double total) {
		super();
		this.items = items;
		this.customer = customer;
		this.total = total;
	}


	public Cart(String username) {
		this.customer = username;
	}


	public ArrayList<CartItem> getItems() {
		return items;
	}


	public void setItems(ArrayList<CartItem> items) {
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
