package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class RequestHandler implements HttpHandler {

    private Gson gson;
    private GsonBuilder builder;
    private Request request;

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Handling request");

        //grab inputstream, do reading stuff etc
        InputStream is = exchange.getRequestBody();

        //grabbing string format of result, using a stupid scanner trick.
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String requestString = scanner.hasNext() ? scanner.next() : "";

        //building request object with Gson
        Request request = buildGsonRequest(requestString);

        //response, for now just replies with what it got
        String response = request.execute();
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * This function uses Gson to parse the JSON into a Java Request class.
     *
     * @param string Raw request string
     * @return Parsed Request object
     */
    private Request buildGsonRequest(String string) {
        //Set up Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        //Creates request object via Gson, then gives it the raw JSON string (crucial!)
        request = gson.fromJson(string, Request.class);
        request.setRaw(string);
        return request;
    }
}
