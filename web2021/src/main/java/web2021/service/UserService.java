package web2021.service;

import static web2021.Application.update;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import web2021.dto.LoginDTO;
import web2021.dto.UserDTO;
import web2021.model.Courier;
import web2021.model.Customer;
import web2021.model.Manager;
import web2021.model.User;
import web2021.repository.AdministratorRepository;
import web2021.repository.CourierRepository;
import web2021.repository.CustomerRepository;
import web2021.repository.ManagerRepository;

public class UserService {
	
	private AdministratorRepository administratorRepository;
	private ManagerRepository managerRepository;
	private CourierRepository courierRepository;
	private CustomerRepository customerRepository;
	
	public UserService() {
		this.administratorRepository = new AdministratorRepository();
		this.managerRepository = new ManagerRepository();
		this.courierRepository = new CourierRepository();
		this.customerRepository = new CustomerRepository();
	}
	
	public boolean checkIsUsernameTaken(String username) {
		for(Customer customer : customerRepository.getAll()) {
			if(customer.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return true;
			}
		}
		for(Manager manager : managerRepository.getAll()) {
			if(manager.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return true;
			}
		}
		for(User user : administratorRepository.getAll()) {
			if(user.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return true;
			}
		}
		for(Courier courier : courierRepository.getAll()) {
			if(courier.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public User loginAsAdministrator(LoginDTO loginDTO) {
		for(User user : administratorRepository.getAll()) {
			if(user.getUsername().equals(loginDTO.getUsername())) {
				if(user.getPassword().equals(loginDTO.getPassword())) {
					return user;
				}
				else {
					return null;
				}
			}
		}
		return null;
	}
	
	public Manager loginAsManager(LoginDTO loginDTO) {
		for(Manager manager : managerRepository.getAll()) {
			if(manager.getUsername().equals(loginDTO.getUsername())) {
				if(manager.getPassword().equals(loginDTO.getPassword())) {
					return manager;
				}
				else {
					return null;
				}
			}
		}
		return null;
	}
	
	public Courier loginAsCourier(LoginDTO loginDTO) {
		for(Courier courier : courierRepository.getAll()) {
			if(courier.getUsername().equals(loginDTO.getUsername())) {
				if(courier.getPassword().equals(loginDTO.getPassword())) {
					return courier;
				}
				else {
					return null;
				}
			}
		}
		return null;
	}
	
	public Customer loginAsCustomer(LoginDTO loginDTO) {
		for(Customer customer : customerRepository.getAll()) {
			if(customer.getUsername().equals(loginDTO.getUsername())) {
				if(customer.getPassword().equals(loginDTO.getPassword())) {
					return customer;
				}
				else {
					return null;
				}
			}
		}
		return null;
	}
	
	public User editProfileAdministrator(UserDTO userDTO) {
		User user = administratorRepository.getById(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		if(!userDTO.getPassword().equals("")) user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setBirthday(userDTO.getBirthday());
		return administratorRepository.update(user);
	}
	
	public Manager editProfileManager(UserDTO userDTO) {
		Manager manager = managerRepository.getById(userDTO.getId());
		manager.setUsername(userDTO.getUsername());
		if(!userDTO.getPassword().equals("")) manager.setPassword(userDTO.getPassword());
		manager.setName(userDTO.getName());
		manager.setSurname(userDTO.getSurname());
		manager.setBirthday(userDTO.getBirthday());
		return managerRepository.update(manager);
	}
	
	public Courier editProfileCourier(UserDTO userDTO) {
		Courier courier = courierRepository.getById(userDTO.getId());
		courier.setUsername(userDTO.getUsername());
		if(!userDTO.getPassword().equals("")) courier.setPassword(userDTO.getPassword());
		courier.setName(userDTO.getName());
		courier.setSurname(userDTO.getSurname());
		courier.setBirthday(userDTO.getBirthday());
		return courierRepository.update(courier);
	}
	
	public Customer editProfileCustomer(UserDTO userDTO) {
		Customer customer = customerRepository.getById(userDTO.getId());
		customer.setUsername(userDTO.getUsername());
		if(!userDTO.getPassword().equals("")) customer.setPassword(userDTO.getPassword());
		customer.setName(userDTO.getName());
		customer.setSurname(userDTO.getSurname());
		customer.setBirthday(userDTO.getBirthday());
		update.updateComment(customer);
		return customerRepository.update(customer);
	}
	
	public void blockUnblock(Long userId) {
		Manager manager = managerRepository.getById(userId);
		if(manager != null) {
			manager.setBlocked(!manager.isBlocked());
			managerRepository.update(manager);
			return;
		}
		Customer customer = customerRepository.getById(userId);
		if(customer != null) {
			customer.setBlocked(!customer.isBlocked());
			customerRepository.update(customer);
			return;
		}
		Courier courier = courierRepository.getById(userId);
		if(courier != null) {
			courier.setBlocked(!courier.isBlocked());
			courierRepository.update(courier);
			return;
		}
	}
	
	public List<Customer> getSuspectedUsers() {
		List<Customer> response = new ArrayList<Customer>();
		for(Customer customer : customerRepository.getAll()) {
			if(customer.getRecord() != null) {
				if(calculateDifferenceForDates(customer.getRecord().getDateOfFirstCancelation().toLocalDate(), 
						customer.getRecord().getDateOfLastCancelation().toLocalDate()) < 1 && customer.getRecord().getCounter() >= 5) {
					response.add(customer);
				}				
			}
		}
		return response;
	}
	
	private int calculateDifferenceForDates(LocalDate from, LocalDate to) {
        Period period = Period.between(from, to);
        return period.getMonths();
	}
}
