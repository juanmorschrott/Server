package com.playspace.controller;

import com.playspace.HttpResponse;
import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

public class LoginController implements Controller {

	private static LoginController loginController;
	
	private LoginService service;
	
	private LoginController() {
		service = LoginService.getInstance();
	}
	
	public static synchronized LoginController getInstance() {
		if (loginController == null) {
			loginController = new LoginController();
		}
		return loginController;
	}
	
	@Override
	public HttpResponse doGet(HttpExchange httpExchange) {
		String userId = httpExchange.getRequestURI().getPath().split("/")[1];
		
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setContent(service.getSessionKey(userId));
		
		return httpResponse;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
