package client;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientTest {

    static  private SecureClientNetworking cn;

    @BeforeClass
    public static void setup(){

    }

    @Test
    public void badGetUrl() {
        try {
            cn = new SecureClientNetworking(new URL("https://localhost"));
        } catch (MalformedURLException e) {

        }
        Assert.assertEquals(null, cn.sendGetRequest("whatever"));
    }

    @Test
    public void badPostUrl(){
        //setup test
        try {
            cn = new SecureClientNetworking(new URL("https://localhost"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //make sure it works
        Assert.assertEquals(null ,cn.sendPostRequest("hello world"));
    }

    /**
     * Test https POST with httpbin
     */
    @Test
    public void httpbinPost(){
        //setup test
        try {
            cn = new SecureClientNetworking(new URL("https://httpbin.org/post"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //make sure it works
        Assert.assertEquals("{\n" +
                "  \"args\": {}, \n" +
                "  \"data\": \"\", \n" +
                "  \"files\": {}, \n" +
                "  \"form\": {\n" +
                "    \"hello world\"",cn.sendPostRequest("hello world").substring(0,77));
    }

    /**
     * Tests unsecured POST with http using httpbin
     */
    @Test
    public void httpbinPostUnsecured(){
        try {
            cn = new SecureClientNetworking(new URL("http://httpbin.org/post"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //make sure it works
        Assert.assertEquals("{\n" +
                "  \"args\": {}, \n" +
                "  \"data\": \"\", \n" +
                "  \"files\": {}, \n" +
                "  \"form\": {\n" +
                "    \"hello world\"",cn.sendPostRequest("hello world").substring(0,77));
    }


    @Test
    public void getTest(){
        try {
            cn = new SecureClientNetworking(new URL("https://httpbin.org/get"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("{\n" +
                "  \"args\": {\n" +
                "    \"Hello\": \"World\"", cn.sendGetRequest("Hello=World").substring(0,34));
    }

    @Test
    public void getNonSecureTest(){
        try {
            cn = new SecureClientNetworking(new URL("http://httpbin.org/get"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("{\n" +
                "  \"args\": {\n" +
                "    \"Hello\": \"World\"", cn.sendGetRequest("Hello=World").substring(0,34));
    }

    @AfterClass
    public static void afterClass(){

    }
}
