package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Customer;
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
		return customerDAO.getAll();
	}

	public Customer login(LoginDTO loginDTO) {
		Customer logged = customerDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null) {
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

}
