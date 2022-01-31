package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gson.JsonElement;

import beans.Customer;
import beans.Order;
import beans.OrderStatus;
import beans.Restaurant;
import beans.User;
import dao.CustomerDAO;
import dto.LoginDTO;
import dto.UserDTO;

public class CustomerService {
	
	private CustomerDAO customerDAO;
	
	public CustomerService(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	
	public Customer create(Customer newCustomer) throws IOException {
		return customerDAO.create(newCustomer);
		
	}

	public ArrayList<Customer> getAll() {
		ArrayList<Customer> customers = customerDAO.getAll();
		ArrayList<Customer> result = new ArrayList<Customer>();
		if(customers != null) {
			for(Customer c : customers) {
				if(c.getIsDeleted() == false) {
					result.add(c);
				}
			}
		}
		return result;
	}
	
	public ArrayList<Customer> searchUsersByName(String name){
		
		ArrayList<Customer> all = getAll();
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		for (Customer user : all) {
			 if(user.getName().toLowerCase().contains(name.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}
	
	public ArrayList<Customer> searchUsersByLastName(String lastName){
			
			ArrayList<Customer> all = getAll();
			ArrayList<Customer> result = new ArrayList<Customer>();
			
			for (Customer user : all) {
				 if(user.getLastName().toLowerCase().contains(lastName.toLowerCase().trim())) {
					 result.add(user);
				 }
			}
			
			return result;
		}
	
	public ArrayList<Customer> searchUsersByUsername(String username){
		
		ArrayList<Customer> all = getAll();
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		for (Customer user : all) {
			 if(user.getUsername().toLowerCase().contains(username.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}

	public Customer login(LoginDTO loginDTO) {
		Customer logged = customerDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null && logged.getIsDeleted() == false) {
			if(loginDTO.getPassword().equals(logged.getPassword())) {
				return logged;
			}
		}
		return null;
	}

	public User editCustomer(User logged, UserDTO userDTO) throws ParseException, IOException {
		return customerDAO.update(logged, userDTO);
	}

	public User getUserByUsername(String params) {
		return customerDAO.getByUsername(params);
	}

	public User changePassword(User logged, String newPassword) throws IOException {
		ArrayList<Customer> all = customerDAO.getAll();
		User userFound = null;
		for (User user : all) {
			if(user.getUsername().equals(logged.getUsername())) {
				user.setPassword(newPassword);
				userFound = user;
			}
		}
		customerDAO.saveAll(all);
		return userFound;
	}

	public boolean canComment(String username, String params) {
		Customer customer = customerDAO.getByUsername(username);
		for (Order o : customer.getOrders()) {
			if(o.getOrderStatus().equals(OrderStatus.DELIVERED) && o.getRestaurant().equals(params)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Customer> filtrateByCustomerType(String param, ArrayList<Customer> results) {
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		for(Customer c : results) {
			if(c.getCustomerType() != null && c.getCustomerType().getName().toLowerCase().contains(param.toLowerCase().trim())) {
			customers.add(c);
			}
		}
		return customers;
	}
	
	public boolean canDeleteCustomer(Customer c) {
		for (Order o : c.getOrders()) {
			if(o.getOrderStatus() != OrderStatus.CANCELED && o.getOrderStatus() != OrderStatus.DELIVERED)
				return false;
		}
		return true;
	}

	public User deleteUser(String username) throws IOException {
		ArrayList<Customer> all = customerDAO.getAll();
		User userFound = null;
		for(Customer u : all) {
			if(u.getUsername().equals(username)) {
				if(canDeleteCustomer(u)) {
					u.setIsDeleted(true);
					userFound = u;
				}
			}
		}
		customerDAO.saveAll(all);
		return userFound;
	}
}
