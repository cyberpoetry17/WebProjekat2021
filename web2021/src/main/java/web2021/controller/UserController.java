package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.userService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.UserDTO;
import web2021.model.User;

public class UserController {

//	public static Route logIn = (Request request, Response response) -> {
//		
//	};
	
	public static Route register = (Request request, Response response) -> {
		String payload = request.body();
		UserDTO userDTO = gson.fromJson(payload, UserDTO.class);
		User newUser = userService.register(userDTO); 
		return newUser;
	};
	
}
