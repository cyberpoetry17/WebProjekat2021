package beans;

import java.util.Date;

public class Reservation {

	private Apartment apartment;
	private Date CheckIn; //od kad pocinje 
	private int durationPerNight = 1 ;
	private double price;
	public Apartment getApartment() {
		return apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	public Date getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(Date checkIn) {
		CheckIn = checkIn;
	}
	public int getDurationPerNight() {
		return durationPerNight;
	}
	public void setDurationPerNight(int durationPerNight) {
		this.durationPerNight = durationPerNight;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
