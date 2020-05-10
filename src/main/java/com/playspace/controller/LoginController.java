package com.playspace.controller;

import com.playspace.HttpResponse;
import com.sun.net.httpserver.HttpExchange;

public class LoginController implements Controller {

	private static LoginController loginController;
	
	private LoginController() {
		
	}
	
	public static LoginController getInstance() {
		if (loginController == null) {
			loginController = new LoginController();
		}
		return loginController;
	}
	
	@Override
	public HttpResponse doGet(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
