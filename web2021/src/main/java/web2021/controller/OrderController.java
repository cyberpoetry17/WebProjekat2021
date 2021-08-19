package web2021.controller;

import static web2021.Application.gson;
import static web2021.Application.orderService;

import spark.Request;
import spark.Response;
import spark.Route;

public class OrderController {
	
	public static Route makeOrder = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(orderService.makeOrder(Long.parseLong(id)));
		return json;
	};
	
}
