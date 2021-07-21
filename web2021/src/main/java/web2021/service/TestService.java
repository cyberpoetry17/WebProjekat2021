package web2021.service;

import java.util.ArrayList;

import web2021.model.Test;
import web2021.repository.TestRepository;

public class TestService {
	
	private TestRepository testRepository;
		
	public TestService() {
		this.testRepository = new TestRepository();
	}
	
	public TestService(String path) {
		this.testRepository = new TestRepository(path);
	}
	
	public ArrayList<Test> getAll() {
		return testRepository.getAll();
	}
	
}
