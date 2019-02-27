package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Instance of the server, running on its own thread, to handle each request separately.
 */
public class ServerInstance implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;

    public ServerInstance(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void run() {
        System.out.println("Server instance started");

        try {
            // set up the streams
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(clientSocket.getOutputStream());

            //call the function to handle the data
            read(in);

            //close everything up
            System.out.println("Server instance closing...");
            in.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Where the reading will be handled, just a stub for testing atm.
     *
     * @param in The buffered reader to read from
     * @throws IOException if there is a messup with the r/w
     */

    private void read(BufferedReader in) throws IOException {
        System.out.println(in.readLine());
        System.out.println(in.readLine());

        //respond
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-length: 12");
        writer.println("");
        writer.println("Hello World!");
        writer.flush();
    }

    public boolean isTrue() {
        return true;
    }
}
