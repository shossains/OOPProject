package calculator;

public class SolarPanelCalculator {

    /**
     * The calculator for calculating the amount of kg CO2 produced by using solar panels.
     * @param energy amount of kWh used by solar panels
     * @return Calculated amount of kg CO2 saved by using solar panels
     */

    public static int solar(int energy) {
        Double energyUsed = 0.46 * energy;
        return energyUsed.intValue();
    }

}


