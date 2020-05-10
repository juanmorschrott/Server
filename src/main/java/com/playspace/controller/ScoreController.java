package com.playspace.controller;

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
	public void init(HttpExchange httpExchange) {

	}

}
