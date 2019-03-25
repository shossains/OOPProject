package calculator;

import org.junit.Assert;
import org.junit.Test;

public class LocalProduceCalculatorTest {

    @Test
    public void produce() {
    }

    /**
     * Testing if the calculator works as it should with values > 0.
     */
    @Test
    public void local() {
        Double result = LocalProduceCalculator.produce(600);
        Assert.assertEquals(0.2, result, 1);
    }

    /**
     * Testing if the calculator works as it should with the value 0.
     */
    @Test
    public void localZero() {
        Double result = LocalProduceCalculator.produce(0);
        Assert.assertEquals(0, result, 1);
    }
}