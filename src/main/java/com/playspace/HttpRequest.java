package com.playspace;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {

	private HttpExchange httpExchange;

	private String requestMethod;
	private URI uri;
	private List<String> params;
	private List<String> queryParams;

	public HttpRequest() {
	}

	public HttpRequest(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;

		this.requestMethod = httpExchange.getRequestMethod();
		this.uri = httpExchange.getRequestURI();
		this.params = splitParams(httpExchange);
		this.queryParams = splitQueryParams(httpExchange.getRequestURI().getQuery());
	}

	private List<String> splitParams(HttpExchange he) {
		return Arrays.asList(he.getRequestURI().getPath().split("/"));
	}

	private List<String> splitQueryParams(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestMethod() {
		return this.requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public List<String> getParameters() {
		return this.params;
	}

	public HttpExchange getHttpExchange() {
		return httpExchange;
	}

	public void setHttpExchange(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	public URI getUri() {
		return this.uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public List<String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List<String> queryParams) {
		this.queryParams = queryParams;
	}

}
