package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonSyntaxException;

import beans.Cart;
import beans.CartItem;
import beans.Item;
import dao.CartDAO;

public class CartService {

	private CartDAO cartDAO;
	private ImageService imageService;

	public CartService(CartDAO cartDAO, ImageService imageService) {
		super();
		this.cartDAO = cartDAO;
		this.imageService = imageService;
	}

	public ArrayList<Cart> getAll() {
		return cartDAO.getAll();
	}
	
	public Cart getByCustomerName(String customer) throws JsonSyntaxException, IOException  {
		return cartDAO.getCart(customer);
	}
	
	public Item addItemToCart(String customer, Item item, int quantity) throws JsonSyntaxException, IOException {
		
		Cart cart = getByCustomerName(customer);	
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
					c.setTotal(total);
				}
			}
		}
		else {
			allCarts = new ArrayList<Cart>();
			cart = new Cart();
			cart.setCustomer(customer);
			ArrayList<CartItem> items = new ArrayList<CartItem>();
			CartItem ci = new CartItem(item, quantity);
			items.add(ci);
			cart.setItems(items);
			double total =  item.getPrice()*quantity;
			cart.setTotal(total);
			allCarts.add(cart);
		}
		
		cartDAO.saveAll(allCarts);
		return item;
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
