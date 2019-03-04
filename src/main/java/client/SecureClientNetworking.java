package client;

import java.io.IOException;
import java.io.OutputStream;
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
     * @param request The String request to send to the URL via POST
     * @return Response from the server.
     */
    public String sendPostRequest(String request) {
        try {
            URLConnection urlConnection = serverUrl.openConnection();
            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
            httpsConn.setRequestMethod("POST");
            httpsConn.setChunkedStreamingMode(8);
            httpsConn.setDoOutput(true);
            httpsConn.connect();

            //write to stream
            OutputStream os = httpsConn.getOutputStream();
            os.write(request.getBytes());

            //response
            Scanner scanner = new Scanner(httpsConn.getInputStream()).useDelimiter("\\A");
            String responseString = scanner.hasNext() ? scanner.next() : "";

            return responseString;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendGetRequest(String query){
        try {
            URL queryUrl = new URL(serverUrl.toString()+"?"+query);
            URLConnection urlConnection = queryUrl.openConnection();
            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
            httpsConn.setRequestMethod("GET");
            httpsConn.connect();

            //response
            Scanner scanner = new Scanner(httpsConn.getInputStream()).useDelimiter("\\A");
            String responseString = scanner.hasNext() ? scanner.next() : "";

            return responseString;


        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
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
