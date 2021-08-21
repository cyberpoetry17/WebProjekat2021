package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDTO {
	
	private Long orderId;
	
	private Long customerId;
	
	private Long restaurantId;
	
	private String description;
	
	private int rating;
	
}
