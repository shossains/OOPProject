package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TemperatureCalculator {

    public static void temp() throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.println("Temperature right now: ");
        int tHigh = input.nextInt();

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

        double co2tHigh = third.getFloat("value");
        co2tHigh = Math.round(co2tHigh * 100.00) / 100.00;
        System.out.println(co2tHigh + " kg CO2");

        TemperatureCalculator testCalc = new TemperatureCalculator();

        testCalc.tempCalc(co2tHigh);

    }

    public void tempCalc(double tempHigh) throws MalformedURLException {

        Scanner input = new Scanner(System.in);

        System.out.println("Temperature changed to: ");
        int tLow = input.nextInt();

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

        double co2tLow = third.getFloat("value");
        co2tLow = Math.round(co2tLow * 100.00) / 100.00;
        System.out.println(co2tLow + " kg CO2");

        //Calculate amount of kg CO2 saved.
        double result = tempHigh - co2tLow;
        result = Math.round(result * 100.00) / 100.00;
        System.out.println("Amount CO2 saved= " + result + " kg CO2");

    }


}
