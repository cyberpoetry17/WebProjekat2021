package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.model.enums.RestaurantType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateRestaurantDTO {
	
	private String name;
	
	private RestaurantType restaurantType;
	
	private LocationDTO location;
	
	private String image;
	
}
