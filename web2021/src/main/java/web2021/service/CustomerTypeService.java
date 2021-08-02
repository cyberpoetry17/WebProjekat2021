package web2021.service;

import java.util.List;

import web2021.model.CustomerType;
import web2021.model.enums.CustomerTypeName;
import web2021.repository.CustomerTypeRepository;

public class CustomerTypeService {
	
	private CustomerTypeRepository customerTypeRepository;
	
	private double gold = 3000;
	private double silver = 2000;
	private double bronze = 1000;
	
	public CustomerTypeService() {
		this.customerTypeRepository = new CustomerTypeRepository();
	}
	
	public CustomerTypeService(String path) {
		this.customerTypeRepository = new CustomerTypeRepository(path);
	}
	
	public List<CustomerType> getAll() {
		return customerTypeRepository.getAll();
	}
	
	public CustomerType getCustomerTypeByPoints(double points) {
		if(points < bronze) {
			return getSpecificCustomerType(CustomerTypeName.NONE);
		}
		else if(points >= bronze && points < silver) {
			return getSpecificCustomerType(CustomerTypeName.BRONZE);
		}
		else if(points >= silver && points < gold) {
			return getSpecificCustomerType(CustomerTypeName.SILVER);
		}
		else {
			return getSpecificCustomerType(CustomerTypeName.GOLD);
		}
	}
	
	private CustomerType getSpecificCustomerType(CustomerTypeName customerTypeName) {
		for(CustomerType ct : getAll()) {
			if(ct.getCustomerTypeName() == customerTypeName) {
				return ct;
			}
		}
		return null;
	}
	
}
