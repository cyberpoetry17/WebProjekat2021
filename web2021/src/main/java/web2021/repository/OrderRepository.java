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

import web2021.model.Order;

public class OrderRepository {
	
	private String filename;
	
	public OrderRepository() {
		this.filename = "orders.json";
		this.initFile();
	}
	
	public OrderRepository(String path) {
		this.filename = path;
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Order>());
		}
	}
	
	private void saveAll(Collection<Order> users)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(users, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file orders.json!");
		}
	}
	
	public ArrayList<Order> getAll() {
		ArrayList<Order> users = null;
		Type collectionType = new TypeToken<Collection<Order>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			users = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file orders.json!");
		}
		if(users == null) {
			return new ArrayList<Order>();
		}
		else {
			users.removeIf(u -> u.isDeleted());
			return users;
		}
	}
	
	public Order add(Order newUser) {
		ArrayList<Order> users = getAll();
		boolean exist = false;
		for(Order order : users) {
			if(order.getId().equals(newUser.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			users.add(newUser);
			saveAll(users);
			return newUser;
		}
		return null;
	}
	
	public Order update(Order updateUser)
	{
		ArrayList<Order> users = (ArrayList<Order>) this.getAll();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getId().equals(updateUser.getId())) {
				users.set(i, updateUser);
				this.saveAll(users);
				return users.get(i);
			}
		}
		return null;
	}
	
	public Order getById(Long id) {
		for(Order order : getAll()) {
			if(order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}
	
}
