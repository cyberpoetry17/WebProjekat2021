package web2021.controller;

import static web2021.Application.gson;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.AddToShoppingCartDTO;
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
	
	public static Route getAllCustomers = (Request request, Response response) -> {
		String json = gson.toJson(customerService.getAllCustomers());
		return json;
	};
	
	public static Route  addArticle = (Request request, Response response) -> {
		String payload = request.body();
		AddToShoppingCartDTO addToShoppingCartDTO = gson.fromJson(payload, AddToShoppingCartDTO.class);
		String json = gson.toJson(customerService.addArticle(addToShoppingCartDTO));
		return json;
	};
	
}
