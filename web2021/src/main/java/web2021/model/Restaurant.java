package web2021.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.RestaurantType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant implements Serializable {
	
	private static final long serialVersionUID = 1752999880524684218L;
	
	private Long id;
	
	private boolean isDeleted;
	
	private String name;
	
	private RestaurantType restaurantType;
	
	private List<Article> articles = new ArrayList<Article>();
	
	private boolean isWorking;
	
	private Location location;
	
	private String image;
	
}
