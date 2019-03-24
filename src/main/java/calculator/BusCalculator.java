package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class BusCalculator {

    public static int bus(int number) {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "bus_trips.json";
        String busClass = "&bus_class=city+transit";
        String distance = "?distance=" + number;
        String urlString = host + model + distance + busClass;
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

        Double co2Bus = third.getDouble("value");
        co2Bus = Math.round(co2Bus * 100.00) / 100.00;

        BusCalculator busCalculator = new BusCalculator();
        try {
            return busCalculator.carBus(number, co2Bus);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int carBus(int dist, double busCo2) throws MalformedURLException {
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

        Double co2New = third.getDouble("value");
        co2New = Math.round(co2New * 100.00) / 100.00;

        //Calculate amount of kg CO2 saved.
        Double result = co2New - (busCo2 / 20);
        result = Math.round(result * 1000.00) / 100.00;
        return result.intValue();
    }

}
