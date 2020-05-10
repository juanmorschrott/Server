package com.playspace.repository;

import java.util.HashMap;
import java.util.Map;

import com.playspace.model.User;

public class UserRepository {
	
	private static UserRepository userRepository;
	
	private Map<String, User> users;

	private UserRepository() {
		users = new HashMap<>();
	}
	
	public static UserRepository getInstance() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}
	
	public synchronized User findUserByUserId(String userId) {
		return users.get(userId);
	}
	
	public synchronized User create(String userId, String sessionKey) {
		User u = new User();
		u.setUserId(userId);
		u.setSessionKey(sessionKey);
		u.setSessionKeyCreationTime(System.currentTimeMillis());
		users.put(userId, u);
		
		return u;
	}

}
