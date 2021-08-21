package web2021.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.dto.UserDTO;
import web2021.model.enums.GenderType;
import web2021.model.enums.UserType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {

	private static final long serialVersionUID = -8207261429084622354L;

	private Long id;
	
	private boolean isDeleted;
	
	private boolean isBlocked;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private String surname;
	
	private GenderType genderType;
	
	private LocalDateTime birthday;
	
	private UserType userType;
	
	private String image;
	
	public User(UserDTO userDTO) {
		this.id = System.currentTimeMillis();
		this.isDeleted = false;
		this.username = userDTO.getUsername();
		this.password = userDTO.getPassword();
		this.name = userDTO.getName();
		this.surname = userDTO.getSurname();
		this.genderType = userDTO.getGenderType();
		this.birthday = userDTO.getBirthday();
		this.userType  = userDTO.getUserType();
	}
	
}
