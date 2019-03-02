package server;

import client.ClientSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    private static Server server;


    @BeforeClass
    public static void init() {
        System.out.println("Hello World");
        server = new Server(3000);
        /*try {
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
*/
    }

    @Test
    public void properRequest(){

    }


    @Test
    public void testJazz(){
        assertEquals("1", "1");
    }

    @After
    public void after(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
