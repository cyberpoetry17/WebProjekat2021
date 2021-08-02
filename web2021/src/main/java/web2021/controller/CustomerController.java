package web2021.controller;

import static web2021.Application.gson;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CustomerRegisterDTO;
import web2021.model.Customer;

import static web2021.Application.customerService;

public class CustomerController {
	
	public static Route register = (Request request, Response response) -> {
		String payload = request.body();
		CustomerRegisterDTO customerRegisterDTO = gson.fromJson(payload, CustomerRegisterDTO.class);
		Customer customer = customerService.register(customerRegisterDTO);
		String json = gson.toJson(customer);
		return json;
	};
	
}
