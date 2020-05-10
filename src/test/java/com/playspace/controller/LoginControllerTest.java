package com.playspace.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoginControllerTest {

	private LoginController loginController;
	
	@Before
	public void init() {
		loginController = LoginController.getInstance();
	}
	
	@Test
	public void controller_instance_test() {
		assertNotNull(loginController);
	}

}
