package beans;

import java.util.List;

import enums.Gender;
import enums.Role;

public class User {

	private String username;
	private String password;
	private String surname;
	private String name;
    private Role role;
    private Gender gender;
    private List<Apartment> ApartmentsForRent;
    private List<Apartment> RentedApartments;
    
    
    public User() {
		super();
	}
	public User(String username, String password, String surname, String name, Role role, Gender gender,
			List<Apartment> apartmentsForRent, List<Apartment> rentedApartments, List<Reservation> reservations) {
		super();
		this.username = username;
		this.password = password;
		this.surname = surname;
		this.name = name;
		this.role = role;
		this.gender = gender;
		ApartmentsForRent = apartmentsForRent;
		RentedApartments = rentedApartments;
		Reservations = reservations;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public List<Apartment> getApartmentsForRent() {
		return ApartmentsForRent;
	}
	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		ApartmentsForRent = apartmentsForRent;
	}
	public List<Apartment> getRentedApartments() {
		return RentedApartments;
	}
	public void setRentedApartments(List<Apartment> rentedApartments) {
		RentedApartments = rentedApartments;
	}
	public List<Reservation> getReservations() {
		return Reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		Reservations = reservations;
	}
	private List<Reservation> Reservations;
}
