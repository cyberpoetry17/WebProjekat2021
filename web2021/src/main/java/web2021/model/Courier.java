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
public class Courier extends User implements Serializable {
	
	private static final long serialVersionUID = -2331717582920997112L;
	
	private List<Order> orders = new ArrayList<Order>();
	
}
