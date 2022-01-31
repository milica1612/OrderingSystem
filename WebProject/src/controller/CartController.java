package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import com.google.gson.Gson;

import beans.Cart;
import beans.CartItem;
import beans.Item;
import beans.User;
import service.CartService;
import spark.Session;

public class CartController {

	private CartService cartService;
	private static Gson gson = new Gson();
	
	public CartController(CartService cartService) {
		super();
		this.cartService = cartService;
		
		post("/carts/addItemToCart/:quantity", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				Item item = gson.fromJson(req.body(), Item.class);
				String quantityString = req.params("quantity");	
				int quantity = Integer.parseInt(quantityString);
				
				return gson.toJson(cartService.addItemToCart(logged.getUsername(), item, quantity));
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		post("/carts/removeItem", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				CartItem cartItem = gson.fromJson(req.body(), CartItem.class);	
				return gson.toJson(cartService.removeItem(logged.getUsername(), cartItem));
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		post("/carts/editItemQuantity", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				CartItem cartItem = gson.fromJson(req.body(), CartItem.class);	
				return gson.toJson(cartService.editItemQuantity(logged.getUsername(), cartItem));	
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});
		
		get("carts/getByCustomer", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
				if(logged == null) {
					return null;
				}
				Cart cart = cartService.getItemsFromCart(logged.getUsername());
				if(cart == null) {
					return "";
				}else {
					return gson.toJson(cart);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		});
	}
}