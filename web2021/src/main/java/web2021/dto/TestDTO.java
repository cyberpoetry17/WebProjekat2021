package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestDTO {
	
	private Long id;
	
	private boolean isDeleted;
	
	private String name;
	
	private User user;
	
}
