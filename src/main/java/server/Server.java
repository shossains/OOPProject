package server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;

public class Server {
    private final int port;
    private HttpsServer httpsServer;
    private char[] password;

    /**
     * The main entry point for the server, start an instance of this
     * object and the threads and request handling will all be done on their own.
     */
    public Server(int serverPort, char[] pass) {
        port = serverPort;
        password = pass;
        try {
            runServer();
        }catch(IOException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void runServer() throws IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        System.out.println("Server starting..");
        //setup server http
        httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
        httpsServer.createContext("/", new RequestHandler());
        httpsServer.setExecutor(null);

        //setup server encryption
        SSLContext sslContext = SSLContext.getInstance("TLS");

        //keystore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream keyStream = new FileInputStream("testkey.jks");
        keyStore.load(keyStream, password);

        //key manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, password);

        //trust manager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);

        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext){
            public void configure(HttpsParameters params){

                SSLContext c = getSSLContext();
                SSLParameters sslParameters = c.getDefaultSSLParameters();
                params.setSSLParameters(sslParameters);
                SSLEngine engine = c.createSSLEngine();
                //params.setNeedClientAuth(true);
                params.setCipherSuites(engine.getEnabledCipherSuites());
                params.setProtocols(engine.getEnabledProtocols());


            }
        });



        httpsServer.start();
    }
}
