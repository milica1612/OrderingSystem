package beans;

public class CustomerType {

	private String name;
	private Double discount;
	private int points;
	
	public CustomerType() {
		
	}
	
	public CustomerType(String name, Double discount, int points) {
		super();
		this.name = name;
		this.discount = discount;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
}
