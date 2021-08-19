package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToShoppingCartDTO {
	
	private Long userId;
	
	private Long articleId;
	
	private int quantity;
	
}
