package web2021.service;

import web2021.dto.CustomerRegisterDTO;
import web2021.model.Customer;
import web2021.repository.CustomerRepository;

import static web2021.Application.customerTypeService;

import java.util.List;

public class CustomerService {
	
	private CustomerRepository customerRepository;
	
	public CustomerService() {
		this.customerRepository = new CustomerRepository();
	}
	
	public CustomerService(String path) {
		this.customerRepository = new CustomerRepository(path);
	}
	
	public Customer register(CustomerRegisterDTO customerRegisterDTO) {
		Customer customer = new Customer();
		customer.setId(System.currentTimeMillis());
		customer.setDeleted(false);
		customer.setUsername(customerRegisterDTO.getUsername());
		customer.setPassword(customerRegisterDTO.getPassword());
		customer.setName(customerRegisterDTO.getName());
		customer.setSurname(customerRegisterDTO.getSurname());
		customer.setGenderType(customerRegisterDTO.getGenderType());
		customer.setBirthday(customerRegisterDTO.getBirthday());
		customer.setUserType(customerRegisterDTO.getUserType());
		customer.setImage("../img/profile.jpg");
		customer.getShoppingCart().setId(customer.getId());
//		customer.getShoppingCart().setCustomer(customer);
		customer.getShoppingCart().setPrice(0);
		customer.setPoints(0);
		customer.setCustomerType(customerTypeService.getCustomerTypeByPoints(0));
		return customerRepository.add(customer);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.getAll();
	}
	
}
