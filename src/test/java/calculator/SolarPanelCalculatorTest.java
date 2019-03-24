package calculator;

import org.junit.Assert;
import org.junit.Test;

public class SolarPanelCalculatorTest {

    @Test
    public void solar() {
    }

    @Test
    public void SolarTest(){
        int result = SolarPanelCalculator.solar(20);
        Assert.assertEquals(9.2, result, 1);
    }

    @Test
    public void SolarZero() {
        int result = SolarPanelCalculator.solar(0);
        Assert.assertEquals(0, result, 1);
    }
}