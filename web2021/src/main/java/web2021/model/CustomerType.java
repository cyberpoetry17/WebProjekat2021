package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.CustomerTypeName;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerType implements Serializable {

	private static final long serialVersionUID = -1757441371464866809L;
	
	private Long id;
	
	private CustomerTypeName customerTypeName;
	
	private double discount;
	
	private double points;
	
}
