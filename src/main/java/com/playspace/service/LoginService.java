package com.playspace.service;

import static com.playspace.config.Configuration.*;

import java.util.Random;

import com.playspace.exception.IncorrectUserIdException;
import com.playspace.model.User;
import com.playspace.repository.UserRepository;

public class LoginService {

	private UserRepository userRepository;
	
	private static LoginService loginService;

	private LoginService() {
		userRepository = UserRepository.getInstance();
	}

	public static LoginService getInstance() {
		if (loginService == null) {
			loginService = new LoginService();
		}
		return loginService;
	}
	
	/**
	 * Check if given user is logged in.
	 * 
	 * @param userId
	 * @return
	 */
	public synchronized boolean isLoggedUser(String userId) {
		User user = userRepository.findUserByUserId(userId);
		if (user != null) {
			if (isValidSessionKey(user.getSessionKeyCreationTime())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retrieves session key given a user id.
	 * If the user is not logged in, it creates a new User
	 * with a new session key.
	 * 
	 * @param userId
	 * @return
	 * @throws IncorrectUserIdException 
	 */
	public synchronized String getSessionKey(String userId) throws IncorrectUserIdException {
		User user = userRepository.findUserByUserId(userId);
		if (user != null) {
			if (!isValidSessionKey(user.getSessionKeyCreationTime())) {
				user.setSessionKey(generateSessionKey());
			}
		} else {
			user = userRepository.create(userId, generateSessionKey());
		}
		return user.getSessionKey();
	}

	/**
	 * Checks if more than configured time has passed since given time.
	 * 
	 * @param startTime
	 * @return
	 */
	public synchronized boolean isValidSessionKey(long startTime) {
		long currentTime = System.currentTimeMillis();

		return (currentTime - startTime) < SESSION_KEY_DURATION;
	}

	/**
	 * Generates a session key id form of a string without spaces
	 * or 'strange' characters.
	 * 
	 * @return
	 */
	public String generateSessionKey() {
		int leftLimit = 65; // letter 'A'
		int rightLimit = 90; // letter 'Z'
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(SESSION_KEY_LENGHT)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

}
