package com.playspace.controller;

import static com.playspace.config.Constants.GET;
import static com.playspace.config.Constants.HTTP_STATUS_NOT_FOUND;
import static com.playspace.config.Constants.NOT_FOUND_TEXT;
import static com.playspace.config.Constants.POST;

import java.io.IOException;
import java.io.OutputStream;

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
		
		String path = httpExchange.getRequestURI().getPath();
		String requestMethod = httpExchange.getRequestMethod();

		if (path.contains("/login") && requestMethod.equals(GET)) {
			httpResponse = loginController.doGet(httpExchange);

		} else if (path.contains("/score") && requestMethod.equals(POST)) {
			httpResponse = scoreController.doPost(httpExchange);
			
		} else if (path.contains("/highscorelist") && requestMethod.equals(GET)) {
			httpResponse = scoreController.doGet(httpExchange);
			
		} else {
			httpResponse = new HttpResponse();
			httpResponse.setContent(NOT_FOUND_TEXT);
			httpResponse.setStatus(HTTP_STATUS_NOT_FOUND);
		}

		response = httpResponse.getContent();
		httpExchange.sendResponseHeaders(httpResponse.getStatus(), response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
