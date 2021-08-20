package web2021.service;

import java.util.ArrayList;
import java.util.List;

import web2021.dto.AddCommentDTO;
import web2021.model.Comment;
import web2021.model.Order;
import web2021.repository.CommentRepository;
import web2021.repository.CustomerRepository;
import web2021.repository.OrderRepository;
import web2021.repository.RestaurantRepository;

public class CommentService {
	
	private CommentRepository commentRepository;
	private RestaurantRepository restaurantRepository;
	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	
	public CommentService() {
		this.commentRepository = new CommentRepository();
		this.restaurantRepository = new RestaurantRepository();
		this.customerRepository = new CustomerRepository();
		this.orderRepository = new OrderRepository();
	}
	
	public Comment addComment(AddCommentDTO dto) {
		Comment comment = new Comment();
		comment.setId(System.currentTimeMillis());
		comment.setDeleted(false);
		comment.setApproved(false);
		comment.setCustomer(customerRepository.getById(dto.getCustomerId()));
		comment.setRestaurant(restaurantRepository.getById(dto.getRestaurantId()));
		comment.setDescription(dto.getDescription());
		comment.setRating(dto.getRating());
		commentRepository.add(comment);
		
		Order order = orderRepository.getById(dto.getOrderId());
		order.setReviewed(true);
		orderRepository.update(order);
		
		return comment;
	}
	
	public List<Comment> getAllComments() {
		List<Comment> response = new ArrayList<Comment>();
		for(Comment comment : commentRepository.getAll()) {
			if(!comment.isDeleted()) {
				response.add(comment);
			}
		}
		return response;
	}
	
	public List<Comment> getCommentsForManager(Long id) {
		List<Comment> response = new ArrayList<Comment>();
		for(Comment comment : getAllComments()) {
			if(comment.getRestaurant().getId().equals(id)) {
				response.add(comment);
			}
		}
		return response;
	}
	
	public Comment reviewComment(Long id, boolean isApproved) {
		Comment comment = commentRepository.getById(id);
		comment.setReviewed(true);
		comment.setApproved(isApproved);
		return commentRepository.update(comment);
	}
	
	public List<Comment> getCommentsForRestaurant(Long id) {
		List<Comment> response = new ArrayList<Comment>();
		for(Comment comment : getAllComments()) {
			if(comment.isApproved() && !comment.isDeleted()) {
				response.add(comment);
			}
		}
		return response;
	}
	
}
