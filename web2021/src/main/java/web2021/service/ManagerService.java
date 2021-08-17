package web2021.service;

import java.util.ArrayList;
import java.util.List;

import web2021.dto.CustomerRegisterDTO;
import web2021.model.Manager;
import web2021.repository.ManagerRepository;

public class ManagerService {
	
	private ManagerRepository managerRepository;
	
	public ManagerService() {
		this.managerRepository = new ManagerRepository();
	}
	
	public ManagerService(String path) {
		this.managerRepository = new ManagerRepository(path);
	}
	
	public Manager register(CustomerRegisterDTO customerRegisterDTO) {
		Manager manager = new Manager();
		manager.setId(System.currentTimeMillis());
		manager.setDeleted(false);
		manager.setUsername(customerRegisterDTO.getUsername());
		manager.setPassword(customerRegisterDTO.getPassword());
		manager.setName(customerRegisterDTO.getName());
		manager.setSurname(customerRegisterDTO.getSurname());
		manager.setGenderType(customerRegisterDTO.getGenderType());
		manager.setBirthday(customerRegisterDTO.getBirthday());
		manager.setUserType(customerRegisterDTO.getUserType());
		return managerRepository.add(manager);
	}
	
	public List<Manager> getAllManagers() {
		return managerRepository.getAll();
	}
	
	public List<Manager> getManagersWithoutRestaurant() {
		List<Manager> managers = new ArrayList<Manager>();
		for(Manager manager : managerRepository.getAll()) {
			if(manager.getRestaurant() == null) {
				managers.add(manager);
			}
		}
		return managers;
	}
	
}
