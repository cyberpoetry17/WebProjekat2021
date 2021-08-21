package web2021.model;

import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.dto.CustomerRegisterDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends User implements Serializable {

	private static final long serialVersionUID = 8455044274954973765L;
	
	private final ShoppingCart shoppingCart = new ShoppingCart();
	
	private double points;
	
	private CustomerType customerType;
	
	private CancelationRecord record;

	public Customer(CustomerRegisterDTO customerRegisterDTO) {
		super(System.currentTimeMillis(), false, false, customerRegisterDTO.getUsername(), customerRegisterDTO.getPassword(), customerRegisterDTO.getName(), 
				customerRegisterDTO.getSurname(), customerRegisterDTO.getGenderType(), customerRegisterDTO.getBirthday(), customerRegisterDTO.getUserType(), "");
		this.points = 0;
	}
	
}
