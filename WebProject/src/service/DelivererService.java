package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import beans.Customer;
import beans.Deliverer;
import beans.Manager;
import beans.Order;
import beans.OrderStatus;
import beans.User;
import dao.CustomerDAO;
import dao.DelivererDAO;
import dao.ManagerDAO;
import dto.LoginDTO;
import dto.OrderRequest;
import dto.UserDTO;

public class DelivererService {
	
private DelivererDAO delivererDAO;
	
	public DelivererService(DelivererDAO delivererDAO) {
		this.delivererDAO = delivererDAO;
	}
	
	public Deliverer create(Deliverer newDeliverer) throws IOException {
		return delivererDAO.create(newDeliverer);
		
	}

	public ArrayList<Deliverer> getAll() {
		ArrayList<Deliverer> deliverers = delivererDAO.getAll();
		ArrayList<Deliverer> result = new ArrayList<Deliverer>();
		if(deliverers != null) {
			for(Deliverer d : deliverers) {
				if(d.getIsDeleted() == false) {
					result.add(d);
				}
			}
		}
		return result;
	}

	public ArrayList<Deliverer> searchUsersByName(String name){
		
		ArrayList<Deliverer> all = getAll();
		ArrayList<Deliverer> result = new ArrayList<Deliverer>();
		
		for (Deliverer user : all) {
			 if(user.getName().toLowerCase().contains(name.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}
	
	public ArrayList<Deliverer> searchUsersByLastName(String lastName){
			
			ArrayList<Deliverer> all = getAll();
			ArrayList<Deliverer> result = new ArrayList<Deliverer>();
			
			for (Deliverer user : all) {
				 if(user.getLastName().toLowerCase().contains(lastName.toLowerCase().trim())) {
					 result.add(user);
				 }
			}
			
			return result;
		}
	
	public ArrayList<Deliverer> searchUsersByUsername(String username){
		
		ArrayList<Deliverer> all = getAll();
		ArrayList<Deliverer> result = new ArrayList<Deliverer>();
		
		for (Deliverer user : all) {
			 if(user.getUsername().toLowerCase().contains(username.toLowerCase().trim())) {
				 result.add(user);
			 }
		}
		
		return result;
	}
	
	public Deliverer login(LoginDTO loginDTO) {
		Deliverer logged = delivererDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null && logged.getIsDeleted() == false) {
			if(loginDTO.getPassword().equals(logged.getPassword())) {
				return logged;
			}
		}
		return null;
	}

	public User editDeliverer(User logged, UserDTO userDTO) throws ParseException, IOException {
		return delivererDAO.update(logged, userDTO);
	}

	public User getUserByUsername(String params) {
		return delivererDAO.getByUsername(params);
	}

	public User changePassword(User logged, String newPassword) throws IOException {
		ArrayList<Deliverer> all = delivererDAO.getAll();
		User userFound = null;
		for (User user : all) {
			if(user.getUsername().equals(logged.getUsername())) {
				user.setPassword(newPassword);
				userFound = user;
			}
		}
		delivererDAO.saveAll(all);
		return userFound;
	}

	public ArrayList<OrderRequest> getAllRequests(String restaurant) {
		ArrayList<Deliverer> deliverers = getAll();
		ArrayList<OrderRequest> requests = new ArrayList<OrderRequest>();
		for (Deliverer deliverer : deliverers) {
			for (Order o : deliverer.getOrders()) {
				if(o.getOrderStatus().equals(OrderStatus.WAITING_FOR_DELIVERY_APPROVAL) && o.getRestaurant().equals(restaurant)) {
					requests.add(new OrderRequest(deliverer, o));
				}
			}
		}
		return requests;
	}

	public void changeOrderToInTransport(OrderRequest orderReq) throws IOException {
		ArrayList<Deliverer> deliverers = delivererDAO.getAll();
		for (Deliverer deliverer : deliverers) {
			if(deliverer.getUsername().equals(orderReq.getDeliverer().getUsername())) {
				for (Order order : deliverer.getOrders()) {
					if(order.getCode().equals(orderReq.getOrder().getCode())) {
						order.setOrderStatus(OrderStatus.IN_TRANSPORT);
					}
				}
			}
		}
		delivererDAO.saveAll(deliverers);
		
	}
	

	public boolean canDelete(Deliverer d) {
		for (Order o : d.getOrders()) {
			if(o.getOrderStatus() != OrderStatus.CANCELED && o.getOrderStatus() != OrderStatus.DELIVERED)
				return false;
		}
		return true;
	}

	public User deleteUser(String username) throws IOException {
		ArrayList<Deliverer> all = delivererDAO.getAll();
		User userFound = null;
		for(Deliverer u : all) {
			if(u.getUsername().equals(username)) {
				if(canDelete(u)) {
					u.setIsDeleted(true);
					userFound = u;
				}
			}
		}
		delivererDAO.saveAll(all);
		return userFound;
	}

}
