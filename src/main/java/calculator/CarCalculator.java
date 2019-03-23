package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

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
        double fuelCalc = 0.05 * number;
        String fuel = "&fuel_use=" + fuelCalc;
        String urlString = host + model + distance + fuel;
        URL url = new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");

        //Calculate amount of kg CO2 saved.
        double co2Car = third.getFloat("value");
        co2Car = Math.round(co2Car * 100.00) / 100.00;
        System.out.println(co2Car);

    }

}
