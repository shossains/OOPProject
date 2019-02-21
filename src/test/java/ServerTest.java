import client.ClientSocket;
import server.ServerThread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerTest {
    public static void main(String[] args) {
        System.out.println("Hello World");
        ServerThread serverEntry = new ServerThread(3000);
        Thread sThread = new Thread(serverEntry);
        sThread.start();
        try {
            ClientSocket client = new ClientSocket("127.0.0.1", 3000);
            Thread.sleep(200);
            client.handshake();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
