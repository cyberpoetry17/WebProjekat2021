package web2021.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import web2021.dto.CreateRestaurantDTO;
import web2021.model.Location;
import web2021.model.Restaurant;
import web2021.model.enums.RestaurantType;
import web2021.repository.RestaurantRepository;

public class RestaurantService {
	
	private RestaurantRepository restaurantRepository;
	
	public RestaurantService() {
		this.restaurantRepository = new RestaurantRepository();
	}
	
	public List<RestaurantType> getAllRestaurantTypes() {
		List<RestaurantType> response = new ArrayList<RestaurantType>(Arrays.asList(RestaurantType.values()));
		return response;
	}
	
	public Restaurant createRestaurant(CreateRestaurantDTO createRestaurantDTO) {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(System.currentTimeMillis());
		restaurant.setDeleted(false);
		restaurant.setName(createRestaurantDTO.getName());
		restaurant.setRestaurantType(createRestaurantDTO.getRestaurantType());
		restaurant.setWorking(true);
		
		Location location = new Location(createRestaurantDTO.getLocation());
		restaurant.setLocation(location);
		
		restaurant.setImage(createRestaurantDTO.getImage());
		restaurant.setAverageRating(0);
		return restaurantRepository.add(restaurant);
	}
	
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepository.getAll();
	}
	
}
