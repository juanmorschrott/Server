package com.playspace.repository;

import java.util.HashMap;
import java.util.Map;

import com.playspace.model.User;

public class UserRepository {
	
	private UserRepository userRepository;
	
	private static Map<String, User> users;

	private UserRepository() {
		users = new HashMap<>();
	}
	
	public UserRepository getInstance() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}
	
	public User findUserByUserId(String userId) {
		return users.get(userId);
	}
	
	public User create(String userId, String sessionKey) {
		User u = new User();
		u.setUserId(userId);
		u.setSessionKey(sessionKey);
		u.setSessionKeyCreationTime(System.currentTimeMillis());
		users.put(userId, u);
		
		return u;
	}

}
