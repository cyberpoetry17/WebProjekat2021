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

import web2021.model.Courier;

public class CourierRepository {
	
	private String filename;
	
	public CourierRepository() {
		this.filename = "couriers.json";
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Courier>());
		}
	}
	
	private void saveAll(Collection<Courier> couriers)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(couriers, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file couriers.json!");
		}
	}
	
	public ArrayList<Courier> getAll() {
		ArrayList<Courier> couriers = null;
		Type collectionType = new TypeToken<Collection<Courier>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			couriers = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file couriers.json!");
		}
		if(couriers == null) {
			return new ArrayList<Courier>();
		}
		else {
			couriers.removeIf(u -> u.isDeleted());
			return couriers;
		}
	}
	
	public Courier add(Courier newUser) {
		ArrayList<Courier> couriers = getAll();
		boolean exist = false;
		for(Courier courier : couriers) {
			if(courier.getId().equals(newUser.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			couriers.add(newUser);
			saveAll(couriers);
			return newUser;
		}
		return null;
	}
	
	public Courier update(Courier updateUser)
	{
		ArrayList<Courier> couriers = (ArrayList<Courier>) this.getAll();
		for (int i = 0; i < couriers.size(); i++) {
			if(couriers.get(i).getId().equals(updateUser.getId())) {
				couriers.set(i, updateUser);
				this.saveAll(couriers);
				return couriers.get(i);
			}
		}
		return null;
	}
	
	public Courier getById(Long id) {
		for(Courier courier : getAll()) {
			if(courier.getId().equals(id)) {
				return courier;
			}
		}
		return null;
	}
}
