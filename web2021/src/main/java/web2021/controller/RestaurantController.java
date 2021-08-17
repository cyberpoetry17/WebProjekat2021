package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.restaurantService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CreateRestaurantDTO;

public class RestaurantController {
	
	public static Route getAllRestaurantTypes = (Request request, Response response) -> {
		String json = gson.toJson(restaurantService.getAllRestaurantTypes());
		return json;
	};
	
	public static Route createRestaurant = (Request request, Response response) -> {
		String payload = request.body();
		CreateRestaurantDTO createRestaurantDTO = gson.fromJson(payload, CreateRestaurantDTO.class);
		String json = gson.toJson(restaurantService.createRestaurant(createRestaurantDTO));
		return json;
	};
	
}
