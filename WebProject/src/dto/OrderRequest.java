package dto;

import beans.Deliverer;
import beans.Order;

public class OrderRequest {
	private Deliverer deliverer;
	private Order order;
	
	public OrderRequest() {}
	
	public OrderRequest(Deliverer deliverer, Order order) {
		super();
		this.deliverer = deliverer;
		this.order = order;
	}

	public Deliverer getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(Deliverer deliverer) {
		this.deliverer = deliverer;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
