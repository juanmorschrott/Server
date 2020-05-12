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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.playspace.HttpResponse;
import com.playspace.config.Constants;
import com.playspace.repository.ScoreRepository;
import com.playspace.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

public class ScoreController implements Controller {
	
	private static final Logger logger = LogManager.getLogger(ScoreController.class);

	private static ScoreController scoreController;
	private LoginService loginService;
	private ScoreRepository scoreRepository;

	private ScoreController() {
		loginService = LoginService.getInstance();
		scoreRepository = ScoreRepository.getInstance();
	}

	public static synchronized ScoreController getInstance() {
		if (scoreController == null) {
			scoreController = new ScoreController();
		}
		return scoreController;
	}

	@Override
	public HttpResponse doGet(HttpExchange httpExchange) {
		logger.info("GET  /highscorelist");
		
		HttpResponse httpResponse = new HttpResponse();

		try {
			Integer levelId = Integer.valueOf(httpExchange.getRequestURI().getPath().split("/")[1]);
			String scoresCSV = scoreRepository.findHighestScoresByLevelId(levelId);
			if (!scoresCSV.isEmpty()) {
				httpResponse.setContent(scoresCSV);
				httpResponse.setStatus(Constants.HTTP_STATUS_OK);
			} else {
				httpResponse.setStatus(Constants.HTTP_STATUS_NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			httpResponse.setContent("Bad request");
			httpResponse.setStatus(Constants.HTTP_STATUS_BAD_REQUEST);
		} catch (Exception e) {			
			httpResponse.setContent(e.toString());
			httpResponse.setStatus(Constants.HTTP_STATUS_SERVER_ERROR);
		}
		return httpResponse;
	}

	@Override
	public HttpResponse doPost(HttpExchange httpExchange) {
		logger.info("POST /score");
		
		HttpResponse httpResponse = new HttpResponse();

		try {
			Integer levelId = Integer.valueOf(httpExchange.getRequestURI().getPath().split("/")[1]);
			String sessionKey = splitQueryParams(httpExchange.getRequestURI()).get("sessionKey");
			String payload = parseBody(httpExchange.getRequestBody());

			if (loginService.isLoggedUserSessionKey(sessionKey)) {
				logger.info("Level id: " + levelId + ", Session key: " + sessionKey + ", Points: " + payload);
				
				httpResponse.setStatus(Constants.HTTP_STATUS_OK);
			} else {
				logger.warn("Unauthorized access attempt");

				httpResponse.setStatus(Constants.HTTP_STATUS_UNAUTHORIZED);
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
