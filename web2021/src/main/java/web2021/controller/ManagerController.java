package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.managerService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.CustomerRegisterDTO;
import web2021.model.Manager;

public class ManagerController {
	public static Route register = (Request request, Response response) -> {
		String payload = request.body();
		CustomerRegisterDTO customerRegisterDTO = gson.fromJson(payload, CustomerRegisterDTO.class);
		Manager manager = managerService.register(customerRegisterDTO);
		String json = gson.toJson(manager);
		return json;
	};
	
	public static Route getAllManagers = (Request request, Response response) -> {
		String json = gson.toJson(managerService.getAllManagers());
		return json;
	};
}
