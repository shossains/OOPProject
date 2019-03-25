package calculator;

public class LocalProduceCalculator {

    /**
     * The calculator for calculating the difference in kg CO2 produced by buying local food or imported food.
     * @param weight Amount of gram food bought.
     * @return (Amount of grams bought imported food) - (Amount of grams bought local produced food) = amount of grams CO2 saved
     */
    public static double produce(int weight) {
        //Calculate amount of kg CO2 saved.
        Double localFood = 232.0 * weight;
        Double importFood = 560.0 * weight;

        Double doubleResult = importFood - localFood;

        doubleResult = doubleResult /1000000;
        doubleResult = Math.round(doubleResult*100.00) / 100.00;
        return doubleResult;
    }


}
