package server;


import com.google.gson.Gson;
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

    private String testExecute(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TestRequest","TestRequest");
        return jsonObject.toString();

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
