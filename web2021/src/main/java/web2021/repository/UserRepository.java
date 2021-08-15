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

import web2021.model.User;

public class UserRepository {
	
	private String filename;
	
	public UserRepository() {
		this.filename = "users.json";
		this.initFile();
	}
	
	public UserRepository(String path) {
		this.filename = path;
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<User>());
		}
	}
	
	private void saveAll(Collection<User> users)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(users, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file users.json!");
		}
	}
	
	public ArrayList<User> getAll() {
		ArrayList<User> users = null;
		Type collectionType = new TypeToken<Collection<User>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			users = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file users.json!");
		}
		if(users == null) {
			return new ArrayList<User>();
		}
		else {
			users.removeIf(u -> u.isDeleted());
			return users;
		}
	}
	
	public User add(User newUser) {
		ArrayList<User> users = getAll();
		boolean exist = false;
		for(User user : users) {
			if(user.getId().equals(newUser.getId())) {
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
	
	public User update(User updateUser)
	{
		ArrayList<User> users = (ArrayList<User>) this.getAll();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getId().equals(updateUser.getId())) {
				users.set(i, updateUser);
				this.saveAll(users);
				return users.get(i);
			}
		}
		return null;
	}
	
	public User getById(Long id) {
		for(User user : getAll()) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
}
