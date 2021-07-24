package web2021.service;

import java.util.ArrayList;

import web2021.dto.TestDTO;
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
	
	public Test addTest(TestDTO testDTO) {
//		Test test = new Test(testDTO);
		Test test = new Test();
		test.setId(System.currentTimeMillis());
		test.setDeleted(false);
		test.setName("Test " + getAll().size());
		test.setUser(testDTO.getUser());
		test.getUser().setId(System.currentTimeMillis());
		testRepository.add(test);
		return test;
	}
	
	public Test updateTest(TestDTO testDTO) {
		if(testDTO == null) {
			return null;
		}
		Test test = testRepository.getById(testDTO.getId());
		if(test == null) {
			return null;
		}
		test.setDeleted(testDTO.isDeleted());
		test.setName(testDTO.getName());
		test.setUser(testDTO.getUser());
		testRepository.update(test);
		return test;
	}
	
	public Test deleteTest(Long id) {
		Test test = testRepository.getById(id);
		if(test == null) {
			return null;
		}
		test.setDeleted(true);
		testRepository.update(test);
		return test;
	}
	
}
