package web2021.service;

import java.util.ArrayList;
import java.util.List;

import web2021.dto.CustomerRegisterDTO;
import web2021.model.Manager;
import web2021.model.Restaurant;
import web2021.repository.ManagerRepository;
import web2021.repository.RestaurantRepository;

public class ManagerService {
	
	private ManagerRepository managerRepository;
	private RestaurantRepository restaurantRepository;
	
	public ManagerService() {
		this.managerRepository = new ManagerRepository();
		this.restaurantRepository = new RestaurantRepository();
	}
	
	public ManagerService(String path) {
		this.managerRepository = new ManagerRepository(path);
		this.restaurantRepository = new RestaurantRepository();
	}
	
	public Manager register(CustomerRegisterDTO customerRegisterDTO) {
		Manager manager = new Manager();
		manager.setId(System.currentTimeMillis());
		manager.setDeleted(false);
		manager.setBlocked(false);
		manager.setUsername(customerRegisterDTO.getUsername());
		manager.setPassword(customerRegisterDTO.getPassword());
		manager.setName(customerRegisterDTO.getName());
		manager.setSurname(customerRegisterDTO.getSurname());
		manager.setGenderType(customerRegisterDTO.getGenderType());
		manager.setBirthday(customerRegisterDTO.getBirthday());
		manager.setUserType(customerRegisterDTO.getUserType());
		manager.setImage("../img/profile.jpg");
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
	
	public void assignRestaurantToManager(Long managerId, Long restaurantId) {
		Manager manager = managerRepository.getById(managerId);
		Restaurant restaurant = restaurantRepository.getById(restaurantId);
		manager.setRestaurant(restaurant);
		managerRepository.update(manager);
	}
	
}
