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
        //1: make sure that the username doesnt exist already
        //2: construct the database query string
        //3: run it
        //4: make sure it successufully registered a new user, or assume it has
        //5: write appropriate API docs on the API list if you add/change anything with the protocol
        return "{'error': true, 'reason' : 'not implemented yet'}";
    }
}
