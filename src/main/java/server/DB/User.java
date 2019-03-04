package server.DB;

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

    }
}
