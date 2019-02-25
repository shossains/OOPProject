package server;

import client.ClientSocket;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
//    @Before
//    public static void main() {
//        System.out.println("Hello World");
//        ServerThread serverEntry = new ServerThread(3000);
//        Thread sThread = new Thread(serverEntry);
//        sThread.start();
//        try {
//            ClientSocket client = new ClientSocket("127.0.0.1", 3000);
//            Thread.sleep(200);
//            client.handshake();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
    @Test
    public void test() {
        assertTrue(new ServerInstance(new Socket()).isTrue());
    }
    @Test
    public void testJazz(){
        assertEquals("1", "1");
    }

}
