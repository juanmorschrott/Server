package com.playspace.service;

import static com.playspace.config.Configuration.SESSION_KEY_DURATION;
import static com.playspace.config.Configuration.SESSION_KEY_LENGHT;

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
	 * Check if user is currently logged in.
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean isLoggedUser(User user) {
		User u = userRepository.findUserByUserId(user.getUserId());
		if (u != null && isValidSessionKey(user.getSessionKeyCreationTime())) {
			return true;
		}
		return false;
	}

	/**
	 * Check if a user is logged given a session key.
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isLoggedUserSessionKey(String sessionKey) {
		User u = userRepository.findUserBySessionId(sessionKey);

		return isValidSessionKey(u.getSessionKeyCreationTime());
	}

	/**
	 * Generates a new session key given an existent user. If the user is not logged
	 * in, it creates a new User with a new session key.
	 * 
	 * @param userId
	 * @return
	 * @throws IncorrectUserIdException
	 */
	public String generateSession(String userId) throws IncorrectUserIdException {
		User user = userRepository.findUserByUserId(userId);
		if (user != null) {
			if (!isValidSessionKey(user.getSessionKeyCreationTime())) {
				user.setSessionKey(generateSessionKey());
			}
		} else {
			user = new User();
			user.setUserId(userId);
			user.setSessionKey(generateSessionKey());
			user.setSessionKeyCreationTime(System.currentTimeMillis());
			user = userRepository.create(user);
		}
		return user.getSessionKey();
	}

	/**
	 * Checks if more than configured time has passed since given time.
	 * 
	 * @param startTime
	 * @return
	 */
	public boolean isValidSessionKey(long startTime) {
		long currentTime = System.currentTimeMillis();

		return (currentTime - startTime) < SESSION_KEY_DURATION;
	}

	/**
	 * Generates a session key id form of a string without spaces or 'strange'
	 * characters.
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

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static LoginService getLoginService() {
		return loginService;
	}

	public static void setLoginService(LoginService loginService) {
		LoginService.loginService = loginService;
	}

}
