package com.playspace.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.playspace.config.Configuration;
import com.playspace.exception.IncorrectUserIdException;
import com.playspace.model.User;
import com.playspace.repository.UserRepository;

public class LoginServiceTest {

	private LoginService loginService;
	
	private UserRepository userRepository;
	
	@Before
	public void init() {
		loginService = LoginService.getInstance();
		userRepository = UserRepository.getInstance();
		loginService.setUserRepository(userRepository);
	}
	
	@Test
	public void login_service_instance_test() {
		assertNotNull(loginService);
	}
	
	@Test
	public void is_logged_user_test() throws IncorrectUserIdException {
		User user = new User();
		user.setUserId("4711");
		user.setSessionKey(loginService.generateSessionKey());
		user.setSessionKeyCreationTime(System.currentTimeMillis());		
		userRepository.create(user);
		
		assertTrue(loginService.isLoggedUserSessionKey(user.getSessionKey()));
	}
	
	@Test
	public void is_valid_session_key_with_current_time_test() throws InterruptedException {
		assertTrue(loginService.isValidSessionKey(System.currentTimeMillis()));
	}
	
	@Test
	public void is_valid_session_key_with_future_time_test() throws InterruptedException {
		long someTimeInTheFuture = 4093797600000L; // 09/23/2099
		assertTrue(loginService.isValidSessionKey(someTimeInTheFuture));
	}
	
	@Test
	public void is_valid_session_key_with_past_time_test() throws InterruptedException {
		long someTimeLongAgo = 969660000000L; // 09/23/2000
		assertFalse(loginService.isValidSessionKey(someTimeLongAgo));
	}
	
	@Test
	public void generate_session_key_test() {
		String sessionKey = loginService.generateSessionKey();
		
		assertNotNull(sessionKey);
		assertTrue(sessionKey.length() == Configuration.SESSION_KEY_LENGHT);
	}

}
