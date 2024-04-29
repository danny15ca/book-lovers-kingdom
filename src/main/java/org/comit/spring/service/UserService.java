package org.comit.spring.service;

import org.comit.spring.bean.User;
import org.comit.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User findUser(User user){
		
		return this.userDao.findUser(user);
	}
	
	public User findUser(String username){
		
		return this.userDao.findUser(username);
	}
	
	@Transactional
	public void createUser(User user){
		
		this.validateUser(user);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		this.userDao.signUpUser(user);
	}
	
	private void validateUser(User user) {
		
		if (user.getFirstName().isEmpty() || 
			 user.getLastName().isEmpty() ||
			 user.getUsername().isEmpty() ||
			 user.getEmail().isEmpty()) {
			
			throw new RuntimeException("Invalid User Data: " + user);
		}
			
	}

}
