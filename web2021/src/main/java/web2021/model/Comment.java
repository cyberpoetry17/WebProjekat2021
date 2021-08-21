package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 6109018351588058328L;
	
	private Long id;
	
	private boolean isReviewed;
	
	private boolean isDeleted;
	
	private boolean isApproved;
	
	private Customer customer;
	
	private Restaurant restaurant;
	
	private String description;
	
	private int rating;
	
}
