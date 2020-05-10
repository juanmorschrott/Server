package com.playspace.controller;

import static com.playspace.config.Constants.HTTP_STATUS_NOT_FOUND;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.playspace.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FrontController implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		HttpResponse httpResponse = null;
		String response = null;
		Controller controller = null;
		
		URI uri = httpExchange.getRequestURI();
		
		if (uri.getPath().contains("/login")) {
			controller = LoginController.getInstance();
			response = controller.doGet(httpExchange).render();
			
		} else if (uri.getPath().contains("/score") || uri.getPath().contains("/highscorelist")) {
			controller = ScoreController.getInstance();
			response = controller.doGet(httpExchange).render();
			
		} else {
			httpResponse = new HttpResponse();
			httpResponse.setContent("Page not found");
			response = httpResponse.render();			
		}

		httpExchange.sendResponseHeaders(HTTP_STATUS_NOT_FOUND, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();


	}

}
