package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.restaurantService;
import static web2021.Application.managerService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CreateRestaurantDTO;
import web2021.model.Restaurant;

public class RestaurantController {
	
	public static Route getAllRestaurantTypes = (Request request, Response response) -> {
		String json = gson.toJson(restaurantService.getAllRestaurantTypes());
		return json;
	};
	
	public static Route createRestaurant = (Request request, Response response) -> {
		String payload = request.body();
		CreateRestaurantDTO createRestaurantDTO = gson.fromJson(payload, CreateRestaurantDTO.class);
		Restaurant restaurant = restaurantService.createRestaurant(createRestaurantDTO);
		managerService.assignRestaurantToManager(createRestaurantDTO.getManagerId(),restaurant.getId());
		String json = gson.toJson(restaurant);
		return json;
	};
	
	public static Route getAllRestaurants = (Request request, Response response) -> {
		String json = gson.toJson(restaurantService.getAllRestaurants());
		return json;
	};
	
}
