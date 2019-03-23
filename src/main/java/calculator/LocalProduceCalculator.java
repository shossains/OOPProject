package calculator;

import java.util.Scanner;

public class LocalProduceCalculator {

    public static void produce() {

        Scanner input = new Scanner(System.in);

        System.out.println("Kg food bought: ");
        double number = input.nextDouble();

        number = number/1000;

        //Calculate amount of kg CO2 saved.
        double localFood = 0.232 * number;
        double importFood = 0.560 * number;

        double result = importFood - localFood;

        result = Math.round(result * 100.00) / 100.00;
        System.out.println(result + " kg CO2 saved");
    }


}
