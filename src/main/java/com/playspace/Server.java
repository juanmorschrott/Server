package com.playspace;

import static com.playspace.config.Configuration.PORT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.playspace.config.Configuration;
import com.playspace.controller.FrontController;
import com.sun.net.httpserver.HttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

	private static final Logger logger = LogManager.getLogger(Server.class);

	public static void main(String[] args) throws IOException {
		logger.info("Server running on port: " + Configuration.PORT);

		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		server.createContext("/", new FrontController());
		server.setExecutor(Executors.newCachedThreadPool());

		server.start();
	}

}
