package client;

import jdk.nashorn.internal.parser.JSONParser;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.ServerTest;

import java.net.MalformedURLException;
import java.net.URL;

public class ClientTest {

    static  private  ClientNetworking cn;

    @BeforeClass
    public static void setup(){

    }

    @Test
    public void httpbinPost(){
        //setup test
        try {
            cn = new ClientNetworking(new URL("https://httpbin.org/post"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //make sure it works
        Assert.assertEquals("{\n" +
                "  \"args\": {}, \n" +
                "  \"data\": \"\", \n" +
                "  \"files\": {}, \n" +
                "  \"form\": {\n" +
                "    \"hello world\"",cn.sendRequest("hello world").substring(0,77));
    }

    @AfterClass
    public static void afterClass(){

    }
}
