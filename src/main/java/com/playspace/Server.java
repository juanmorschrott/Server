package com.playspace;

import com.playspace.context.Session;
import com.playspace.handlers.LoginHandler;
import com.playspace.handlers.ScoreHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static com.playspace.config.Configuration.PORT;

public class Server {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        Session session = Session.getInstance();
        
        server.createContext("/login", new LoginHandler(session));
        server.createContext("/score", new ScoreHandler(session));

        // Creates a thread pool that creates new threads as needed,
        // but will reuse previously constructed threads when they are available
        server.setExecutor(Executors.newCachedThreadPool());

        server.start();
    }

}
