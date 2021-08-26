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

import web2021.model.Comment;

public class CommentRepository {
	
	private String filename;
	
	public CommentRepository() {
		this.filename = "comments.json";
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Comment>());
		}
	}
	
	private void saveAll(Collection<Comment> users)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(users, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file comments.json!");
		}
	}
	
	public ArrayList<Comment> getAll() {
		ArrayList<Comment> users = null;
		Type collectionType = new TypeToken<Collection<Comment>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			users = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file comments.json!");
		}
		if(users == null) {
			return new ArrayList<Comment>();
		}
		else {
			users.removeIf(u -> u.isDeleted());
			return users;
		}
	}
	
	public Comment add(Comment newUser) {
		ArrayList<Comment> users = getAll();
		boolean exist = false;
		for(Comment comment : users) {
			if(comment.getId().equals(newUser.getId())) {
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
	
	public Comment update(Comment updateUser)
	{
		ArrayList<Comment> users = (ArrayList<Comment>) this.getAll();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getId().equals(updateUser.getId())) {
				users.set(i, updateUser);
				this.saveAll(users);
				return users.get(i);
			}
		}
		return null;
	}
	
	public Comment getById(Long id) {
		for(Comment comment : getAll()) {
			if(comment.getId().equals(id)) {
				return comment;
			}
		}
		return null;
	}
	
}
