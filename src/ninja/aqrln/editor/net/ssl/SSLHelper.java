package ninja.aqrln.editor.net.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * Missing certificates installer
 * @author Alexey Orlenko
 */
public class SSLHelper {

    /**
     * Load DSTRootCAX3 that seems to be missing in Java
     */
    public static void installCertificate() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            Path path = Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts");
            keyStore.load(Files.newInputStream(path), "changeit".toCharArray());

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try (InputStream stream = new BufferedInputStream(SSLHelper.class.getResourceAsStream("DSTRootCAX3.der"))) {
                Certificate certificate = certificateFactory.generateCertificate(stream);
                keyStore.setCertificateEntry("DSTRootCAX3", certificate);
            }

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
