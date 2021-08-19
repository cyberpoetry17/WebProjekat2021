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
	
	private void saveAll(Collection<Order> orders)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(orders, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file orders.json!");
		}
	}
	
	public ArrayList<Order> getAll() {
		ArrayList<Order> orders = null;
		Type collectionType = new TypeToken<Collection<Order>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			orders = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file orders.json!");
		}
		if(orders == null) {
			return new ArrayList<Order>();
		}
		else {
			orders.removeIf(u -> u.isDeleted());
			return orders;
		}
	}
	
	public Order add(Order newUser) {
		ArrayList<Order> orders = getAll();
		boolean exist = false;
		for(Order order : orders) {
			if(order.getId().equals(newUser.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			orders.add(newUser);
			saveAll(orders);
			return newUser;
		}
		return null;
	}
	
	public Order update(Order updateUser)
	{
		ArrayList<Order> orders = (ArrayList<Order>) this.getAll();
		for (int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getId().equals(updateUser.getId())) {
				orders.set(i, updateUser);
				this.saveAll(orders);
				return orders.get(i);
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

