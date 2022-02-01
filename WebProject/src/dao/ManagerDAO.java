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
import beans.Gender;
import beans.Manager;
import beans.User;
import dto.UserDTO;

public class ManagerDAO {
private String file;
	
	public ManagerDAO(String file) {
		super();
		this.file = file;
	}

	public Manager create(Manager newManager) throws IOException {
		ArrayList<Manager> managers = getAll();
		if(managers == null) {
			managers = new ArrayList<Manager>();
		}
		managers.add(newManager);
		saveAll(managers);
		return newManager;
	}
	
	public void saveAll(ArrayList<Manager> managers) throws IOException {
		 Gson gson = new Gson();
	     FileWriter fw = new FileWriter(this.file);
	     gson.toJson(managers, fw);
	     fw.flush();
	     fw.close();
	}
	
	public ArrayList<Manager> getAll(){
		 Gson gson = new Gson();
	     Type token = new TypeToken<ArrayList<Manager>>(){}.getType();
         BufferedReader br = null;
         try {
             br = new BufferedReader(new FileReader(this.file));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         
         return gson.fromJson(br, token);
	}

	public Manager getByUsername(String username) {
		ArrayList<Manager> managers = getAll();
		if(managers != null) {
			for (Manager manager : managers) {
				if(manager.getUsername().equals(username)) {
					return manager;
				}
			}
		}
		return null;
	}

	public User update(User logged, UserDTO userDTO) throws ParseException, IOException {
		ArrayList<Manager> users = getAll();
		if(users != null) {
			for (Manager user : users) {
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
