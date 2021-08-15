package web2021.service;

import web2021.dto.LoginDTO;
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
	
	public UserService(String admin, String manager, String courier, String customer) {
		this.administratorRepository = new AdministratorRepository(admin);
		this.managerRepository = new ManagerRepository(manager);
		this.courierRepository = new CourierRepository(courier);
		this.customerRepository = new CustomerRepository(customer);
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
	
}
