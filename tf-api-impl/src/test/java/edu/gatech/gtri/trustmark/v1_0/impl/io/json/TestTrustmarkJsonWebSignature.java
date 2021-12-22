package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static org.junit.Assert.assertTrue;


/**
 * Created by robert on 11/19/2020.
 */
public class TestTrustmarkJsonWebSignature extends AbstractTest {

    public static final String TRUSTMARK_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.json";
    public static final String X509_CERTIFICATE_PEM_FILE = "./src/test/resources/Trustmarks/TestX509Certificate.pem";
    public static final String PRIVATE_KEY_PEM_FILE = "./src/test/resources/Trustmarks/TestPrivateKey.pem";

    @Test
    public void testSignTrustmarkJsonWebSignatureString() throws Exception {
        logger.info("Testing that we can generate/validate JSON Web Signature on trustmarks...");

        X509Certificate x509Certificate = loadX509Certificate(X509_CERTIFICATE_PEM_FILE);

        RSAPrivateKey privateKey = getPrivateKeyFromPemFile(PRIVATE_KEY_PEM_FILE);

        // TRUSTMARK_FULL_FILE refers to an unsigned trustmark in json
        File jsonFile = new File(TRUSTMARK_FULL_FILE);
        String trustmarkJson = FileUtils.readFileToString(jsonFile);

        TrustmarkJsonWebSignatureImpl jsonWebSign = new TrustmarkJsonWebSignatureImpl();


        String signedJson = jsonWebSign.generateJsonWebSignature(privateKey, trustmarkJson);

        logger.info("Successfully signed trustmark json string...");


        boolean validSignature = jsonWebSign.validateJsonWebSignature(x509Certificate, signedJson);

        assertTrue(validSignature);

        logger.info("Successfully validated trustmark JSON Web Signature...");

    }//end testSignTrustmarkJsonWebSignatureString()


    X509Certificate loadX509Certificate(String pemFile) throws Exception {
        File certPemFile = new File(pemFile);
        String certPem = FileUtils.readFileToString(certPemFile);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        ByteArrayInputStream inStream = new ByteArrayInputStream(certPem.getBytes());
        Certificate cert = cf.generateCertificate(inStream);
        X509Certificate x509Cert = (X509Certificate) cert;

        return x509Cert;
    }

    public RSAPrivateKey getPrivateKeyFromPemFile(String keyFile) throws IOException, GeneralSecurityException {
        File keyPemFile = new File(keyFile);
        String privateKeyPEM = FileUtils.readFileToString(keyPemFile);

        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = DatatypeConverter.parseBase64Binary(privateKeyPEM);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
        return privKey;
    }
}
