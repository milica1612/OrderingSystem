package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.User;
import beans.UserType;
import dao.UserDAO;
import dto.LoginDTO;
import dto.UserDTO;

public class UserService {
	private UserDAO userDAO;
	
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public ArrayList<User> getAllUsers() {
		return userDAO.getAll();
	}

	public User create(User newUser) throws IOException {
		return userDAO.create(newUser);
	}

	public User login(LoginDTO loginDTO) {
		User logged = userDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null) {
			if(loginDTO.getPassword().equals(logged.getPassword())) {
				return logged;
			}
		}
		return null;
	}

	public User editUser(User logged, UserDTO userDTO) throws ParseException, IOException {
		return userDAO.update(logged, userDTO);
	}

	public User getUserByUsername(String params) {
		return userDAO.getByUsername(params);
	}
	
	public ArrayList<User> getUsersByName(String name){
		
		ArrayList<User> all = getAllUsers();
		ArrayList<User> result = new ArrayList<User>();
		
		for (User user : all) {
			 if(user.getName().toLowerCase().contains(name.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}
	
	public ArrayList<User> getUsersByLastName(String lastName){
			
			ArrayList<User> all = getAllUsers();
			ArrayList<User> result = new ArrayList<User>();
			
			for (User user : all) {
				 if(user.getLastName().toLowerCase().contains(lastName.toLowerCase().trim())) {
					 result.add(user);
				 }
			}
			
			return result;
		}
	
	public ArrayList<User> getUsersByUsername(String username){
		
		ArrayList<User> all = getAllUsers();
		ArrayList<User> result = new ArrayList<User>();
		
		for (User user : all) {
			 if(user.getName().toLowerCase().contains(username.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}

	public ArrayList<User> filtrateUsers(UserType userType, ArrayList<User> allResults){
		ArrayList<User> result = new ArrayList<User>();
		
		for (User user : allResults) {
			 if(user.getUserType().equals(userType)) {
				 result.add(user);
			 }
		}
		
		return result;
	}
	
}
