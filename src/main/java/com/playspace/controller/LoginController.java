package com.playspace.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.playspace.HttpResponse;
import com.playspace.config.Constants;
import com.playspace.exception.IncorrectUserIdException;
import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

public class LoginController implements Controller {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

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
		logger.info("GET  /login");

		HttpResponse httpResponse = new HttpResponse();

		try {
			Integer userId = Integer.valueOf(httpExchange.getRequestURI().getPath().split("/")[1]);
			httpResponse.setContent(service.generateSession(userId));
			httpResponse.setStatus(Constants.HTTP_STATUS_OK);

		} catch (NumberFormatException e) {
			httpResponse.setContent("Bad request");
			httpResponse.setStatus(Constants.HTTP_STATUS_BAD_REQUEST);
		} catch (IncorrectUserIdException e) {
			httpResponse.setContent(e.getMessage());
			httpResponse.setStatus(Constants.HTTP_STATUS_SERVER_ERROR);
		}

		logger.info("Response: " + httpResponse.getStatus() + " " + httpResponse.getContent());

		return httpResponse;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
