package application;

/**
 * The class that authenticates and stores data about the user. This is the main class
 * to use when dealing with and updating user data.
 * This should also be the class that serves to save user data on the client side for
 * e.g. automatic logins, and local storage of said logins.
 */
public class User {
    private String username;
    private String password;

    /**
     * Username and password that is needed to log in to the database.
     * @param username Username in the db
     * @param password  Password in the db
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
