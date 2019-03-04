package server;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

/**
 * Server class which runs a HTTPS server, which handles request using the RequestHandler.
 */
public class Server {
    private final int port;
    private final char[] password;
    private final FileInputStream keyStream;
    private HttpsServer httpsServer;

    /**
     * The main entry point for the server, start an instance of this
     * object and the threads and request handling will all be done on their own.
     *
     * @param serverPort The port on which the server listens
     * @param keystream    The FileInputStream for the keystore
     * @param pass       The password to the keystore
     */
    public Server(int serverPort, FileInputStream keystream, char[] pass) {
        port = serverPort;
        password = pass;
        keyStream = keystream;
        try {
            runServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
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

    /**
     * Runs the server, and throws all the exceptions which could happen when trying to run it.
     *
     * @throws IOException Thrown when there's an issue creating the https server, usually
     *                      due to the port being taken.
     * @throws NoSuchAlgorithmException Thrown when there's a typo when writing the
     *                      encryption type, but since its hardcoded, shouldnt't be a problem.
     * @throws CertificateException Issue with keystore/protocol.
     * @throws UnrecoverableKeyException Issue with keystore.
     * @throws KeyStoreException Issue with keystore.
     * @throws KeyManagementException Issue with keystore.
     */
    public void runServer() throws IOException, NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        System.out.println("Server starting..");
        //setup server http
        httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
        httpsServer.createContext("/", new RequestHandler());
        httpsServer.setExecutor(null);

        //keystore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(keyStream, password);

        //key manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, password);

        //trust manager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);

        //setup server encryption
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);

        httpsServer.setHttpsConfigurator(getConfigurator(sslContext));


        httpsServer.start();
    }

    /**
     * Returns HTTPS configuration for the server.
     * @param sslContext The SSL context to be used
     * @return HttpsConfigurator to be used by the server
     */
    private HttpsConfigurator getConfigurator(SSLContext sslContext) {
        return new HttpsConfigurator(sslContext) {
            public void configure(HttpsParameters params) {

                SSLContext context = getSSLContext();
                SSLParameters sslParameters = context.getDefaultSSLParameters();
                params.setSSLParameters(sslParameters);
                SSLEngine engine = context.createSSLEngine();
                params.setNeedClientAuth(true);
                params.setCipherSuites(engine.getEnabledCipherSuites());
                params.setProtocols(engine.getEnabledProtocols());


            }
        };
    }
}
