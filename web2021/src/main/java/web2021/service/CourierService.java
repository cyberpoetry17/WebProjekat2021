package web2021.service;

import web2021.dto.CustomerRegisterDTO;
import web2021.model.Courier;
import web2021.repository.CourierRepository;

public class CourierService {
	
	private CourierRepository courierRepository;
	
	public CourierService() {
		this.courierRepository = new CourierRepository();
	}
	
	public CourierService(String path) {
		this.courierRepository = new CourierRepository(path);
	}
	
	public Courier register(CustomerRegisterDTO customerRegisterDTO) {
		Courier courier = new Courier();
		courier.setId(System.currentTimeMillis());
		courier.setDeleted(false);
		courier.setUsername(customerRegisterDTO.getUsername());
		courier.setPassword(customerRegisterDTO.getPassword());
		courier.setName(customerRegisterDTO.getName());
		courier.setSurname(customerRegisterDTO.getSurname());
		courier.setGenderType(customerRegisterDTO.getGenderType());
		courier.setBirthday(customerRegisterDTO.getBirthday());
		courier.setUserType(customerRegisterDTO.getUserType());
		return courierRepository.add(courier);
	}
	
}
