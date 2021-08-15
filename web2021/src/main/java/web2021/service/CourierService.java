package web2021.service;

import web2021.repository.CourierRepository;

public class CourierService {
	
	private CourierRepository courierRepository;
	
	public CourierService() {
		this.courierRepository = new CourierRepository();
	}
	
	public CourierService(String path) {
		this.courierRepository = new CourierRepository(path);
	}
}
