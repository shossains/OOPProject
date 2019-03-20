package calculator;

import client.SecureClientNetworking;
import org.json.JSONObject;

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
        URL url= new URL(urlString);
        SecureClientNetworking scn = new SecureClientNetworking(url);

        //send post request
        String response = scn.sendPostRequest("");

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response);

        JSONObject test1 = myResponse.getJSONObject("decisions");
        System.out.println(test1);

        JSONObject test2 = test1.getJSONObject("carbon");
        System.out.println(test2);
        System.out.println("Description: " + test2.getString("description"));

        JSONObject test3 = test2.getJSONObject("object");
        System.out.println("Value: " + test3.getFloat("value"));

        double co2 = test3.getFloat("value");
        co2 = Math.round(co2 *10.0)/10.0;
        System.out.println(co2);

    }
}
