package com.playspace.context;

import static com.playspace.config.Configuration.SESSION_KEY_DURATION;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.playspace.cache.User;

public class Session {

	private static Session session;

	private static Map<String, User> users;

	private Session() {
		users = new HashMap<>();
	}

	public static Session getInstance() {
		if (session == null) {
			session = new Session();
		}
		return session;
	}
	
	public static synchronized String getSessionKey(String userID) {
		User user = users.get(userID);
		if (user != null) {
			if (!isValidSessionKey(user.getSessionKeyCreationTime())) {
				user.setSessionKey(generateSessionKey());
			} 
			
			return user.getSessionKey();
		} else {
			user = new User();
			user.setUserId(userID);
			user.setSessionKey(generateSessionKey());
			user.setSessionKeyCreationTime(System.currentTimeMillis());
			users.put(userID, user);
			
			return user.getSessionKey();
		}
	}

	private static boolean isValidSessionKey(long startTime) {
		long currentTime = System.currentTimeMillis();

		return ((currentTime - startTime) * 1000) < SESSION_KEY_DURATION;
	}

	private static String generateSessionKey() {
		int leftLimit = 65; // letter 'A'
		int rightLimit = 90; // letter 'Z'
		int targetStringLength = 10;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

}
