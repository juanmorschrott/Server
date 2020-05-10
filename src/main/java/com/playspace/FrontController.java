package com.playspace;

import static com.playspace.config.Constants.HTTP_STATUS_OK;

import java.io.IOException;
import java.io.OutputStream;

import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FrontController implements HttpHandler {

	private LoginService loginService;

	public FrontController() {
		this.loginService = LoginService.getInstance();
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		String response = loginService.getSessionKey("");

		httpExchange.sendResponseHeaders(HTTP_STATUS_OK, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
