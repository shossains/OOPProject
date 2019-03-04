package server;


import client.ClientNetworking;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class ServerTest {

    private static Server server;
    private static final String serverpassword = "password";
    private static ClientNetworking cn;


    @BeforeClass
    public static void init() {
        //setup server
        try {
            server = new Server(3000, new FileInputStream("testkey.jks"), serverpassword.toCharArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //setup client
        try {
            cn = new ClientNetworking(new URL("https://localhost:3000"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testResponse(){
        Assert.assertEquals("{\"TestRequest\":\"TestRequest\"}", cn.sendRequest("{'type':'TestRequest'}"));
    }


    @Test
    public void keyStoreExists(){
        assertTrue(new File("testkey.jks").exists());
    }

    @After
    public void after(){
        try {
            //giving thread sleep time so i can manually test it using external tools
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
