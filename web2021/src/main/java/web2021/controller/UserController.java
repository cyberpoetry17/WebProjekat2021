package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.userService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.LoginDTO;
import web2021.dto.UserDTO;
import web2021.model.enums.UserType;

public class UserController {
	
	public static Route checkUsername = (Request request, Response response) -> {
		String username = request.params(":username");
		boolean isTaken = userService.checkIsUsernameTaken(username);
		if(isTaken) {
			return "taken";
		}
		else {
			return "not_taken";
		}
	};
	
	public static Route login = (Request request, Response response) -> {
		String payload = request.body();
		LoginDTO loginDTO = gson.fromJson(payload, LoginDTO.class);
		String json = "null";
		
		json = gson.toJson(userService.loginAsAdministrator(loginDTO));
		if(!json.equals("null")) {
			return json;
		}
		
		json = gson.toJson(userService.loginAsManager(loginDTO));
		if(!json.equals("null")) {
			return json;
		}
		
		json = gson.toJson(userService.loginAsCourier(loginDTO));
		if(!json.equals("null")) {
			return json;
		}
		
		json = gson.toJson(userService.loginAsCustomer(loginDTO));
		if(!json.equals("null")) {
			return json;
		}
		
		return json;
	};
	
	public static Route editProfile = (Request request, Response response) -> {
		String payload = request.body();
		UserDTO userDTO = gson.fromJson(payload, UserDTO.class);
		String json = null;
		if(userDTO.getUserType() == UserType.ADMINISTRATOR) {
			json = gson.toJson(userService.editProfileAdministrator(userDTO));
		}
		else if(userDTO.getUserType() == UserType.MANAGER) {
			json = gson.toJson(userService.editProfileManager(userDTO));
		}
		else if(userDTO.getUserType() == UserType.COURIER) {
			json = gson.toJson(userService.editProfileCourier(userDTO));	
		}
		else if(userDTO.getUserType() == UserType.CUSTOMER) {
			json = gson.toJson(userService.editProfileCustomer(userDTO));
		}
		return json;
	};
	
}
