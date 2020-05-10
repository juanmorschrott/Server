package com.playspace;

import static com.playspace.config.Configuration.PORT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.playspace.controller.FrontController;
import com.sun.net.httpserver.HttpServer;

public class Server {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);        
        server.createContext("/", new FrontController());
        server.setExecutor(Executors.newCachedThreadPool());

        server.start();
    }

}
