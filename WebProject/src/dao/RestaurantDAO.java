package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Customer;
import beans.Restaurant;

public class RestaurantDAO {
	private String file;

	public RestaurantDAO(String file) {
		super();
		this.file = file;
	}
	
	public void saveAll(ArrayList<Restaurant> restaurants) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(restaurants, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Restaurant> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Restaurant>>(){}.getType();
       BufferedReader br = null;
       try {
           br = new BufferedReader(new FileReader(this.file));
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       
       return gson.fromJson(br, token);
	}

	public Restaurant create(Restaurant newRestaurant) throws IOException {
		ArrayList<Restaurant> restaurants = getAll();
		if(restaurants == null) {
			restaurants = new ArrayList<Restaurant>();
		}
		restaurants.add(newRestaurant);
		saveAll(restaurants);
		return newRestaurant;
	}
}
