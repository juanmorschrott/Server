package com.playspace.controller;

import com.playspace.HttpResponse;
import com.playspace.config.Constants;
import com.playspace.exception.IncorrectUserIdException;
import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

public class LoginController implements Controller {

	private static LoginController loginController;

	private HttpExchange httpExchange;

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
	public void init(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	public HttpResponse doGet() {
		String userId;
		HttpResponse httpResponse = null;
		
		try {
			userId = this.httpExchange.getRequestURI().getPath().split("/")[1];

			httpResponse = new HttpResponse();
			httpResponse.setContent(service.getSessionKey(userId));
			httpResponse.setStatus(Constants.HTTP_STATUS_OK);
			
		} catch (IncorrectUserIdException e) {
			httpResponse.setStatus(Constants.HTTP_STATUS_SERVER_ERROR);
		}

		return httpResponse;
	}

}
