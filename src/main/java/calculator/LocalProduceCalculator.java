package calculator;

public class LocalProduceCalculator {

    /**
     * Calculating the difference in kg CO2 produced by buying local food or imported food.
     *
     * @param weight Amount of gram food bought.
     * @return (Amount of grams bought imported food) - (Amount of grams bought local produced food)
     */
    public static Double produce(int weight) {
        //Calculate amount of kg CO2 saved.
        Double localFood = 232.0 * weight;
        Double importFood = 560.0 * weight;

        Double doubleResult = importFood - localFood;

        doubleResult = doubleResult / 1000000;
        doubleResult = Math.round(doubleResult * 100.00) / 100.00;
        return doubleResult;
    }


}
