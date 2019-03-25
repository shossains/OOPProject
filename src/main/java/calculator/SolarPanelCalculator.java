package calculator;

public class SolarPanelCalculator {

    public static int solar(int energy) {
        //Calculate amount of kg CO2 saved.
        Double energyUsed = 0.46 * energy;
        return energyUsed.intValue();

    }
}
