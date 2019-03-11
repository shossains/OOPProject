package server.queries;

/**
 * Fields needed for registration of a new user.
 */
public class RegisterQuery extends  ServerQuery {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    /**Runs the actual registration process.
     * @return result of registration.
     */
    public String runQuery() {
        // Logic for actually running the query

        //testing for now, so false
        return "{\"success\":\"false\"}";
    }
}
