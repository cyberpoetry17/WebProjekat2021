package web2021.service;

import web2021.repository.ManagerRepository;

public class ManagerService {
	
	private ManagerRepository managerRepository;
	
	public ManagerService() {
		this.managerRepository = new ManagerRepository();
	}
	
	public ManagerService(String path) {
		this.managerRepository = new ManagerRepository(path);
	}
	
}
