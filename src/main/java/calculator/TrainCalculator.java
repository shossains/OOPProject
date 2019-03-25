package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class TrainCalculator {

    public static int train(int number) throws Exception {
        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "rail_trips.json";
        String trainClass = "&rail_class=intercity";
        String distance = "?distance=" + number;
        String urlString = host + model + distance + trainClass;
        URL url = new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");

        Double co2Train = third.getDouble("value");
        co2Train = Math.round(co2Train * 100.00) / 100.00;

        TrainCalculator trainCalculator = new TrainCalculator();
        return trainCalculator.carTrain(number, co2Train);
    }

    public static int carTrain(int dist, double trainCo2) throws MalformedURLException {
        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "automobile_trips.json";
        String distance = "?distance=" + dist;
        Double fuelCalc = 0.05 * dist;
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

        Double co2Car = third.getDouble("value");
        co2Car = Math.round(co2Car * 100.00) / 100.00;

        //Calculate amount of kg CO2 saved.
        Double result = co2Car - (trainCo2 / 200);
        result = Math.round(result * 1000.00) / 100.00;
        return result.intValue();
    }
}