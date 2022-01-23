package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import beans.Cart;
import beans.Order;
import beans.OrderStatus;
import beans.User;

public class OrderDAO {

private String file;
	
	public OrderDAO(String file) {
		super();
		this.file = file;
	}
	
	public void saveAll(ArrayList<Order> orders) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(orders, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Order> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Order>>(){}.getType();
     BufferedReader br = null;
     try {
         br = new BufferedReader(new FileReader(this.file));
     } catch (FileNotFoundException e) {
         e.printStackTrace();
     }
     
     return gson.fromJson(br, token);
	}

	public Order create(Order newOrder) throws IOException {
		ArrayList<Order> orders = getAll();
		if(orders == null) {
			orders = new ArrayList<Order>();
		}
		newOrder.setDateAndTime(new Date());
		newOrder.setOrderStatus(OrderStatus.PROCESSING);	
		orders.add(newOrder);
		saveAll(orders);
		return newOrder;
	}
	
	
}
