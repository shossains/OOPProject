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
    private static SecureClientNetworking httpsCon;
    private static SecureClientNetworking httpCon;


    /**
     * Sets up the server as well as http and https urls
     */

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
            httpsCon = new SecureClientNetworking(new URL("https://localhost:3000"));
            httpCon = new SecureClientNetworking(new URL("http://localhost:3000"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a test request and listens for a response. This is effectively a test of the whole server, from receiving,
     * to processing, up until response.
     */

    @Test
    public void fullHttpsRequestResponse() {
        Assert.assertEquals("{\"success\":\"who knows\", \"isTest\": false," +
                " \"username\":\"alexshulzycki\"}", httpsCon.sendPostRequest("{'type':'TestRequest'," +
                " 'extraData':'Irrelevant Data'}"));
    }

    /**
     * This test is supposed to successfully fail, as the server shouldn't accept any unencrypted requests.
     */

    @Test
    public void insecureRequestTest() {
        Assert.assertEquals(null, httpCon.sendPostRequest("{'type':'TestRequest'," +
                " 'extraData':'Irrelevant Data'}"));
    }


    /**
     * Makes sure the testkey keystore actually exists, might be handy for debugging later on.
     */

    @Test
    public void keyStoreExists() {
        assertTrue(new File("testkey.jks").exists());
    }

    @Test
    public void keyStoreIsReadable() {
        assertTrue(new File("testkey.jks").canRead());
    }

    @After
    public void after() {
        try {
//giving thread some well-deserved sleep time so you can manually test the server using external tools
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}