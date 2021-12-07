package service;

import java.io.IOException;
import java.util.ArrayList;

import beans.Customer;
import dao.CustomerDAO;

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

}
