package service;

import java.io.IOException;
import java.util.ArrayList;
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
					double total;
					if(customer != null && customer.getCustomerType() != null) {
						total = c.getTotal() + item.getPrice()*quantity* customer.getCustomerType().getDiscount();
					}else {
						total = c.getTotal() + item.getPrice()*quantity;
					}
			
					c.setTotal(total);
					c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
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
			double total;
			if(customer != null && customer.getCustomerType() != null) {
				total = (item.getPrice()*quantity)* customer.getCustomerType().getDiscount();
			}else {
				total = item.getPrice()*quantity;
			}
			cart.setTotal(total);
			cart.setTotal(Math.round(cart.getTotal()*100.0)/100.0);
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
								c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
							}else {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity());
								c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
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
								c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
							}else {
								c.setTotal(c.getTotal() - i.getItem().getPrice()*i.getQuantity());
								c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
							}
							i.setQuantity(cartItem.getQuantity());
							break;
						}		
					}
					c.setItems(items);
					if(customer != null && customer.getCustomerType() != null) {
						c.setTotal(c.getTotal() + cartItem.getItem().getPrice()*cartItem.getQuantity()*customer.getCustomerType().getDiscount());				
						c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
					}else {
						c.setTotal(c.getTotal() + cartItem.getItem().getPrice()*cartItem.getQuantity());
						c.setTotal(Math.round(c.getTotal()*100.0)/100.0);
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
