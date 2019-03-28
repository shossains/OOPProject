package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class CarCalculator {

    /**
     * The calculator for getting the amount of kg CO2 produced by a car.
     * @param distance The distance
     * @return Amount of kg CO2 produced by a car
     */

    public static Double car(int distance) {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "automobile_trips.json";
        String dist = "?distance=" + distance;
        Double fuelCalc = 0.05 * distance;
        String fuel = "&fuel_use=" + fuelCalc;
        String urlString = host + model + dist + fuel;
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");

        //Calculate amount of kg CO2 saved.
        Double co2Car = third.getDouble("value");
        co2Car = Math.round(co2Car * 100.00) / 100.00;
        return co2Car;
    }

}
