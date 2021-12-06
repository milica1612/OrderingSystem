package beans;

import java.util.HashMap;
import java.util.HashSet;

public class Cart {

	private HashMap<Item, Integer> items;
	private Customer customer;
	private Double total;
	
	public Cart() {
	}
	
	
	public Cart(HashMap<Item, Integer> items, Customer customer, Double total) {
		super();
		this.items = items;
		this.customer = customer;
		this.total = total;
	}


	public HashMap<Item, Integer> getItems() {
		return items;
	}


	public void setItems(HashMap<Item, Integer> items) {
		this.items = items;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
	
}
