package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.ArticleType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleDTO {
	
	private String name;
	
	private double price;
	
	private ArticleType articleType; 
	
	private double quantity;
	
	private String description;
	
	private String image;
	
	private Long restaurantId;
	
}
