package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import beans.Cart;

public class CartDAO {
	private String file;
	
	public CartDAO(String file) {
		super();
		this.file = file;
	}
	
	public void saveAll(ArrayList<Cart> carts) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(carts, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Cart> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Cart>>(){}.getType();
      BufferedReader br = null;
      try {
          br = new BufferedReader(new FileReader(this.file));
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
      return gson.fromJson(br, token);
	}
	
	public Cart getCart(String customer) throws JsonSyntaxException, IOException {
		ArrayList<Cart> carts = getAll();
		if(carts != null) {
			for (Cart cart: carts) {
				if(cart.getCustomer().equals(customer)) {
					return cart;
				}
			}
		}
		return null;
	}
}
