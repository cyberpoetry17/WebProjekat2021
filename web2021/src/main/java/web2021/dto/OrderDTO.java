package web2021.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.ArticleQuantity;
import web2021.model.Customer;
import web2021.model.enums.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	private Long idOrder;
	private OrderStatus orderStatus;
//	private Long idrestaurant;
//	private Customer customer;
//	private Long idCustomer;
//	private List<ArticleQuantity> articles = new ArrayList<ArticleQuantity>();

}
