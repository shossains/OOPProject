package client;



import javax.net.ssl.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class ClientNetworking {
    private URL serverURL;
    private SSLContext sslContext;

    public ClientNetworking(URL url){
        serverURL = url;

        //let the connection trust all certificates so we can actually work with our server
        trustfulContext();
        trustfulHostnames();
    }

    public String sendRequest(String request){
        try {
            URLConnection URLcon = serverURL.openConnection();
            HttpsURLConnection httpsConn = (HttpsURLConnection) URLcon;
            httpsConn.setRequestMethod("POST");
            httpsConn.setChunkedStreamingMode(8);
            httpsConn.setDoOutput(true);
            httpsConn.connect();

            //write to stream
            OutputStream os = httpsConn.getOutputStream();
            os.write(request.getBytes());

            //response
            Scanner s = new Scanner(httpsConn.getInputStream()).useDelimiter("\\A");
            String responseString = s.hasNext() ? s.next() : "";

            return responseString;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void trustfulContext(){
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

    private void trustfulHostnames(){
        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true; // Just allow them all.
            }
        };


            //System.setProperty("jsse.enableSNIExtension", "false");
            HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);


    }
}
