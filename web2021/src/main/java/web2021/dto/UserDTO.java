package web2021.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.GenderType;
import web2021.model.enums.UserType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

	private Long id;
	
	private boolean isDeleted;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String surname;
	
	private GenderType genderType;
	
	private LocalDateTime birthday;
	
	private UserType userType;
	
}
