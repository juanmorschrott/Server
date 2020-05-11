package com.playspace.controller;

import static com.playspace.config.Constants.HTTP_STATUS_NOT_FOUND;
import static com.playspace.config.Constants.NOT_FOUND_TEXT;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.playspace.HttpRequest;
import com.playspace.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FrontController implements HttpHandler {

	private LoginController loginController;
	private ScoreController scoreController;

	public FrontController() {
		loginController = LoginController.getInstance();
		scoreController = ScoreController.getInstance();
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		HttpResponse httpResponse = null;
		String response = null;
		
		URI uri = httpExchange.getRequestURI();
		String requestMethod = httpExchange.getRequestMethod();

		if (uri.getPath().contains("/login") && requestMethod.equals("GET")) {
			httpResponse = loginController.doGet(httpExchange);
			response = httpResponse.render();

		} else if (uri.getPath().contains("/score") && requestMethod.equals("POST")) {
			httpResponse = scoreController.doPost(httpExchange);
			response = httpResponse.render();
			
		} else if (uri.getPath().contains("/highscorelist") && requestMethod.equals("GET")) {
			httpResponse = scoreController.doGet(httpExchange);
			response = httpResponse.render();
			
		} else {
			httpResponse = new HttpResponse();
			httpResponse.setContent(NOT_FOUND_TEXT);
			httpResponse.setStatus(HTTP_STATUS_NOT_FOUND);
			response = httpResponse.render();
		}

		httpExchange.sendResponseHeaders(httpResponse.getStatus(), response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
