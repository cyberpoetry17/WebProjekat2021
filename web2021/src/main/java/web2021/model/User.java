package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {

	private static final long serialVersionUID = -8207261429084622354L;

	private Long id;
	
	private String name;
	
	private String surname;
	
}
