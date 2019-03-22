package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class CarCalculator {

    public static void car() throws Exception {


        Scanner input = new Scanner(System.in);

        System.out.println("Distance: ");
        int number = input.nextInt();

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "automobile_trips.json";
        String distance = "?distance=" + number;
        double fuelCalc = 0.05*number;
        String fuel = "&fuel_use=" + fuelCalc;
        String urlString = host + model + distance + fuel;
        URL url= new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject test1 = myResponse.getJSONObject("decisions");
        System.out.println(test1);

        JSONObject test2 = test1.getJSONObject("carbon");
        System.out.println(test2);
        System.out.println("Description: " + test2.getString("description"));

        JSONObject test3 = test2.getJSONObject("object");
        System.out.println("Value: " + test3.getFloat("value"));

        double co2 = test3.getFloat("value");
        co2 = Math.round(co2 *100.00)/100.00;
        System.out.println(co2);


    }

    public static void carBus(int x, double y) throws MalformedURLException {

        System.out.println("Distance: ");

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "automobile_trips.json";
        String distance = "?distance=" + x;
        double fuelCalc = 0.05*x;
        String fuel = "&fuel_use=" + fuelCalc;
        String urlString = host + model + distance + fuel;
        URL url= new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject test1 = myResponse.getJSONObject("decisions");


        JSONObject test2 = test1.getJSONObject("carbon");


        JSONObject test3 = test2.getJSONObject("object");

        double co4 = test3.getFloat("value");
        co4 = Math.round(co4 *100.00)/100.00;
        System.out.println(co4);

        double result = co4 - y;
        result = Math.round(result *100.00)/100.00;
        System.out.println("Amount CO2 saved= " + result + " kg CO2");

    }


}
