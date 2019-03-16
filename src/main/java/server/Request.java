package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import server.queries.RegisterQuery;
import server.queries.TestQuery;
import server.queries.VegMealQuery;

/**
 * Class for parsing requests using Gson.
 * Must give raw string used to instantiate the object via setRaw(),
 * otherwise the queries won't work.
 */
public class Request {

    //basic fields
    private String type;
    private String username;
    private String password;


    /**
     * Raw query, which can then be used to finally manipulate all
     * the data within it, not just the three variables above.
     */
    private String rawQuery;

    /**
     * MUST bet set before running execute, as queries
     * depend on having access to the full query string.
     *
     * @param raw Raw string used to set up this object
     */
    public void setRaw(String raw) {
        rawQuery = raw;
    }

    /**
     * Executes the request, returning the response in JSON.
     * Avaliable requests:
     * register - Register a new user. Provided username and password will be used.
     * TestRequest - For testing purposes
     */
    public String execute() {
        if (rawQuery == null) {
            System.out.println("You forgot to set the rawQuery Einstein");
        }
        switch (type) {
            case "TestRequest":
                return buildGson(rawQuery, TestQuery.class).runQuery();
            case "register":
                return registerUser();

            case "VegMeal":
                return vegMeal();
            default:
                return null;
        }
    }

    /**
     * Registers the user given username and password on the database.
     *
     * @return Either true or false on success, and gives reason for failure;
     */
    private String registerUser() {
        RegisterQuery regQuery = buildGson(rawQuery, RegisterQuery.class);
        return regQuery.runQuery();
    }

    private String vegMeal() {
        VegMealQuery vegMealQuery = buildGson(rawQuery, VegMealQuery.class);
        return vegMealQuery.runQuery();
    }


    public String getType() {
        return type;
    }


    /**
     * Builds specified object with Gson from specified string.
     *
     * @param string String to parse
     * @param tclass Class to parse to
     * @param <T>    Class which is returned (the same as tclass)
     * @return Parsed tClass object
     */
    public <T> T buildGson(String string, Class<T> tclass) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        T request = gson.fromJson(string, tclass);
        return request;
    }
}
