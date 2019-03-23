package calculator;

public class LocalProduceCalculator {

    public static double produce(double weight) {
        weight = weight/1000;

        //Calculate amount of kg CO2 saved.
        double localFood = 0.232 * weight;
        double importFood = 0.560 * weight;

        double result = importFood - localFood;

        result = Math.round(result * 100.00) / 100.00;
        return result;
    }


}
