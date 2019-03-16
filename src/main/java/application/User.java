package application;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class that authenticates and stores data about the user. This is the main class
 * to use when dealing with and updating user data.
 * This should also be the class that serves to save user data on the client side for
 * e.g. automatic logins, and local storage of said logins.
 * Also, contains the server URL, as it is a convenient place to store it.
 */
public class User {
    private static String username;
    private static String password;
    private static URL serverURL;


    public static URL getServerUrl() {
        return serverURL;
    }

    /**
     * Sets up the serverURL to be used for client-server communication.
     * todo: verify if the URL actually contains a live server.
     * @param url string version of the url
     * @return true if successful, false if not
     */
    public static boolean setServerUrl(String url) {
        try {
            serverURL = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }
}
