package com.playspace.repository;

import java.util.HashSet;
import java.util.Set;

import com.playspace.exception.IncorrectUserIdException;
import com.playspace.model.User;

public class UserRepository {

	private static UserRepository userRepository;

	private Set<User> users;

	private UserRepository() {
		users = new HashSet<>();
	}

	public static synchronized UserRepository getInstance() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}

	/**
	 * Get all users;
	 */
	public Set<User> findAll() {
		return this.users;
	}

	/**
	 * Find user by id.
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserByUserId(int userId) {
		for (User u : this.users) {
			if (u.getUserId() == userId)
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
	public User findUserBySessionId(String sessionKey) {
		for (User u : this.users) {
			if (u.getSessionKey().equals(sessionKey)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Creates new user giving user id and session key.
	 * 
	 * @param userId
	 * @param sessionKey
	 * @return
	 * @throws IncorrectUserIdException
	 */
	public User create(User user) throws IncorrectUserIdException {
		if (user.getUserId() == null || user.getSessionKey() == null || user.getSessionKey().isEmpty()
				|| user.getSessionKey().length() > 16) {
			throw new IncorrectUserIdException("You have not provided the correct parameters to create a user.");
		}
		users.add(user);

		return user;
	}

}
