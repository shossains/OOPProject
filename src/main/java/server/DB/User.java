package server.DB;

/**
 * The class that authenticates and stores data about the user. This is the main class
 * to use when dealing with and updating user data.
 */
public class User {
    private String username;
    private String password;

    /**
     * @param username Username in the DB
     * @param password  Password in the DB
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * fetches user data from database
     */
    public void fetchDB(){
        //TODO fetching user data from the database
    }
}
