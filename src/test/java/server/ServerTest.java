package server;


import client.SecureClientNetworking;
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
    private static SecureClientNetworking cn;


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
            cn = new SecureClientNetworking(new URL("https://localhost:3000"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends a test request and listens for a response. This is effectively a test of the whole server, from receiving,
     * to processing, up until response.
     */
    @Test
    public void testResponse(){
        Assert.assertEquals("{\"success\":\"who knows\", \"isTest\": false," +
                " \"username\":\"alexshulzycki\"}", cn.sendPostRequest("{'type':'TestRequest'," +
                " 'extraData':'Irrelevant Data'}"));
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
