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
public class ArticleQuantity implements Serializable {
	
	private static final long serialVersionUID = -7267122750585784155L;
	
	private Long id;
	
	private Article article;
	
	private int quantity;
	
}
