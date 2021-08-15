package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.userService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.LoginDTO;

public class UserController {

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
	
}
