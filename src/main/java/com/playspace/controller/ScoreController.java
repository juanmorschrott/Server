package com.playspace.controller;

import com.playspace.HttpResponse;
import com.sun.net.httpserver.HttpExchange;

public class ScoreController implements Controller {

private static ScoreController scoreController;
	
	private ScoreController() {
		
	}
	
	public static ScoreController getInstance() {
		if (scoreController == null) {
			scoreController = new ScoreController();
		}
		return scoreController;
	}
	
	@Override
	public HttpResponse doGet(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
