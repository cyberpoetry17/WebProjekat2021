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

import web2021.model.CustomerType;

public class CustomerTypeRepository {
	
private String filename;
	
	public CustomerTypeRepository() {
		this.filename = "customerTypes.json";
		this.initFile();
	}
	
	public CustomerTypeRepository(String path) {
		this.filename = path;
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<CustomerType>());
		}
	}
	
	private void saveAll(Collection<CustomerType> customerTypes)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(customerTypes, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file customerTypes.json!");
		}
	}
	
	public ArrayList<CustomerType> getAll() {
		ArrayList<CustomerType> customerTypes = null;
		Type collectionType = new TypeToken<Collection<CustomerType>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			customerTypes = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file customerTypes.json!");
		}
		if(customerTypes == null) {
			return new ArrayList<CustomerType>();
		}
		return customerTypes;
//		else {
//			customerTypes.removeIf(t -> t.isDeleted());
//			return customerTypes;
//		}
	}
	
	public CustomerType add(CustomerType newCustomerType) {
		ArrayList<CustomerType> customerTypes = getAll();
		boolean exist = false;
		for(CustomerType customerType : customerTypes) {
			if(customerType.getId().equals(newCustomerType.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			customerTypes.add(newCustomerType);
			saveAll(customerTypes);
			return newCustomerType;
		}
		return null;
	}
	
	public CustomerType update(CustomerType updateCustomerType)
	{
		ArrayList<CustomerType> customerTypes = (ArrayList<CustomerType>) this.getAll();
		for (int i = 0; i < customerTypes.size(); i++) {
			if(customerTypes.get(i).getId().equals(updateCustomerType.getId())) {
				customerTypes.set(i, updateCustomerType);
				this.saveAll(customerTypes);
				return customerTypes.get(i);
			}
		}
		return null;
	}
	
	public CustomerType getById(Long id) {
		for(CustomerType customerType : getAll()) {
			if(customerType.getId().equals(id)) {
				return customerType;
			}
		}
		return null;
	}
	
}
