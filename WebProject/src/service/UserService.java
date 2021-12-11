package service;

import java.util.ArrayList;

import beans.User;
import dao.UserDAO;
import dto.LoginDTO;

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

	public User login(LoginDTO loginDTO) {
		User logged = userDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null) {
			if(loginDTO.getPassword().equals(logged.getPassword())) {
				return logged;
			}
		}
		return null;
	}

}
