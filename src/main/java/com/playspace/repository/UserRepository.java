package com.playspace.repository;

import java.util.HashMap;
import java.util.Map;

import com.playspace.exception.IncorrectUserIdException;
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

	/**
	 * Find user by id.
	 * 
	 * @param userId
	 * @return
	 */
	public synchronized User findUserByUserId(String userId) {
		return users.get(userId);
	}

	/**
	 * Creates new user giving user id and session key.
	 * 
	 * @param userId
	 * @param sessionKey
	 * @return
	 * @throws IncorrectUserIdException 
	 */
	public synchronized User create(String userId, String sessionKey) throws IncorrectUserIdException {
		if (userId.isEmpty() || userId.length() > 16 || sessionKey.isEmpty()) {
			throw new IncorrectUserIdException("You have not provided the correct parameters to create a user.");
		}
		User u = new User();
		u.setUserId(userId);
		u.setSessionKey(sessionKey);
		u.setSessionKeyCreationTime(System.currentTimeMillis());
		users.put(userId, u);

		return u;
	}

}
