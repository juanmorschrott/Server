package com.playspace.handlers;

import com.playspace.context.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ScoreHandler implements HttpHandler {
	
	private Session session;
	
	public ScoreHandler(Session session) {
		this.session = session;
	}

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

}
