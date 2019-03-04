package server;

import com.google.gson.JsonObject;

/**
 * Class for parsing requests using Gson.
 */
public class Request {


    private String type;
    private String response;

    public Request() {
    }

    /**
     * Executes the request, returning the response in JSON.
     */
    public String execute() {
        switch (type) {
            case "TestRequest":
                return testExecute();
            default:
                return null;
        }
    }

    /**
     * Test function to test ability to execute a test funtion on the server and return JSON.
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
}
