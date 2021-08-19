package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.ArticleType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
	
	private Long id;
	
	private boolean isDeleted;
	
	private String name;
	
	private double price;
	
	private ArticleType articleType; 
	
//	private Restaurant restaurant;
	
	private double quantity;
	
	private String description;
	
	private String image;
}
