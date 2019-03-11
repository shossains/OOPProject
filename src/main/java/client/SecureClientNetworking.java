package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class SecureClientNetworking {
    private final URL serverUrl;
    private SSLContext sslContext;

    /**
     * Sets up the client networking by setting up ssl and private variables.
     *
     * @param url Url to connect to
     */
    public SecureClientNetworking(URL url) {
        serverUrl = url;

        //let the connection trust all certificates so we can actually work with our server
        trustfulContext();
        trustfulHostnames();
    }

    /**
     * Opens a connection and sends a request to the server set up previously.
     *
     * @param request The String request to send to the URL via POST
     * @return Response from the server.
     */
    public String sendPostRequest(String request) {
        try {
            URLConnection urlConnection = serverUrl.openConnection();
            HttpURLConnection httpConn;
            if (serverUrl.getProtocol().equals("https")) {
                httpConn = (HttpsURLConnection) urlConnection;
            } else {
                httpConn = (HttpURLConnection) urlConnection;
            }
            httpConn.setRequestMethod("POST");
            httpConn.setChunkedStreamingMode(8);
            httpConn.setDoOutput(true);
            httpConn.connect();

            //write to stream
            OutputStream os = httpConn.getOutputStream();
            os.write(request.getBytes());

            //response
            Scanner scanner = new Scanner(httpConn.getInputStream()).useDelimiter("\\A");
            String responseString = scanner.hasNext() ? scanner.next() : "";

            return responseString;


        } catch (IOException e) {
            System.out.println("Java IO exception");
            return null;
        }
    }

    /**
     * Sends a https get request to the url.
     *
     * @param query The string in the get request that goes right after the ? in the url.
     *              This method doesn't validate, so you will have to do it yourself.
     * @return The String response from the server.
     */
    public String sendGetRequest(String query) {
        try {
            URL queryUrl = new URL(serverUrl.toString() + "?" + query);
            HttpURLConnection httpConn;
            if (queryUrl.getProtocol().equals("https")) {
                URLConnection urlConnection = queryUrl.openConnection();
                httpConn = (HttpsURLConnection) urlConnection;
            } else {
                URLConnection urlConnection = queryUrl.openConnection();
                httpConn = (HttpURLConnection) urlConnection;
            }
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            //response
            Scanner scanner = new Scanner(httpConn.getInputStream()).useDelimiter("\\A");
            String responseString = scanner.hasNext() ? scanner.next() : "";

            return responseString;


        } catch (IOException e) {
            System.out.println("Java IO exception");
            return null;
        }
    }

    /**
     * Lets the server use self-signed certificates.
     */
    private void trustfulContext() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            return;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
        } catch (KeyManagementException e) {
            System.out.println("KeyManagementException");
        }
        System.out.println("Failed to allow untrusted certificates");

    }

    /**
     * Lets the server use self-signed certificates, by allowing all hostnames.
     */
    private void trustfulHostnames() {
        HostnameVerifier trustAllHostnames = (hostname, session) -> {
            return true; // Just allow them all.
        };


        //System.setProperty("jsse.enableSNIExtension", "false");
        HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);


    }
}
