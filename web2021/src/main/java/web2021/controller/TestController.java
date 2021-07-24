package web2021.controller;

import static web2021.utils.Responses.serverError;

import java.util.ArrayList;

import static web2021.Application.gson;
import static web2021.Application.testService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.TestDTO;
import web2021.model.Test;

public class TestController {
	
	public static Route getAll = (Request request, Response response) -> {
		ArrayList<Test> tests = testService.getAll();
		String returnToFront = gson.toJson(tests);
		return returnToFront;
	};
	
	public static Route addTest = (Request request, Response response) -> {
		String payload = request.body();
		TestDTO testDTO = gson.fromJson(payload, TestDTO.class);
		Test newTest = testService.addTest(testDTO);
		if(newTest == null) {
			return serverError("test with same id already exist", response);
		}
		String returnToFront = gson.toJson(newTest);
		return returnToFront;
	};
	
	public static Route updateTest = (Request request, Response response) -> {
		String payload = request.body();
		TestDTO testDTO = gson.fromJson(payload, TestDTO.class);
		Test updateTest = testService.updateTest(testDTO);
		if(updateTest == null) {
			return serverError("test with given id doesnt exist", response);
		}
		String returnToFront = gson.toJson(updateTest);
		return returnToFront;
	};
	
	public static Route deleteTest = (Request request, Response response) -> {
		String id = request.params(":id");
		Test deletedTest = testService.deleteTest(Long.parseLong(id));
		if(deletedTest == null) {
			return serverError("test with given id doesnt exist", response);
		}
		String returnToFront = gson.toJson(deletedTest);
		return returnToFront;
	};
}
