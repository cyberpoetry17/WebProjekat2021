package web2021.service;

import static web2021.Application.customerService;
import static web2021.Application.customerTypeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
			order.setPrice(customer.getShoppingCart().getPrice());
			order.setCustomer(customer);
			order.setOrderStatus(OrderStatus.PROCESSING);
			orderRepository.add(order);
			orders.add(order);
			
			descreaseQuantityOfArticle(entry.getKey(), entry.getValue());
		}
		return orders;
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
		
}
