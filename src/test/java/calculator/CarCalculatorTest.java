package calculator;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class CarCalculatorTest {

    @Test
    public void car() {

    }

    @Test
    public void should_be_equals_when_is_same_objects_and_strict_mode_test() throws JSONException {

        final String expected =
                "{" +
                        "'decisions' :" + "{" +
                            "'carbon' :" + "{" +
                                "'description' : '2.7 kg'," +
                                "'object' :" + "{" +
                                    "'value' : '2.6755309506179863'," +
                                    "'units' : 'kilograms'" +
                                "}," +
                                "'methodology' : 'from co2 emission, ch4 emission, n2o emission, and hfc emission'" +
                            "}," +
                        "}," +
                        "}";
        final  String actual =
                "{" +
                        "'decisions' :" + "{" +
                            "'carbon' :" + "{" +
                                "'description' : '2.7 kg'," +
                                "'object' :" + "{" +
                                    "'value' : '2.6755309506179863'," +
                                    "'units' : 'kilograms'" +
                                "}," +
                                "'methodology' : 'from co2 emission, ch4 emission, n2o emission, and hfc emission'" +
                            "}," +
                        "}," +
                        "}";
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }

//    @Test
//    public void should_be_equals_when_adding_other_fields_and_lenient_mode_test() throws JSONException {
//
//        final String expected =
//                "{" +
//                        "'decisions' :" + "{" +
//                            "'carbon' :" + "{" +
//                                "'description' : '2.7 kg'," +
//                                "'object' :" + "{" +
//                                    "'value' : '2.6755309506179863'," +
//                                    "'units' : 'kilograms'" +
//                                "}" +
//                                "'methodology' : 'from co2 emission, ch4 emission, n2o emission, and hfc emission'" +
//                            "}" +
//                        "}" +
//                        "}";
//        final  String actual =
//                "{" +
//                        "'decisions' :" + "{" +
//                            "'carbon' :" + "{" +
//                                "'description' : '2.7 kg'," +
//                                "'object' :" + "{" +
//                                    "'value' : '2.6755309506179863'," +
//                                    "'units' : 'kilograms'" +
//                                "}" +
//                                "'methodology' : 'from co2 emission, ch4 emission, n2o emission, and hfc emission'" +
//                            "}" +
//                        "}" +
//                        "}";
//        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
//    }

//    @Test
//    public void should_be_equals_when_adding_other_fields_and_lenient_mode_test() throws JSONException {
//
//        final String expected =
//                "{" +
//                        "'id' : '123456'," +
//                        "'name' : 'Toto'" +
//                        "}";
//        final  String actual =
//                "{" +
//                        "'id' : '123456'," +
//                        "'name' : 'Toto'," +
//                        "'age': 27" +
//                        "}";
//        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
//    }

//    @Test public void shouldbeequal() throws JSONException {
//        String actual = "{id:123, name:\"John\", zip:\"33025\"}";
//        JSONAssert.assertEquals(
//                "{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);
//    }

    @Test public void shouldbeequal() throws JSONException {
        String actual = "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"}, methodology:\"from co2 emission, ch4 emission, n2o emission, and hfc emission\"},}  }";
        JSONAssert.assertEquals(
                "{decisions: {carbon: {description:\"2.7 kg\", object: {value:\"2.6755309506179863\", units:\"kilograms\"},},}  }", actual, JSONCompareMode.LENIENT);
    }



}
