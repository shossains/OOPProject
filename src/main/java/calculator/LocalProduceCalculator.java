package calculator;

public class LocalProduceCalculator {
    public static int produce(int weight) {
        //Calculate amount of kg CO2 saved.
        Double localFood = 0.232 * weight;
        Double importFood = 0.560 * weight;

        Double doubleResult = importFood - localFood;

        doubleResult = Math.round(doubleResult) / 10.00;
        return doubleResult.intValue();
    }


}
