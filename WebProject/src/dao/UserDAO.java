package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Customer;
import beans.Gender;
import beans.User;
import dto.UserDTO;

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
	     Type token = new TypeToken<ArrayList<User>>(){}.getType();
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

	public User update(User logged, UserDTO userDTO) throws ParseException, IOException {
		ArrayList<User> users = getAll();
		if(users != null) {
			for (User user : users) {
				if(user.getUsername().equals(logged.getUsername())) {
					user.setUsername(userDTO.getUsername());
					user.setName(userDTO.getName());
					user.setLastName(userDTO.getLastName());
					user.setGender(Gender.values()[userDTO.getGender()]);
					Date date=new SimpleDateFormat("yyyy-MM-dd").parse(userDTO.getDateOfBirth());
					user.setDateOfBirth(date);
					saveAll(users);
					return user;
				}
			}
		}
		return null;
	}

}
