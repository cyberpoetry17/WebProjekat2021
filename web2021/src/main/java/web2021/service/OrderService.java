package web2021.service;

import web2021.repository.OrderRepository;
import web2021.repository.UserRepository;

public class OrderService {
 private OrderRepository orderRepository;
 private UserRepository userRepository;
 
 public OrderService() {
	 this.orderRepository = new OrderRepository();
	 this.userRepository = new UserRepository();
 }
 
 
}
