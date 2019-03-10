package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.queries.RegisterQuery;
import server.queries.ServerQuery;
import server.queries.TestQuery;

import static org.junit.Assert.*;

public class RequestTest {
    static private GsonBuilder gsonBuilder;
    static private Gson gson;
    static private String stringquery;
    static private Request gsonTestRequest;

    @BeforeClass
    public static void before() {

//test request for testing of the actual Request class
        gsonTestRequest = new Request();
        gsonTestRequest.setRaw(stringquery);

//setup gson for testing json classes
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        stringquery = "{'type':'TestRequest', 'isTest': true, 'username':'alexshulzycki'}";
    }

    /**
     * Tests the ability of Gson to parse queries.
     * It tests parsing of the ServerQuery superclass, as well as the TestQuery.
     */
    @Test
    public void gsonTestQuery() {
//test the superclass ServerQuery
        ServerQuery serverQuery = gsonTestRequest.buildGson(stringquery, ServerQuery.class);
        Assert.assertEquals("ServerQuery", "alexshulzycki", serverQuery.getUsername());

//test functions
        TestQuery testQuery = gsonTestRequest.buildGson(stringquery, TestQuery.class);
        Assert.assertEquals("Test TestQuery query", "{\"success\":\"who knows\", \"isTest\": true," +
                " \"username\":\"alexshulzycki\"}", testQuery.runQuery());
    }

    @Test
    public void setType() {
        gsonTestRequest.setType("TestType");
        Assert.assertEquals("TestType", gsonTestRequest.getType());
    }

    /**
     * Tests the full handling and running of a test request with the Request object.
     */
    @Test
    public void gsonTestQueryFull() {
        Request testRequest = gson.fromJson(stringquery, Request.class);
        testRequest.setRaw(stringquery);
        Assert.assertEquals("{\"success\":\"who knows\", \"isTest\": true," +
                " \"username\":\"alexshulzycki\"}", testRequest.execute());
    }
}