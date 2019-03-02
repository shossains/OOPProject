package server;


import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    private static Server server;
    private static final String serverpassword = "password";


    @BeforeClass
    public static void init() {
        server = new Server(3000, serverpassword.toCharArray());
        
    }

    @Test
    public void properRequest(){

    }


    @Test
    public void keyStoreExists(){
        assertTrue(new File("testkey.jks").exists());
    }

    @After
    public void after(){
        try {
            //giving thread sleep time so i can manually test it using external tools
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
