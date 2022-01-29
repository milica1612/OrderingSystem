package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import beans.Cart;
import beans.CartItem;
import beans.Customer;
import beans.Item;
import dao.CartDAO;
import dao.CustomerDAO;

public class CartService {

	private CartDAO cartDAO;
	private CustomerDAO customerDAO;
	private ImageService imageService;

	public CartService(CartDAO cartDAO, CustomerDAO customerDAO, ImageService imageService) {
		super();
		this.cartDAO = cartDAO;
		this.imageService = imageService;
		this.customerDAO = customerDAO;
	}

	public ArrayList<Cart> getAll() {
		return cartDAO.getAll();
	}
	
	public Cart getByCustomerName(String customer) throws JsonSyntaxException, IOException  {
		return cartDAO.getCart(customer);
	}
	
	public Item addItemToCart(String customerName, Item item, int quantity) throws JsonSyntaxException, IOException {
		
		Cart cart = getByCustomerName(customerName);
		Customer customer = customerDAO.getByUsername(customerName);
		ArrayList<Cart> allCarts = cartDAO.getAll();
		boolean found = false;
		if(allCarts != null && cart != null) {
			for(Cart c: allCarts) {
				if(c.getCustomer().equals(cart.getCustomer())) {
					ArrayList<CartItem> items = cart.getItems();
					for(CartItem i: items) {
						if(i.getItem().getName().equals(item.getName())){
							i.setQuantity(i.getQuantity() + quantity);
							found = true;
							break;
						}		
					}
					if(!found) {
						CartItem ci = new CartItem(item, quantity);
						items.add(ci);
					}
					c.setItems(items);
					double total = c.getTotal() + item.getPrice()*quantity;
					if(customer != null && customer.getCustomerType() != null) {
						total = total * customer.getCustomerType().getDiscount();
					}
			
					c.setTotal(total);
					break;
				}
			}
		}
		else {
			allCarts = new ArrayList<Cart>();
			cart = new Cart();
			cart.setCustomer(customerName);
			ArrayList<CartItem> items = new ArrayList<CartItem>();
			CartItem ci = new CartItem(item, quantity);
			items.add(ci);
			cart.setItems(items);
			double total =  item.getPrice()*quantity;
			if(customer != null && customer.getCustomerType() != null) {
				total = total * customer.getCustomerType().getDiscount();
			}
			cart.setTotal(total);
			allCarts.add(cart);
		}
		
		cartDAO.saveAll(allCarts);
		return item;
	}
	
	public CartItem removeItem(String username, CartItem cartItem) throws IOException {
		ArrayList<Cart> allCarts = cartDAO.getAll();
		Customer customer = customerDAO.getByUsername(username);
		if(allCarts != null) {
			for(Cart c: allCarts) {
				if(c.getCustomer().equals(username)) {
					ArrayList<CartItem> items = c.getItems();
					for(CartItem i: items) {
						if(i.getItem().getName().equals(cartItem.getItem().getName())){
							if(customer != null && customer.getCustomerType() != null) {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity()*customer.getCustomerType().getDiscount());				
							}else {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity());
							}
							items.remove(i);
							break;
						}
					}
					c.setItems(items);
				}
			}
		}
		cartDAO.saveAll(allCarts);
		return cartItem;
	}
	
	public CartItem editItemQuantity(String username, CartItem cartItem) throws IOException {
		ArrayList<Cart> allCarts = cartDAO.getAll();
		Customer customer = customerDAO.getByUsername(username);
		if(allCarts != null) {
			for(Cart c: allCarts) {
				if(c.getCustomer().equals(username)) {
					ArrayList<CartItem> items = c.getItems();
					for(CartItem i: items) {
						if(i.getItem().getName().equals(cartItem.getItem().getName())){
							if(customer != null && customer.getCustomerType() != null) {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity()*customer.getCustomerType().getDiscount());				
							}else {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity());
							}
							i.setQuantity(cartItem.getQuantity());
							break;
						}		
					}
					c.setItems(items);
					if(customer != null && customer.getCustomerType() != null) {
						c.setTotal(c.getTotal() + cartItem.getItem().getPrice()*cartItem.getQuantity()*customer.getCustomerType().getDiscount());				
					}else {
						c.setTotal(c.getTotal() + cartItem.getItem().getPrice()*cartItem.getQuantity());
					}
				}
			}
		}
		cartDAO.saveAll(allCarts);
		return cartItem;
		}
	
	
	public Cart getItemsFromCart(String username) {
		ArrayList<Cart> carts = getAll();
		if(carts != null) {
			for(Cart cart: carts) {
				if(cart.getCustomer().equals(username)) {
					return cart;
				}
			}
		}
		return null;
	}
	
}
