package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class TemperatureCalculator {

    public static int temp(int tHigh, int tLow) throws Exception {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "residences.json";
        int temperature = 1006 * 256 * tHigh;
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
        return temperatureCalculator.tempCalc(tLow, co2tHigh);

    }

    public static int tempCalc(int tLow, double tempHigh) throws MalformedURLException {

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "residences.json";
        int temperature = 1006 * 256 * tLow;
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
        return result.intValue();
    }


}
