package com.playspace.controller;

import com.playspace.HttpResponse;
import com.sun.net.httpserver.HttpExchange;

public interface Controller {
	
	void init(HttpExchange httpExchange);
	
}
