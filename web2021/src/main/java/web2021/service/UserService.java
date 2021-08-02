package web2021.service;

import web2021.dto.UserDTO;
import web2021.model.User;
import web2021.repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository;
	
	public UserService() {
		this.userRepository = new UserRepository();
	}
	
	public UserService(String path) {
		this.userRepository = new UserRepository(path);
	}
	
	public User register(UserDTO userDTO) {
		User user = new User(userDTO);
		return userRepository.add(user);
	}
	
}
