package com.playspace.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.playspace.HttpResponse;
import com.playspace.config.Constants;
import com.playspace.exception.BadParametersException;
import com.playspace.repository.ScoreRepository;
import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

public class ScoreController implements Controller {

	private static ScoreController scoreController;

	private LoginService loginService;

	private ScoreRepository scoreRepository;

	private ScoreController() {
		loginService = LoginService.getInstance();
		scoreRepository = ScoreRepository.getInstance();
	}

	public static ScoreController getInstance() {
		if (scoreController == null) {
			scoreController = new ScoreController();
		}
		return scoreController;
	}

	@Override
	public HttpResponse doGet(HttpExchange httpExchange) {
		HttpResponse httpResponse = new HttpResponse();

		try {
			Integer levelId = Integer.valueOf(httpExchange.getRequestURI().getPath().split("/")[1]);

			if (levelId != null) {
				String scoresCSV = scoreRepository.findHighestScoresByLevelId(levelId);
				if (!scoresCSV.isEmpty()) {
					httpResponse.setContent(scoresCSV);
				}
			} else {
				httpResponse.setContent("Bad request");
				httpResponse.setStatus(Constants.HTTP_STATUS_BAD_REQUEST);
			}
		} catch (Exception e) {
			httpResponse.setContent(e.getMessage());
			httpResponse.setStatus(Constants.HTTP_STATUS_SERVER_ERROR);
		}
		return httpResponse;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		HttpResponse httpResponse = new HttpResponse();

		try {
			Integer levelId = Integer.valueOf(httpExchange.getRequestURI().getPath().split("/")[1]);
			String sessionKey = splitQueryParams(httpExchange.getRequestURI()).get("sessionKey");
			String payload = parseBody(httpExchange.getRequestBody());

			if (levelId != null && !sessionKey.isEmpty() && !payload.isEmpty()
					&& loginService.isLoggedUserSessionKey(sessionKey)) {
				System.out.println("Level id: " + levelId + ", Session key: " + sessionKey + ", Points: " + payload);
			}
		} catch (IOException e) {
			httpResponse.setStatus(Constants.HTTP_STATUS_BAD_REQUEST);
		}
		return httpResponse;
	}

	/**
	 * Extracts query params and parses into a Map.
	 * 
	 * @param uri
	 * @return
	 * @throws UnsupportedEncodingException
	 */
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

	/**
	 * Extracts body content and parses to String.
	 * 
	 * @param requestBody
	 * @return
	 * @throws IOException
	 */
	private String parseBody(InputStream requestBody) throws IOException {
		InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}

}
