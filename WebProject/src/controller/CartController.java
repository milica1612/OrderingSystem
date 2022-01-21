package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import com.google.gson.Gson;

import beans.Cart;
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
		
		get("/carts/getByCustomerName", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User loggedUser = session.attribute("user");
				return gson.toJson(cartService.getByCustomerName(loggedUser.getUsername()));
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
		
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
		
		get("carts/getByCustomer", (req, res) -> {
			res.type("application/json");
			try {
				Session session = req.session(true);
				User logged = session.attribute("logged");
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