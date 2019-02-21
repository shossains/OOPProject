package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    private final int port;
    private ServerSocket ss;
    private boolean alive;

    /**
     * The main entry point for the server, start an instance of this
     * object and the threads and request handling will all be done on their own.
     */
    public ServerThread(int serverPort) {
        port = serverPort;
    }

    @Override
    public void run() {
            System.out.println("Server starting..");
            alive = true;

            try {
                ss = new ServerSocket(port);
                while (alive) {
                    try {
                        System.out.println("Server active on "+port+" , waiting for connections.");
                        Socket clientSocket = ss.accept();
                        Thread serverInstance = new Thread( new ServerInstance(clientSocket));
                        serverInstance.start();
                    } catch (IOException e) {
                        //catches exception, determines whether
                        //its for normal reasons.
                        e.printStackTrace();
                        if (!alive) {
                            System.out.println("Server has been stopped");
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
