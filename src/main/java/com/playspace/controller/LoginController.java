package com.playspace.controller;

import com.playspace.HttpResponse;
import com.playspace.config.Constants;
import com.playspace.exception.BadParametersException;
import com.playspace.exception.IncorrectUserIdException;
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
		String userId;
		HttpResponse httpResponse = new HttpResponse();

		try {
			String[] params = httpExchange.getRequestURI().getPath().split("/");
			if (params.length > 0) {
				userId = params[1];
				httpResponse.setContent(service.generateSession(userId));
				httpResponse.setStatus(Constants.HTTP_STATUS_OK);
			} else {
				throw new BadParametersException("Bad request");
			}

		} catch (IncorrectUserIdException e) {
			httpResponse.setContent(e.getMessage());
			httpResponse.setStatus(Constants.HTTP_STATUS_SERVER_ERROR);
		} catch (BadParametersException e) {
			httpResponse.setContent(e.getMessage());
			httpResponse.setStatus(Constants.HTTP_STATUS_BAD_REQUEST);
		}

		return httpResponse;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
