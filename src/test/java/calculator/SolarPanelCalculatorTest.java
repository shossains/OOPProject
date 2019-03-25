package calculator;

import org.junit.Assert;
import org.junit.Test;

public class SolarPanelCalculatorTest {

    @Test
    public void solar() {
    }

    /**
     * Testing if the calculator works as it should with values > 0.
     */
    @Test
    public void SolarTest(){
        Double result = SolarPanelCalculator.solar(20);
        Assert.assertEquals(9.2, result, 1);
    }

    /**
     * Testing if the calculator works as it should with the value 0.
     */
    @Test
    public void SolarZero() {
        Double result = SolarPanelCalculator.solar(0);
        Assert.assertEquals(0, result, 1);
    }
}