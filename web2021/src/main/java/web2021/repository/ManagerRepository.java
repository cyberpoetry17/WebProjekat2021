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

import web2021.model.Manager;

public class ManagerRepository {
	
	private String filename;
	
	public ManagerRepository() {
		this.filename = "managers.json";
		this.initFile();
	}
	
	public ManagerRepository(String path) {
		this.filename = path;
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Manager>());
		}
	}
	
	private void saveAll(Collection<Manager> managers)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(managers, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file managers.json!");
		}
	}
	
	public ArrayList<Manager> getAll() {
		ArrayList<Manager> managers = null;
		Type collectionType = new TypeToken<Collection<Manager>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			managers = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file managers.json!");
		}
		if(managers == null) {
			return new ArrayList<Manager>();
		}
		else {
			managers.removeIf(u -> u.isDeleted());
			return managers;
		}
	}
	
	public Manager add(Manager newManager) {
		ArrayList<Manager> managers = getAll();
		boolean exist = false;
		for(Manager manager : managers) {
			if(manager.getId().equals(newManager.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			managers.add(newManager);
			saveAll(managers);
			return newManager;
		}
		return null;
	}
	
	public Manager update(Manager updateManager)
	{
		ArrayList<Manager> managers = (ArrayList<Manager>) this.getAll();
		for (int i = 0; i < managers.size(); i++) {
			if(managers.get(i).getId().equals(updateManager.getId())) {
				managers.set(i, updateManager);
				this.saveAll(managers);
				return managers.get(i);
			}
		}
		return null;
	}
	
	public Manager getById(Long id) {
		for(Manager manager : getAll()) {
			if(manager.getId().equals(id)) {
				return manager;
			}
		}
		return null;
	}
}
