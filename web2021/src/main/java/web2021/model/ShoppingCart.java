package web2021.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCart implements Serializable {
	
	private static final long serialVersionUID = 1529724001179215624L;
	
	private Long id;
	
	private List<ArticleQuantity> articles = new ArrayList<ArticleQuantity>();
	
	private Customer customer;
	
	private double price;
	
}
