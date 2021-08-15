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
public class Manager extends User implements Serializable {
	
	private static final long serialVersionUID = 7323106407810489267L;

	private Restaurant restaurant;
	
}
