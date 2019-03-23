package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CarCalculatorTest {

    static private SecureClientNetworking cn;

    @Test
    public void car() {

    }

    @Test
    public void badPostUrl() {
        //setup test
        try {
            cn = new SecureClientNetworking(new URL("https://localhost"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //make sure it works
        Assert.assertEquals(null, cn.sendPostRequest("hello world"));
    }

    /**
     * Test https POST with httpbin.
     */

    @Test
    public void httpbinPost() {
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
                "    \"hello world\"", cn.sendPostRequest("hello world").substring(0, 77));
    }

    /**
     * Tests unsecured POST with http using httpbin
     */

    @Test
    public void httpbinPostUnsecured() {
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
                "    \"hello world\"", cn.sendPostRequest("hello world").substring(0, 77));
    }

    @Test
    public void verifySimilar() {
        final String test1 = "SameJsonObject";
        JSONObject obj1 = new JSONObject()
                .put("key1", "abc")
                .put("key2", 2)
                .put("key3", test1);

        JSONObject obj2 = new JSONObject()
                .put("key1", "acb")
                .put("key2", 2)
                .put("key3", test1);

        JSONObject obj3 = new JSONObject()
                .put("key1", "abc")
                .put("key2", 2)
                .put("key3", new String(test1));

        assertFalse("Should eval to false", obj1.similar(obj2));

        assertTrue("Should eval to true", obj1.similar(obj3));

    }

//    /**
//     * A JSONObject can be created with no content
//     */
//    @Test
//    public void emptyJsonObject() {
//        JSONObject jsonObject = new JSONObject();
//        assertTrue("jsonObject should be empty", jsonObject.isEmpty());
//    }


}
