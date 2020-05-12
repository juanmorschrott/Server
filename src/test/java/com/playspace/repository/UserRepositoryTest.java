package com.playspace.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.playspace.exception.IncorrectUserIdException;
import com.playspace.model.User;

public class UserRepositoryTest {

	UserRepository userRepository;

	@Before
	public void init() {
		userRepository = UserRepository.getInstance();
	}

	@Test
	public void create_user_should_have_correct_parameters() {
		try {
			User user = new User();
			user.setUserId(1);
			userRepository.create(user);
		} catch (IncorrectUserIdException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void find_user_by_session_id_should_return_null_if_not_exists_test() {
		assertNull(userRepository.findUserBySessionId(""));
	}

	@Test
	public void find_user_by_session_id_should_find_user_if_exists_test() throws IncorrectUserIdException {
		String fakeKey = "fake key";

		User user = new User();
		user.setUserId(1);
		user.setSessionKey(fakeKey);
		userRepository.create(user);

		User result = userRepository.findUserBySessionId(fakeKey);

		assertNotNull(result);
		assert (result.getSessionKey().equals(fakeKey));
	}

	@Test
	public void find_user_by_id_test() throws IncorrectUserIdException {
		int fakeId = 4711;

		User user = new User();
		user.setUserId(fakeId);
		user.setSessionKey("fake key");
		userRepository.create(user);

		User result = userRepository.findUserByUserId(fakeId);

		assertNotNull(result);
		assert(result.getUserId() == fakeId);
	}

}
