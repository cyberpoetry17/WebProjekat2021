package web2021.service;


import static web2021.Application.customerService;

import static web2021.Application.customerTypeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import web2021.model.Article;
import web2021.model.ArticleQuantity;
import web2021.model.Customer;
import web2021.model.Order;
import web2021.model.Restaurant;
import web2021.model.ShoppingCart;
import web2021.model.enums.OrderStatus;
import web2021.dto.OrderDTO;
import web2021.repository.CustomerRepository;
import web2021.repository.OrderRepository;
import web2021.repository.RestaurantRepository;

public class OrderService {
	
	private OrderRepository orderRepository;
	private RestaurantRepository restaurantRepository;
	private CustomerRepository customerRepository;
	
	public OrderService() {
		this.orderRepository = new OrderRepository();
		this.restaurantRepository = new RestaurantRepository();
		this.customerRepository = new CustomerRepository();
	}
	
	public List<Order> makeOrder(Long customerId) {
		
		Customer customer = customerService.getCustomerById(customerId);
		
		Map<Long, List<ArticleQuantity>> makeOrders = parseShoppigCart(customer.getShoppingCart());
		
		customer = increasePoints(customer);
		
		List<Order> orders = new ArrayList<Order>();
		
		for (Map.Entry<Long, List<ArticleQuantity>> entry : makeOrders.entrySet()) {
			Order order = new Order();
			order.setId(System.currentTimeMillis());
			order.setDeleted(false);
			order.setReviewed(false);
			order.setSerialNumber("S/N:" + System.currentTimeMillis());
			order.setArticles(entry.getValue());
			order.setRestaurant(restaurantRepository.getById(entry.getKey()));
			order.setDateOfOrder(LocalDateTime.now());
			order.setPrice(calculatePrice(entry.getValue()));
			order.setCustomer(customer);
			order.setOrderStatus(OrderStatus.PROCESSING);
			orderRepository.add(order);
			orders.add(order);
			
			descreaseQuantityOfArticle(entry.getKey(), entry.getValue());
		}
		return orders;
	}
	
	private double calculatePrice(List<ArticleQuantity> articleQuantities) {
		double price = 0;
		for(ArticleQuantity aq : articleQuantities) {
			price = price + aq.getQuantity() * aq.getArticle().getPrice();
		}
		return price;
	}
	private void increaseQuantityOfArticle(Long restaurantId,List<ArticleQuantity> articles) {
		Restaurant restaurant = restaurantRepository.getById(restaurantId);
		for(ArticleQuantity aq : articles) {
			Article article = restaurant.getArticleById(aq.getArticle().getId());
			article.setQuantity(article.getQuantity() + aq.getQuantity());
			restaurant.updateArticle(article);
		}
		restaurantRepository.update(restaurant);
	}
	private void descreaseQuantityOfArticle(Long restaurantId, List<ArticleQuantity> articles) {
		Restaurant restaurant = restaurantRepository.getById(restaurantId);
		for(ArticleQuantity aq : articles) {
			Article article = restaurant.getArticleById(aq.getArticle().getId());
			article.setQuantity(article.getQuantity() - aq.getQuantity());
			restaurant.updateArticle(article);
		}
		restaurantRepository.update(restaurant);
	}
	
	private Customer increasePoints(Customer customer) {
		customer.setPoints(customer.getPoints() + customer.getShoppingCart().getPrice()/1000*133);
		customer.setCustomerType(customerTypeService.getCustomerTypeByPoints(customer.getPoints()));
		customer.getShoppingCart().setArticles(new ArrayList<ArticleQuantity>());
		customer.getShoppingCart().setPrice(0);
		customerRepository.update(customer);
		return customer;
	}
	
	private Customer decreasePoints(Customer customer,double price) {
		if(customer.getPoints() > 0 && price > 0) {
		customer.setPoints(customer.getPoints() - price/1000*133*4);
		customer.setCustomerType(customerTypeService.getCustomerTypeByPoints(customer.getPoints()));
		
		customerRepository.update(customer);
		return customer;}
		else {
			return customer;
		}
	}
	
	//sortira artikle po restoranima
	private Map<Long, List<ArticleQuantity>> parseShoppigCart(ShoppingCart shoppingCart) {
		Map<Long, List<ArticleQuantity>> makeOrders = new HashMap<Long, List<ArticleQuantity>>();
		for(Long id : findRestaurants(shoppingCart.getArticles())) {
			makeOrders.put(id, new ArrayList<ArticleQuantity>());
		}
		for(ArticleQuantity articleQuantity : shoppingCart.getArticles()) {
			makeOrders.get(articleQuantity.getArticle().getRestaurantId()).add(articleQuantity);
		}
		return makeOrders;
	}
	
	//pronalazi sve restorane
	private Set<Long> findRestaurants(List<ArticleQuantity> articleQuantities) {
		Set<Long> ids = new HashSet<Long>();
		for(ArticleQuantity articleQuantity : articleQuantities) {
			ids.add(articleQuantity.getArticle().getRestaurantId());
		}
		return ids;
	}
	
	public List<OrderStatus> getOrderStatus() {
		List<OrderStatus> response = new ArrayList<OrderStatus>(Arrays.asList(OrderStatus.values()));
		return response;
	}
	
	public List<Order> getOrdersForCustomer(Long id) {
		List<Order> response = new ArrayList<Order>();
		for(Order order : orderRepository.getAll()) {
			if(order.getCustomer().getId().equals(id)) {
				response.add(order);
			}
		}
		return response;
	}
	
	public List<Order> getOrdersForCourier(Long id) {
		List<Order> response = new ArrayList<Order>();
		for(Order order : orderRepository.getAll()) {
			if(order.getOrderStatus() == OrderStatus.WAITINGFORDELIVERY) {
				response.add(order);
			}
			else if(order.getCourierId() != null && order.getCourierId().equals(id)) {
				response.add(order);
			}
		}
		return response;
	}
	
	public List<Order> getOrdersForManager(Long id) {
		List<Order> response = new ArrayList<Order>();
		for(Order order : orderRepository.getAll()) {
			if(order.getRestaurant().getId().equals(id)) {
				response.add(order);
			}
		}
		return response;
	}
	
	public Order cancelOrder(OrderDTO orderDTO) {
		Order order = orderRepository.getById(orderDTO.getIdOrder());  //nadjem order ovde
		Customer customer = customerRepository.getById(order.getCustomer().getId());
		Map<Long, List<ArticleQuantity>> cancelOrders = parseShoppigCart(customer.getShoppingCart());
		
		if(order.getOrderStatus().equals(OrderStatus.PROCESSING)) {
			order.setOrderStatus(orderDTO.getOrderStatus());
			for (Map.Entry<Long, List<ArticleQuantity>> entry : cancelOrders.entrySet()) {
				increaseQuantityOfArticle(entry.getKey(), entry.getValue());
			}
			Customer updatedCustomer = decreasePoints(customer,order.getPrice());
			order.setCustomer(updatedCustomer);
			orderRepository.update(order);
			return order;
		}
		return order;
	}
	

		

}
