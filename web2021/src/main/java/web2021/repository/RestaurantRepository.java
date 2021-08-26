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

import web2021.model.Restaurant;

public class RestaurantRepository {
	
	private String filename;
	
	public RestaurantRepository() {
		this.filename = "restaurants.json";
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Restaurant>());
		}
	}
	
	private void saveAll(Collection<Restaurant> restaurants)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(restaurants, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file restaurants.json!");
		}
	}
	
	public ArrayList<Restaurant> getAll() {
		ArrayList<Restaurant> restaurants = null;
		Type collectionType = new TypeToken<Collection<Restaurant>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			restaurants = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file restaurants.json!");
		}
		if(restaurants == null) {
			return new ArrayList<Restaurant>();
		}
		else {
			restaurants.removeIf(u -> u.isDeleted());
			return restaurants;
		}
	}
	
	public Restaurant add(Restaurant newUser) {
		ArrayList<Restaurant> restaurants = getAll();
		boolean exist = false;
		for(Restaurant restaurant : restaurants) {
			if(restaurant.getId().equals(newUser.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			restaurants.add(newUser);
			saveAll(restaurants);
			return newUser;
		}
		return null;
	}
	
	public Restaurant update(Restaurant updateUser)
	{
		ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) this.getAll();
		for (int i = 0; i < restaurants.size(); i++) {
			if(restaurants.get(i).getId().equals(updateUser.getId())) {
				restaurants.set(i, updateUser);
				this.saveAll(restaurants);
				return restaurants.get(i);
			}
		}
		return null;
	}
	
	public Restaurant getById(Long id) {
		for(Restaurant restaurant : getAll()) {
			if(restaurant.getId().equals(id)) {
				return restaurant;
			}
		}
		return null;
	}
}
