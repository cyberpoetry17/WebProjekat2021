package web2021.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order implements Serializable {

	private static final long serialVersionUID = 6137889408237947075L;

	private Long id;
	
	private boolean isDeleted;
	
	private boolean isApproved;
	
	private boolean isReviewed;
	
	
	private String serialNumber;
	
	private List<ArticleQuantity> articles = new ArrayList<ArticleQuantity>();
	
	private Restaurant restaurant;
	
	private LocalDateTime dateOfOrder;
	
	private double price;

	private Long courierId;
	
	private Customer customer;
	
	private OrderStatus orderStatus;
	
}
