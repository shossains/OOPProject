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
        URL url= new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject test1 = myResponse.getJSONObject("decisions");

        JSONObject test2 = test1.getJSONObject("carbon");

        JSONObject test3 = test2.getJSONObject("object");

        double co2 = test3.getFloat("value");
        co2 = Math.round(co2 *100.00)/100.00;
        System.out.println(co2 + " kg CO2");

        TemperatureCalculator testCalc = new TemperatureCalculator();

        testCalc.tempCalc(co2);

    }

    public void tempCalc(double x) throws MalformedURLException {

        Scanner input = new Scanner(System.in);

        System.out.println("Temperature changed to: ");
        int tLow = input.nextInt();

        //Setup https client
        String host = "http://impact.brighterplanet.com/";
        String model = "residences.json";
        int temperature = 1006 * 256 * tLow;
        String monthlyUse = "?monthly_natural_gas_volume_estimate=" + temperature;
        String urlString = host + model + monthlyUse;
        URL url= new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject test1 = myResponse.getJSONObject("decisions");

        JSONObject test2 = test1.getJSONObject("carbon");

        JSONObject test3 = test2.getJSONObject("object");

        double co3 = test3.getFloat("value");
        co3 = Math.round(co3 *100.00)/100.00;
        System.out.println(co3 + " kg CO2");

        double result = x - co3;
        result = Math.round(result *100.00)/100.00;
        System.out.println("Amount CO2 saved= " + result + " kg CO2");

    }


}
