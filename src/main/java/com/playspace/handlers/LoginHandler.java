package com.playspace.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.playspace.config.Configuration;
import com.playspace.context.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LoginHandler implements HttpHandler {
	
	private Session session;
	
	public LoginHandler(Session session) {
		this.session = session;
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		String userId = "";
		
		String response = session.getSessionKey(null);

		httpExchange.sendResponseHeaders(Configuration.HTTP_STATUS_OK, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
