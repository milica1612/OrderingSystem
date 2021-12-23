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

import beans.Deliverer;
import beans.Gender;
import beans.Manager;
import beans.User;
import dto.UserDTO;

public class DelivererDAO {
	private String file;
		
	public DelivererDAO(String file) {
		super();
		this.file = file;
	}

	public Deliverer create(Deliverer newDeliverer) throws IOException {
		ArrayList<Deliverer> deliverers = getAll();
		if(deliverers == null) {
			deliverers = new ArrayList<Deliverer>();
		}
		deliverers.add(newDeliverer);
		saveAll(deliverers);
		return newDeliverer;
	}
	
	public void saveAll(ArrayList<Deliverer> deliverers) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(deliverers, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Deliverer> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Deliverer>>(){}.getType();
         BufferedReader br = null;
         try {
             br = new BufferedReader(new FileReader(this.file));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         
         return gson.fromJson(br, token);
	}

	public Deliverer getByUsername(String username) {
		ArrayList<Deliverer> deliverers = getAll();
		if(deliverers != null) {
			for (Deliverer deliverer : deliverers) {
				if(deliverer.getUsername().equals(username)) {
					return deliverer;
				}
			}
		}
		return null;
	}

	public User update(User logged, UserDTO userDTO) throws ParseException, IOException {
		ArrayList<Deliverer> users = getAll();
		if(users != null) {
			for (Deliverer user : users) {
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
