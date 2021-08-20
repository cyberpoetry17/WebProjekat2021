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
	
	public static Route getOrderStatus = (Request request, Response response) -> {
		String json = gson.toJson(orderService.getOrderStatus());
		return json;
	};
	
	public static Route getOrdersForCustomer = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(orderService.getOrdersForCustomer(Long.parseLong(id)));
		return json;
	};
	
	public static Route getOrdersForCourier = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(orderService.getOrdersForCourier(Long.parseLong(id)));
		return json;
	};
	
	public static Route getOrdersForManager = (Request request, Response response) -> {
		String id = request.params(":id");
		String json = gson.toJson(orderService.getOrdersForManager(Long.parseLong(id)));
		return json;
	};
	
}
