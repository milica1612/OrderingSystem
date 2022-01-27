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
		return delivererDAO.getAll();
	}

	public Deliverer login(LoginDTO loginDTO) {
		Deliverer logged = delivererDAO.getByUsername(loginDTO.getUsername());
		
		if(logged != null) {
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
		ArrayList<Deliverer> all = getAll();
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
		ArrayList<Deliverer> deliverers = getAll();
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

}
