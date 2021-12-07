package service;

import java.util.ArrayList;

import beans.User;
import dao.UserDAO;

public class UserService {
	private UserDAO userDAO;
	
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public ArrayList<User> getAllUsers() {
		return userDAO.getAll();
	}

	public void register(User newUser) {
		// TODO Auto-generated method stub
		
	}

}
