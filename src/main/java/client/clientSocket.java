package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class ClientSocket {
    PrintWriter out;
    BufferedReader in;
    Socket socket;

    public ClientSocket(String path, int port){
        try {
            System.out.println("Starting client sockets");
            socket = new Socket(path,port);
            out = new PrintWriter(socket.getOutputStream());
            InputStreamReader inStream = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(inStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handshake() throws IOException {
        System.out.println("Sending handshake");
        out.println("Hello!");
        socket.close();
    }

}
