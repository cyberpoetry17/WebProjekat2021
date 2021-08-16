package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.courierService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CustomerRegisterDTO;
import web2021.model.Courier;

public class CourierController {
	public static Route register = (Request request, Response response) -> {
		String payload = request.body();
		CustomerRegisterDTO customerRegisterDTO = gson.fromJson(payload, CustomerRegisterDTO.class);
		Courier courier = courierService.register(customerRegisterDTO);
		String json = gson.toJson(courier);
		return json;
	};
}
