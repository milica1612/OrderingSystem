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
import beans.User;

public class UserDAO {
	private String file;
	
	public UserDAO(String file) {
		super();
		this.file = file;
	}
	
	public void saveAll(ArrayList<User> users) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(users, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<User> getAll(){
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

	public User getByUsername(String username) {
		ArrayList<User> users = getAll();
		if(users != null) {
			for (User user : users) {
				if(user.getUsername().equals(username)) {
					return user;
				}
			}
		}
		return null;
	}
	

}
