package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.junit.Test;


import java.io.*;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Date;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import sun.security.x509.*;
import javax.xml.bind.DatatypeConverter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.spec.*;
import javax.crypto.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * Created by robert on 8/29/2020.
 */
public class TestTrustmarkXmlSignature extends AbstractTest {

    public static final String TRUSTMARK_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.xml";
    public static final String X509_CERTIFICATE_PEM_FILE = "./src/test/resources/Trustmarks/TestX509Certificate.pem";
    public static final String PRIVATE_KEY_PEM_FILE = "./src/test/resources/Trustmarks/TestPrivateKey.pem";

    @Test
    public void testSignTrustmarkXmlString() throws Exception {
        logger.info("Testing that we can generate/validate XML signatures on trustmarks...");

        X509Certificate x509Certificate = loadX509Certificate(X509_CERTIFICATE_PEM_FILE);

        RSAPrivateKey privateKey = getPrivateKeyFromPemFile(PRIVATE_KEY_PEM_FILE);

        // TRUSTMARK_FULL_FILE refers to an unsigned trustmark
        File xmlFile = new File(TRUSTMARK_FULL_FILE);
        String trustmarkXml = FileUtils.readFileToString(xmlFile);

        TrustmarkXmlSignatureImpl xmlSign = new TrustmarkXmlSignatureImpl();

        String referenceUri = "tf:id";
        String signedXml = xmlSign.generateXmlSignature(x509Certificate, privateKey, referenceUri, trustmarkXml);

        logger.info("Successfully signed trustmark xml string...");

        //  Save the file to the local file system to allow the use of XmlSecTool to validate the xml signature:
        //     saveXmlToFile(signedXml, "PathToSignedXmlFile");
        //  Use XmlSecTool for XML signature verification (Replace PathToCertificatePemFile below
        //  with X509_CERTIFICATE_PEM_FILE above):
        //     ./xmlsectool.sh --verifySignature --inFile PathToSignedXmlFile --certificate PathToCertificatePemFile
        //saveXmlToFile(signedXml, "/localPath/signedWithJavaTrustmark.xml");
        
        boolean validSignature = xmlSign.validateXmlSignature(referenceUri, signedXml);

        assertTrue(validSignature);

        logger.info("Successfully validated trustmark xml signature...");

    }//end testSignTrustmarkXmlString()


    X509Certificate loadX509Certificate(String pemFile) throws Exception{
        File certPemFile = new File(pemFile);
        String certPem = FileUtils.readFileToString(certPemFile);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        ByteArrayInputStream inStream = new ByteArrayInputStream(certPem.getBytes());
        Certificate cert = cf.generateCertificate(inStream);
        X509Certificate x509Cert = (X509Certificate)cert;

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

    private void saveXmlToFile(String xmlString, String path) throws Exception {
        Document doc = loadXMLFrom(xmlString);

        // Output the resulting document.
        OutputStream os = new FileOutputStream(path);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(os));
    }

    Document loadXMLFrom(String xml) throws Exception {
        InputSource is= new InputSource(new StringReader(xml));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);
        return doc;
    }
}
