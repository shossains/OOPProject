package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final int port;
    private HttpServer httpServer;

    /**
     * The main entry point for the server, start an instance of this
     * object and the threads and request handling will all be done on their own.
     */
    public Server(int serverPort) {
        port = serverPort;
        try {
            runServer();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void runServer() throws IOException {
        System.out.println("Server starting..");
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", new RequestHandler());
        httpServer.setExecutor(null);
        httpServer.start();
    }
}
