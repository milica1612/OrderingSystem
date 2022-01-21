package beans;

public class Comment {

	private Customer customer;
	private Restaurant restaurant;
	private String content;
	private int rating;
	private boolean approved = false;
	
	public Comment() {
		
	}
	
	public Comment(Customer customer, Restaurant restaurant, String content, int rating, boolean approved) {
		super();
		this.customer = customer;
		this.restaurant = restaurant;
		this.content = content;
		this.rating = rating;
		this.approved = approved;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
