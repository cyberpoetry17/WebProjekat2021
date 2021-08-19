package web2021.service;

import static web2021.Application.articleService;

import web2021.dto.AddToShoppingCartDTO;
import web2021.dto.CustomerRegisterDTO;
import web2021.model.ArticleQuantity;
import web2021.model.Customer;
import web2021.model.ShoppingCart;
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
	
	public Customer getCustomerById(Long id) {
		return customerRepository.getById(id);
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
	
	public ShoppingCart addArticle(AddToShoppingCartDTO dto) {
		
		ArticleQuantity articleQuantity = new ArticleQuantity();
		articleQuantity.setId(System.currentTimeMillis());
		articleQuantity.setArticle(articleService.getArticleById(dto.getArticleId()));
		articleQuantity.setQuantity(dto.getQuantity());
			
		Customer customer = customerRepository.getById(dto.getUserId());
		customer.getShoppingCart().getArticles().add(articleQuantity);
		customer.getShoppingCart().setPrice(customer.getShoppingCart().getPrice() + articleQuantity.getQuantity() * articleQuantity.getArticle().getPrice());
		customerRepository.update(customer);
		
		return customer.getShoppingCart();
	}
	
}
