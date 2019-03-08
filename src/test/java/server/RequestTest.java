package server;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {
    static private Request request;

    @BeforeClass
    public static void before(){
        request = new Request();
        request.setType("TestRequest");
    }

    @Test
    public void execute() {
        Assert.assertEquals("{\"TestRequest\":\"TestRequest\"}", request.execute());
    }

    @Test
    public void getType() {
        Assert.assertEquals("TestRequest", request.getType());
    }

    @Test
    public void setType() {
        request.setType("TestType");
        Assert.assertEquals("TestType", request.getType());
    }
}