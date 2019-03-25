package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class BusCalculator {

    /**
     * The calculator for getting the amount of kg CO2 produced by a bus.
     * @param number The distance travelled.
     * @return Passing the values of the distance(int) and kg CO2(double) to the method 'carBus()'.
     */

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

    /**
     * The calculator for calculating the difference in kg CO2 produced by a bus and a car.
     * @param dist The distance from the method 'bus()'.
     * @param busCo2 The amount of kg CO2 by the bus, from the method 'bus()'.
     * @return the calculated result of the equation ->
     * (Amount of kg CO2 produced by a car) - (Amount of kg CO2 produced by a bus/ Average amount of people taking the bus) = kg CO2 saved
     * @throws MalformedURLException
     */

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
        result = Math.round(result * 100.00) / 100.00;
        return result.intValue();
    }

}
