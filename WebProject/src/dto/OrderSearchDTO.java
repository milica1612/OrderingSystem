package dto;

public class OrderSearchDTO {

	private double priceFrom;
	private double priceTo;
	private String dateFrom;
	private String dateTo;
	
	public OrderSearchDTO() {}
	
	public OrderSearchDTO(double priceFrom, double priceTo, String dateFrom, String dateTo) {
		super();
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	public double getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}
	public double getPriceTo() {
		return priceTo;
	}
	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
}
