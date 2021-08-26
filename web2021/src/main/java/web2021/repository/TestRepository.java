package web2021.repository;

import static web2021.Application.gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import web2021.model.Test;


public class TestRepository {
	
	private String filename;
	
	public TestRepository() {
		this.filename = "tests.json";
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Test>());
		}
	}
	
	private void saveAll(Collection<Test> tests)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(tests, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file tests.json!");
		}
	}
	
	public ArrayList<Test> getAll() {
		ArrayList<Test> tests = null;
		Type collectionType = new TypeToken<Collection<Test>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			tests = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file tests.json!");
		}
		if(tests == null) {
			return new ArrayList<Test>();
		}
		else {
			tests.removeIf(t -> t.isDeleted());
			return tests;
		}
	}
	
	public Test add(Test newTest) {
		ArrayList<Test> tests = getAll();
		boolean exist = false;
		for(Test test : tests) {
			if(test.getId().equals(newTest.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			tests.add(newTest);
			saveAll(tests);
			return newTest;
		}
		return null;
	}
	
	public Test update(Test updateTest)
	{
		ArrayList<Test> tests = (ArrayList<Test>) this.getAll();
		for (int i = 0; i < tests.size(); i++) {
			if(tests.get(i).getId().equals(updateTest.getId())) {
				tests.set(i, updateTest);
				this.saveAll(tests);
				return tests.get(i);
			}
		}
		return null;
	}
	
	public Test getById(Long id) {
		for(Test test : getAll()) {
			if(test.getId().equals(id)) {
				return test;
			}
		}
		return null;
	}
}
