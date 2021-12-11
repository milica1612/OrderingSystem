package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Customer;
import beans.User;
import dao.CustomerDAO;
import dto.LoginDTO;

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

}
