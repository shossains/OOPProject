package server.queries;

public class TestQuery extends ServerQuery {
    boolean isTest;

    /**
     * Test Query run, probably to be expanded in the future.
     *
     * @return Test String in JSON format containing the isTest variable.
     */
    public String runQuery() {

        return "{\"success\":\"who knows\", \"isTest\": "
                + Boolean.toString(isTest) + ", \"username\":\"alexshulzycki\"}";
    }
}
