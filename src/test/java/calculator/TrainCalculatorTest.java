package calculator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.net.MalformedURLException;

public class TrainCalculatorTest {

    String actual = "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
    JSONObject jsonObject = new JSONObject(actual);
    JSONObject obj1 = jsonObject.getJSONObject("decisions");
    JSONObject obj2 = obj1.getJSONObject("carbon");
    JSONObject obj3 = obj2.getJSONObject("object");
    double obj4 = obj3.getDouble("value");

    @Test
    public void train() {
    }

    @Test
    public void carTrain() {
    }

    @Test
    public void TestTrain() throws Exception {
        int result = TrainCalculator.train(20);
        Assert.assertEquals(26, result);
    }

    @Test
    public void TestTrainCar() throws MalformedURLException {
        int result = TrainCalculator.carTrain(10, 0.62);
        Assert.assertEquals(13, result);
    }


    @Test
    public void JsonSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertEquals("{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", actual, JSONCompareMode.STRICT);
    }

    @Test
    public void JsonNotSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertNotEquals(
                "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"},},}  }", actual, true);

    }


    @Test public void JsonAlmostSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertEquals(
                "{decisions: {carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"},},}  }", actual, false);
    }

    @Test
    public void JsonObj1() throws JSONException {
        JSONAssert.assertEquals("{carbon: {description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj1, true);
    }

    @Test
    public void JsonObj2() throws JSONException {
        JSONAssert.assertEquals("{description:\"1.2 kg\", object: {value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj2, true);
    }

    @Test
    public void JsonObj3() throws JSONException {
        JSONAssert.assertEquals("{value:\"1.2380529838363266\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj3, true);
    }

    @Test
    public void JsonObj4() throws JSONException {
        Assert.assertEquals(1.2380529838363266, obj4, 0);
    }
}