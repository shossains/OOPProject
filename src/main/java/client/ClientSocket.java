package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Rudimentary client for testing the server.
 */
public class ClientSocket {
    PrintWriter out;
    BufferedReader in;
    Socket socket;

    /**
     *  Constructor mainly to set up streams and sockets.
     *
     * @param path the path part of the url
     * @param port the port in the url
     */

    public ClientSocket(String path, int port) {
        //setup, variables.
        try {
            System.out.println("Starting client sockets");
            //socketing and streaming
            socket = new Socket(path, port);
            out = new PrintWriter(socket.getOutputStream());
            InputStreamReader inStream = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(inStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rudimentary handshake, just to prove it works.
     *
     * @throws IOException in case there is a messup with r/w
     */
    public void handshake() throws IOException {
        System.out.println("Sending handshake");
        out.println("GET / HTTP/1.1");
        out.println("This is the elaborate handshake\n");
        out.println("");
        out.println("");
        out.flush();


        System.out.println("Server response: "+in.readLine());

        //flush and exit
        out.flush();
        socket.close();
    }

}
