package web2021.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.CustomerType;
import web2021.model.enums.GenderType;
import web2021.model.enums.UserType;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerRegisterDTO {
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String surname;
	
	private GenderType genderType;
	
	private LocalDateTime birthday;
	
	private UserType userType;
	
	private double points;
	
	private CustomerType customerType;
	
}
