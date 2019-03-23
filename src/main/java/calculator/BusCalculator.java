package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class BusCalculator {

    public static void bus() throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.println("Distance: ");
        int number = input.nextInt();

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "bus_trips.json";
        String busClass = "&bus_class=city+transit";
        String distance = "?distance=" + number;
        String urlString = host + model + distance + busClass;
        URL url = new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");

        double co2Bus = third.getFloat("value");
        co2Bus = Math.round(co2Bus * 100.00) / 100.00;
        System.out.println(co2Bus);

        BusCalculator busCalculator = new BusCalculator();
        busCalculator.carBus(number, co2Bus);

    }

    public static void carBus(int dist, double busCo2) throws MalformedURLException {

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

        double co2New = third.getFloat("value");
        co2New = Math.round(co2New * 100.00) / 100.00;
        System.out.println(co2New);

        //Calculate amount of kg CO2 saved.
        double result = (co2New) - (busCo2 / 20);
        result = Math.round(result * 100.00) / 100.00;
        System.out.println("Amount CO2 saved= " + result + " kg CO2");

    }

}
