package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.ArticleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article implements Serializable {
	
	private static final long serialVersionUID = 2983330145715423398L;
	
	private Long id;
	
	private boolean isDeleted;
	
	private String name;
	
	private double price;
	
	private ArticleType articleType; 
	
	private Long restaurantId;
	
	private double quantity;
	
	private String description;
	
	private String image;
}
