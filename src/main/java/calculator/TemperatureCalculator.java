package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class TemperatureCalculator {

    /**
     * Calculating kg CO2 produced by your house with a certain amount of temperature.
     * @param thigh The temperature on which the thermometer is set.
     * @param tlow The temperature on which the thermometer could be set to save CO2.
     * @return 'tlow'(int) and kg CO2(double), passed to the method 'tempCalc()'.
     * @throws Exception Throws Url exception.
     */

    public static Double temp(int thigh, int tlow) throws Exception {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "residences.json";
        int temperature = 1006 * 256 * thigh;
        String monthlyUse = "?monthly_natural_gas_volume_estimate=" + temperature;
        String urlString = host + model + monthlyUse;
        URL url = new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");

        Double co2tHigh = third.getDouble("value");
        co2tHigh = Math.round(co2tHigh * 100.00) / 100.00;


        TemperatureCalculator temperatureCalculator = new TemperatureCalculator();
        return temperatureCalculator.tempCalc(tlow, co2tHigh);
    }

    /**
     * Calculating the difference in kg CO2 by changing the temperature of your house.
     * @param tlow The temperature on which the thermometer could be set to save CO2.
     * @param tempHigh The amount of kg CO2 produced by a temperature.
     * @return The calculated result of the equation.
     * @throws MalformedURLException Url exception.
     */
    public static Double tempCalc(int tlow, Double tempHigh) throws MalformedURLException {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "residences.json";
        int temperature = 1006 * 256 * tlow;
        String monthlyUse = "?monthly_natural_gas_volume_estimate=" + temperature;
        String urlString = host + model + monthlyUse;
        URL url = new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject first = myResponse.getJSONObject("decisions");

        JSONObject second = first.getJSONObject("carbon");

        JSONObject third = second.getJSONObject("object");
        Double co2tLow = third.getDouble("value");
        co2tLow = Math.round(co2tLow * 100.00) / 100.00;

        //Calculate amount of kg CO2 saved.
        Double result = tempHigh - co2tLow;
        result = Math.round(result * 100.00) / 100.00;
        return result;
    }


}
