package com.playspace.service;

import static com.playspace.config.Configuration.SESSION_KEY_DURATION;

import java.util.Random;

import com.playspace.model.User;
import com.playspace.repository.UserRepository;

public class LoginService {

	private UserRepository userRepository;
	
	private static LoginService loginService;

	private LoginService() {
		userRepository = userRepository.getInstance();
	}

	public static LoginService getInstance() {
		if (loginService == null) {
			loginService = new LoginService();
		}
		return loginService;
	}
	
	public synchronized String getSessionKey(String userId) {
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

	public boolean isValidSessionKey(long startTime) {
		long currentTime = System.currentTimeMillis();

		return ((currentTime - startTime) * 1000) < SESSION_KEY_DURATION;
	}

	public String generateSessionKey() {
		int leftLimit = 65; // letter 'A'
		int rightLimit = 90; // letter 'Z'
		int targetStringLength = 10;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

}
