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

public class CustomerDAO {

	private String file;
	
	public CustomerDAO(String file) {
		super();
		this.file = file;
	}

	public Customer create(Customer newCustomer) throws IOException {
		ArrayList<Customer> customers = getAll();
		if(customers == null) {
			customers = new ArrayList<Customer>();
		}
		customers.add(newCustomer);
		saveAll(customers);
		return newCustomer;
	}
	
	public void saveAll(ArrayList<Customer> customers) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(customers, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Customer> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Customer>>(){}.getType();
         BufferedReader br = null;
         try {
             br = new BufferedReader(new FileReader(this.file));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         
         return gson.fromJson(br, token);
	}

}
