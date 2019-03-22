package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TrainCalculator {

    public static void train() throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.println("Distance: ");
        int number = input.nextInt();

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

        double co2Train = third.getFloat("value");
        co2Train = Math.round(co2Train * 100.00) / 100.00;
        System.out.println(co2Train);

        TrainCalculator trainCalculator = new TrainCalculator();
        trainCalculator.carTrain(number, co2Train);

    }

    public static void carTrain(int dist, double trainCo2) throws MalformedURLException {

        System.out.println("Distance: ");

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "automobile_trips.json";
        String distance = "?distance=" + dist;
        double fuelCalc = 0.05 * dist;
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

        double co2Car = third.getFloat("value");
        co2Car = Math.round(co2Car * 100.00) / 100.00;
        System.out.println(co2Car);

        //Calculate amount of kg CO2 saved.
        double result = co2Car - (trainCo2 / 200);
        result = Math.round(result * 100.00) / 100.00;
        System.out.println("Amount CO2 saved= " + result + " kg CO2");

    }


}
