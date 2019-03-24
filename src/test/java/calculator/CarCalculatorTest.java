package calculator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class CarCalculatorTest {

    String actual = "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
    JSONObject jsonObject = new JSONObject(actual);
    JSONObject obj1 = jsonObject.getJSONObject("decisions");
    JSONObject obj2 = obj1.getJSONObject("carbon");
    JSONObject obj3 = obj2.getJSONObject("object");
    double obj4 = obj3.getDouble("value");


    @Test
    public void car() {

    }

    @Test
    public void TestCar() {
        int res = CarCalculator.car(50);
        Assert.assertEquals(6,res);
    }

    @Test
    public void JsonSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertEquals("{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", actual, JSONCompareMode.STRICT);
    }

    @Test
    public void JsonNotSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertNotEquals(
                "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"},},}  }", actual, true);
    }


    @Test public void JsonAlmostSame() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertEquals(
                "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"},},}  }", actual, false);
    }

    @Test
    public void JsonObj1() throws JSONException {
        JSONAssert.assertEquals("{carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj1, true);
    }


    @Test
    public void JsonObj2() throws JSONException {
        JSONAssert.assertEquals("{description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj2, true);
    }

    @Test
    public void JsonObj3() throws JSONException {
        JSONAssert.assertEquals("{value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }", obj3, true);
    }

    @Test
    public void JsonObj4() throws JSONException {
        Assert.assertEquals(2.6755309506179863, obj4, 0);
    }

}
