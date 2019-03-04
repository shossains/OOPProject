package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import server.queries.Register;

import java.lang.reflect.Type;

/**
 * Class for parsing requests using Gson.
 */
public class Request {

    //basic fields
    private String type;
    private String username;
    private String password;


    /**
     * Request query contains an object with a specific query for the server,
     * to allow for flexibility.
     */
    private String requestQuery;

    public Request() {
    }

    /**
     * Executes the request, returning the response in JSON.
     * Avaliable requests:
     *
     *  register - Register a new user. Provided username and password will be used.
     *  TestRequest - For testing purposes
     */
    public String execute() {
        switch (type) {
            case "TestRequest":
                return testExecute();
            case "register":
                return registerUser();
            default:
                return null;
        }
    }

    /**
     * Registers the user given username and password on the database.
     * @return Either true or false on success, and gives reason for failure;
     */
    private String registerUser() {


        return null;
    }

    /**
     * Test function to test ability to execute a test function on the server and return JSON.
     *
     * @return Test string from test classes
     */
    private String testExecute() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TestRequest", "TestRequest");
        return jsonObject.toString();

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public <T> T buildGson(String string, T queryClass) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        T request = gson.fromJson(string, (Type) queryClass);
        return request;
    }
}
