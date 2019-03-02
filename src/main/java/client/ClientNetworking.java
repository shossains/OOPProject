package client;


import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ClientNetworking {
    private URL serverURL;

    public ClientNetworking(URL url){
        serverURL = url;
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
}
