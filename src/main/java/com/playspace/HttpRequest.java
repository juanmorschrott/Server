package com.playspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {

	private HttpExchange httpExchange;

	private String requestMethod;
	private URI uri;
	private String[] params;
	private Map<String, String> queryParams;
	private String body;

	public HttpRequest() {
	}

	public HttpRequest(HttpExchange httpExchange) throws IOException {
		this.httpExchange = httpExchange;

		this.requestMethod = httpExchange.getRequestMethod();
		this.uri = httpExchange.getRequestURI();
		this.params = uri.getPath().split("/");
		this.queryParams = splitQueryParams(uri);
		this.body = parseBody(httpExchange.getRequestBody());
	}

	private Map<String, String> splitQueryParams(URI uri) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		String query = uri.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}
	
	private String parseBody(InputStream requestBody) throws IOException {
		InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
	}

	public HttpExchange getHttpExchange() {
		return httpExchange;
	}

	public void setHttpExchange(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "HttpRequest [requestMethod=" + requestMethod + ", uri=" + uri + ", params=" + Arrays.toString(params)
				+ ", queryParams=" + queryParams + "]";
	}

}
