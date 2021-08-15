package web2021.controller;

import static web2021.Application.gson;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CustomerRegisterDTO;
import web2021.dto.LoginDTO;
import web2021.model.Customer;

import static web2021.Application.customerService;

public class CustomerController {
	
	public static Route login = (Request request, Response response) -> {
		String payload = request.body();
		LoginDTO loginDTO = gson.fromJson(payload, LoginDTO.class);
		Customer customer = customerService.login(loginDTO);
		String json = gson.toJson(customer);
		return json;
	};
	
	public static Route checkUsername = (Request request, Response response) -> {
		String username = request.params(":username");
		boolean isTaken = customerService.checkIsUsernameTaken(username);
		if(isTaken) {
			return "taken";
		}
		else {
			return "not_taken";
		}
	};
	
	public static Route register = (Request request, Response response) -> {
		String payload = request.body();
		CustomerRegisterDTO customerRegisterDTO = gson.fromJson(payload, CustomerRegisterDTO.class);
		Customer customer = customerService.register(customerRegisterDTO);
		String json = gson.toJson(customer);
		return json;
	};
	
}
