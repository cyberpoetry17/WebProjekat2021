package web2021.controller;

import static web2021.utils.Responses.serverError;

import java.util.ArrayList;

import static web2021.utils.Responses.forbidden;
import static web2021.utils.Responses.ok;

import static web2021.Application.parseJws;
import static web2021.Application.gson;
import static web2021.Application.testService;

import spark.Request;
import spark.Response;
import spark.Route;
import web2021.dto.TestDTO;
import web2021.repository.TestRepository;
import web2021.service.TestService;

public class TestController {
	
//	private TestService testService;
//	
//	public TestController() {
//		this.testService = new TestService(new TestRepository());
//	}
//	
//	public TestController(TestService testService) {
//		this.testService = testService;
//	}
	
	public static Route getAll = (Request request, Response response) -> {
		ArrayList<Test> tests = testService.getAll();
		response.type("application/json");
	};
	
}
