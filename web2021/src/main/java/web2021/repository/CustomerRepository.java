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

import web2021.model.Customer;

public class CustomerRepository {
	
private String filename;
	
	public CustomerRepository() {
		this.filename = "customers.json";
		this.initFile();
	}
	
	private void initFile()
	{
		File file = new File(this.filename);
		if (!file.isFile())
		{
			this.saveAll(new ArrayList<Customer>());
		}
	}
	
	private void saveAll(Collection<Customer> customers)
	{
		try (FileWriter writer = new FileWriter(this.filename))
		{
			gson.toJson(customers, writer);
		} catch (JsonIOException | IOException e)
		{
//			e.printStackTrace();
			System.out.println("There was an error trying to save to file customers.json!");
		}
	}
	
	public ArrayList<Customer> getAll() {
		ArrayList<Customer> customers = null;
		Type collectionType = new TypeToken<Collection<Customer>>(){}.getType();
		try(FileReader freader = new FileReader(this.filename); JsonReader jreader = new JsonReader(freader)) {
			customers = gson.fromJson(jreader, collectionType);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("There was an error trying to read from file customers.json!");
		}
		if(customers == null) {
			return new ArrayList<Customer>();
		}
		else {
			customers.removeIf(t -> t.isDeleted());
			return customers;
		}
	}
	
	public Customer add(Customer newCustomer) {
		ArrayList<Customer> customers = getAll();
		boolean exist = false;
		for(Customer customer : customers) {
			if(customer.getId().equals(newCustomer.getId())) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			customers.add(newCustomer);
			saveAll(customers);
			return newCustomer;
		}
		return null;
	}
	
	public Customer update(Customer updateCustomer)
	{
		ArrayList<Customer> customers = (ArrayList<Customer>) this.getAll();
		for (int i = 0; i < customers.size(); i++) {
			if(customers.get(i).getId().equals(updateCustomer.getId())) {
				customers.set(i, updateCustomer);
				this.saveAll(customers);
				return customers.get(i);
			}
		}
		return null;
	}
	
	public Customer getById(Long id) {
		for(Customer customer : getAll()) {
			if(customer.getId().equals(id)) {
				return customer;
			}
		}
		return null;
	}
	
}
