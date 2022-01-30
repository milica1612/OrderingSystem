package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Customer;
import beans.Manager;
import beans.Restaurant;
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
		ArrayList<Manager> managers = managerDAO.getAll();
		ArrayList<Manager> result = new ArrayList<Manager>();
		for(Manager m: managers) {
			if(m.getIsDeleted() == false) {
				result.add(m);
			}
		}
		return result;
	}

	public ArrayList<Manager> getAllAvailable(){
		ArrayList<Manager> managers = getAll();
		ArrayList<Manager> result = new ArrayList<Manager>();
		if(managers != null) {
			for (Manager manager : managers) {
				if(manager.getRestaurant() == null) {
					result.add(manager);
				}
			}
			return result;
		}
		return null;
	}
	public Manager login(LoginDTO loginDTO) {
		Manager logged = managerDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null && logged.getIsDeleted() == false) {
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

	public Manager addRestaurant(String manager, Restaurant restaurant) throws IOException {
		ArrayList<Manager> managers = managerDAO.getAll();
		if(managers != null) {
			for (Manager m : managers) {
				if(m.getUsername().equals(manager)) {
					m.setRestaurant(restaurant);
					managerDAO.saveAll(managers);
					return m;
				}
			}
		}
		return null;
		
	}

	public User changePassword(User logged, String newPassword) throws IOException {
		ArrayList<Manager> all = managerDAO.getAll();
		User userFound = null;
		for (User user : all) {
			if(user.getUsername().equals(logged.getUsername())) {
				user.setPassword(newPassword);
				userFound = user;
			}
		}
		managerDAO.saveAll(all);
		return userFound;
	}
	public Restaurant getRestaurant(String manager) {
		ArrayList<Manager> managers = getAll();
		if(managers != null) {
			for (Manager m : managers) {
				if(m.getUsername().equals(manager)) {
					return m.getRestaurant();
				}
			}
		}
		return null;
	}

	public User deleteUser(String username) throws IOException {
		ArrayList<Manager> all = managerDAO.getAll();
		User userFound = null;
		for(User u : all) {
			if(u.getUsername().equals(username)) {
				u.setIsDeleted(true);
				userFound = u;
			}
		}
		managerDAO.saveAll(all);
		return userFound;
	}

}
