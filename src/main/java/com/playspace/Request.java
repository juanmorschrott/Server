package com.playspace;

import java.util.Arrays;
import java.util.List;

import javax.xml.ws.spi.http.*;

public class Request {

	private HttpExchange httpExchange;

	public Request(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}
		
	public String getRequestMethod() {
		return this.httpExchange.getRequestMethod();
	}

	public String getUri() {
		return this.httpExchange.getRequestURI();
	}

	public List<String> getParameters() {
		return Arrays.asList(this.httpExchange.getRequestURI()
				.toString()
				.split("\\?")[1]
				.split("=")[1]);
	}

}
