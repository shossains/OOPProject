package server.queries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Request;
import server.queries.ServerQuery;
import server.queries.TestQuery;


public class RequestTest {
    static private GsonBuilder gsonBuilder;
    static private Gson gson;
    static private final String stringquery = "{'type':'TestRequest', 'isTest': true, 'username':'alexshulzycki'}";
    static private Request gsonTestRequest;

    @BeforeClass
    public static void before() {
        //test request for testing of the actual Request class
        gsonTestRequest = new Request();
        gsonTestRequest.setRaw(stringquery);

        //setup gson for testing json classes
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    /**
     * Tests the ability of Gson in the Request class to parse queries.
     * It tests parsing of the ServerQuery superclass, as well as the TestQuery.
     */


    @Test
    public void gsonTestRequest() {
        //test the superclass ServerQuery
        ServerQuery serverQuery = gsonTestRequest.buildGson(stringquery, ServerQuery.class);
        Assert.assertEquals("ServerQuery", "alexshulzycki", serverQuery.getUsername());

        //test functions
        TestQuery testQuery = gsonTestRequest.buildGson(stringquery, TestQuery.class);
        Assert.assertEquals("Test TestQuery query", "{\"success\":\"who knows\", \"isTest\": true," +
                " \"username\":\"alexshulzycki\"}", testQuery.runQuery());
    }

    /**
     * Tests if GSON actually works on its own, which is crucial
     */
    @Test
    public void gsonTest(){
       Assert.assertEquals("alexshulzycki" ,gson.fromJson(stringquery, ServerQuery.class).getUsername());
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

    @Test
    public void noRawQuery(){
        Request noRawRequest = gson.fromJson(stringquery, Request.class);
        Assert.assertEquals("You forgot to set the rawQuery Einstein", noRawRequest.execute());
    }

    @Test
    public void wrongType(){
        String queryString = "{'type': 'yeet', 'isTest': true, 'username':'alexshulzycki'}";
        Request badTypeRequest = gson.fromJson(queryString, Request.class);
        badTypeRequest.setRaw(queryString);
        Assert.assertEquals("{'error' : true, 'reason' : 'Unknown type'}", badTypeRequest.execute());
    }

    @Test
    public void noType(){
        String queryString = "{ 'isTest': true, 'username':'alexshulzycki'}";
        Request noTypeRequest = gson.fromJson(queryString, Request.class);
        noTypeRequest.setRaw(queryString);
        Assert.assertEquals("{'error' : true, 'reason' : 'No type given'}", noTypeRequest.execute());
    }

}
