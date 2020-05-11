package com.playspace.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.playspace.exception.IncorrectUserIdException;
import com.playspace.model.User;

public class UserRepository {

	private static UserRepository userRepository;

	private Set<User> users;

	private UserRepository() {
		users = new HashSet<>();
	}

	public static UserRepository getInstance() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}

	public synchronized Set<User> findAll() {
		return this.users;
	}

	/**
	 * Find user by id.
	 * 
	 * @param userId
	 * @return
	 */
	public synchronized User findUserByUserId(String userId) {
		for (User u : this.users) {
			if (u.getUserId().equals(userId))
				return u;
		}
		return null;
	}
	
	/**
	 * Find user by session key.
	 * 
	 * @param sessionKey
	 * @return
	 */
	public synchronized User findUserBySessionId(String sessionKey) {
		return userRepository.findAll()
				.stream()
				.filter(user -> user.getSessionKey().equals(sessionKey))
				.findFirst().get();
	}

	/**
	 * Creates new user giving user id and session key.
	 * 
	 * @param userId
	 * @param sessionKey
	 * @return
	 * @throws IncorrectUserIdException
	 */
	public synchronized User create(User user) throws IncorrectUserIdException {
		if (user.getUserId() == null || user.getUserId().isEmpty() || user.getSessionKey() == null
				|| user.getSessionKey().isEmpty() || user.getSessionKey().length() > 16) {
			throw new IncorrectUserIdException("You have not provided the correct parameters to create a user.");
		}
		users.add(user);

		return user;
	}

}
