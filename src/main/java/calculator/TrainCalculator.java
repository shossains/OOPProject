package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class TrainCalculator {

    /**
     * The calculator for getting the amount of kg CO2 produced by a train.
     * @param number The distance travelled.
     * @return Passing the values of the distance(int) and kg CO2(double) to the method 'carTrain()'
     * @throws Exception Url exception.
     */

    public static Double train(int number) throws MalformedURLException {

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

    /**
     * The calculator for calculating the difference in kg CO2 produced by a train and a car.
     * @param dist The distance travelled from the method 'train()'.
     * @param trainCo2 The amount of kg CO2 produced by the train, from the method 'train()'.
     * @return the calculated result of the equation.
     * @throws MalformedURLException Url Exception.
     */
    public static Double carTrain(int dist, double trainCo2) throws MalformedURLException {

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
        result = Math.round(result * 100.00) / 100.00;
        return result;

    }

}
