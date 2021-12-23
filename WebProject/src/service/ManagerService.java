package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Customer;
import beans.Manager;
import beans.User;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dto.LoginDTO;
import dto.UserDTO;

public class ManagerService {
	
private ManagerDAO managerDAO;
	
	public ManagerService(ManagerDAO managerDAO) {
		this.managerDAO = managerDAO;
	}
	
	public Manager create(Manager newManager) throws IOException {
		return managerDAO.create(newManager);
		
	}

	public ArrayList<Manager> getAll() {
		return managerDAO.getAll();
	}

	public Manager login(LoginDTO loginDTO) {
		Manager logged = managerDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null) {
			if(loginDTO.getPassword().equals(logged.getPassword())) {
				return logged;
			}
		}
		return null;
	}

	public User editManager(User logged, UserDTO userDTO) throws ParseException, IOException {
		return managerDAO.update(logged, userDTO);
	}

	public User getUserByUsername(String params) {
		return managerDAO.getByUsername(params);
	}

}
