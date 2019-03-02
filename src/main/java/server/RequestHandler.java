package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
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
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Handling request");
        //grab inputstream, do reading stuff etc
        InputStream is = exchange.getRequestBody();

        //grabbing string format of result, using a stupid scanner trick.
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String requestString = s.hasNext() ? s.next() : "";
        //String requestString = new BufferedReader(new InputStreamReader(is)).readLine();

        System.out.println("Received string: "+requestString);

        //building request object with Gson
/*

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        request = gson.fromJson(requestString, Request.class);
        System.out.println(request.getRequest());
*/

        //response
        String response = "Yeet";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public Request getRequest() {
        return request;
    }
}
