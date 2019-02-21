package server;

import java.io.*;
import java.net.Socket;

public class ServerInstance implements Runnable {
    private Socket clientSocket;
    private OutputStreamWriter writer;

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
            writer = new OutputStreamWriter(clientSocket.getOutputStream());

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


    private void read(BufferedReader in) throws IOException {
        System.out.println(in.readLine());
    }
}
