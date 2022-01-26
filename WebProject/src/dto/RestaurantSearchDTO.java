package dto;

public class RestaurantSearchDTO {

	private String name;
	private String location;
	private String rating;
	private String type;
	
	public RestaurantSearchDTO() {
		
	}
	
	public RestaurantSearchDTO(String name, String location, String rating, String type) {
		super();
		this.name = name;
		this.location = location;
		this.rating = rating;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
