package web2021.service;

import web2021.repository.AdministratorRepository;

public class AdministratorService {
	
	private AdministratorRepository administratorRepository;
	
	public AdministratorService() {
		this.administratorRepository = new AdministratorRepository();
	}
	
	public AdministratorService(String path) {
		this.administratorRepository = new AdministratorRepository(path);
	}
	
}
