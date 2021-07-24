package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.dto.TestDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Test implements Serializable {
	
	private static final long serialVersionUID = -5787801295529615493L;

	private Long id;
	
	private boolean isDeleted;
	
	private String name;
	
	private User user;
	
	public Test(TestDTO testDTO) {
		this.id = System.currentTimeMillis();
		this.isDeleted = false;
		this.name = testDTO.getName();
		this.user = testDTO.getUser();
		this.user.setId(System.currentTimeMillis());
	}
	
}
