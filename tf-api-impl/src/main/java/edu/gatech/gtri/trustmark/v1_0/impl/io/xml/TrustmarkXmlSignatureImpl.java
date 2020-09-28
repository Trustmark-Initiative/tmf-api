package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkResolverImpl;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.Key;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.*;
import java.util.*;

/**
 * Generate the XML Signature for the Trustmark xml string.
 *
 * Created by robert on 8/29/2020.
 */
public class TrustmarkXmlSignatureImpl {

    private static final Logger log = Logger.getLogger(TrustmarkXmlSignatureImpl.class);

    public TrustmarkXmlSignatureImpl() throws Exception {
    }

    String generateXmlSignature(X509Certificate cert, PrivateKey privateKey, String uri, String xmlString) throws Exception {

        InputStream targetStream = new ByteArrayInputStream(xmlString.getBytes());

        return generateXmlSignature(cert, privateKey, uri, targetStream);
    }

    String generateXmlSignature(X509Certificate cert, PrivateKey privateKey, String uri, String path, String xmlFile) throws Exception {

        InputStream targetStream = new FileInputStream(path + "/" + xmlFile);

        return generateXmlSignature(cert, privateKey, uri, targetStream);
    }


    String generateXmlSignature(X509Certificate cert, PrivateKey privateKey, String uri, InputStream xmlStream) throws Exception {

        // Create a DOM XMLSignatureFactory that will be used to
        // generate the enveloped signature.
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");


        // Instantiate the document to be signed.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().parse(xmlStream);
        Element parent = doc.getDocumentElement();

        List<Transform> transforms = new LinkedList();

        transforms.add(fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null));
        transforms.add(fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec)null));

        uri = getSignatureReferenceUri( uri, parent);
        Reference ref = fac.newReference(uri,
                fac.newDigestMethod(DigestMethod.SHA256,
                        (DigestMethodParameterSpec)null), transforms, (String)null, (String)null);


        // Create the SignedInfo.
        // NOTE: SignatureMethod.RSA_SHA256 does not exists. WHY?
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
                (C14NMethodParameterSpec) null), fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                Collections.singletonList(ref));


        // Create the KeyInfo containing the X509Data.
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        List x509Content = new ArrayList();
        x509Content.add(cert);
        X509Data xd = kif.newX509Data(x509Content);

        KeyValue keyValue = kif.newKeyValue(cert.getPublicKey());


        final List<XMLStructure> newList = new ArrayList<>();
        newList.add(keyValue);
        newList.add(xd);
        KeyInfo ki = kif.newKeyInfo(newList);


        // Create a DOMSignContext and specify the RSA PrivateKey and
        // location of the resulting XMLSignature's parent element.
        DOMSignContext dsc = null;
        Node nextSibling = null;
        if ( parent.hasChildNodes()) {
            nextSibling = parent.getChildNodes().item(0);
            while (!(nextSibling instanceof Element) && nextSibling != null) {
                nextSibling = nextSibling.getNextSibling();
            }
        }

        if (nextSibling != null) {
            dsc = new DOMSignContext(privateKey, parent, nextSibling);
        }
        else {
            dsc = new DOMSignContext(privateKey, parent);
        }

        dsc.setDefaultNamespacePrefix("ds");

        // Create the XMLSignature, but don't sign it yet.
        XMLSignature signature = fac.newXMLSignature(si, ki);

        // Marshal, generate, and sign the enveloped signature.
        signature.sign(dsc);

        // Output the resulting document.
        OutputStream os = new ByteArrayOutputStream();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(os));

        return os.toString();
    }

    String getSignatureReferenceUri(String uri, final Element rootElement) {
        String reference = "";
        if (uri != null) {
            final Attr referenceAttribute =
                    (Attr) rootElement.getAttributes().getNamedItem(uri);
            if (referenceAttribute != null) {
                // Mark the reference attribute as a valid ID attribute
                rootElement.setIdAttributeNode(referenceAttribute, true);
                reference = referenceAttribute.getValue().trim();
                if (reference.length() > 0) {
                    reference = "#" + reference;
                }
            }
        }

        return reference;
    }

    boolean validateXmlSignature(String uri,  String signedXmlString) throws Exception {

        // Instantiate the document to be validated
        Document doc = loadXMLFrom(signedXmlString);

        Element rootElement = doc.getDocumentElement();
        uri = getSignatureReferenceUri(uri, rootElement);

        // Find Signature element
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        // Create a DOM XMLSignatureFactory that will be used to unmarshal the
        // document containing the XMLSignature
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        // Create a DOMValidateContext and specify a KeyValue KeySelector
        // and document context
        DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nl.item(0));

        // unmarshal the XMLSignature
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Validate the XMLSignature (generated above)
        boolean coreValidity = signature.validate(valContext);

        // Check core validation status
        if (coreValidity == false) {
            log.warn("Signature failed core validation");
            boolean sv = signature.getSignatureValue().validate(valContext);
            log.info(String.format("signature validation status: ", sv));

            // check the validation status of each Reference
            Iterator i = signature.getSignedInfo().getReferences().iterator();
            for (int j=0; i.hasNext(); j++) {
                boolean refValid = ((Reference) i.next()).validate(valContext);
                log.info(String.format("ref[\"+j+\"] validity status: ", refValid));
            }
        } else {
            log.warn("Signature passed core validation");
        }

        return coreValidity;
    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
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

    /**
     * KeySelector which retrieves the public key out of the
     * KeyValue element and returns it.
     * NOTE: If the key algorithm doesn't match signature algorithm,
     * then the public key will be ignored.
     */
    class KeyValueKeySelector extends KeySelector {
        public KeySelectorResult select(KeyInfo keyInfo,
                                        KeySelector.Purpose purpose,
                                        AlgorithmMethod method,
                                        XMLCryptoContext context)
                throws KeySelectorException {
            if (keyInfo == null) {
                throw new KeySelectorException("Null KeyInfo object!");
            }
            SignatureMethod sm = (SignatureMethod) method;
            List list = keyInfo.getContent();

            for (int i = 0; i < list.size(); i++) {
                XMLStructure xmlStructure = (XMLStructure) list.get(i);
                if (xmlStructure instanceof KeyValue) {
                    PublicKey pk = null;
                    try {
                        pk = ((KeyValue)xmlStructure).getPublicKey();
                    } catch (KeyException ke) {
                        throw new KeySelectorException(ke);
                    }
                    // make sure algorithm is compatible with method
                    String signatureAlgorithm =  sm.getAlgorithm();
                    String publicKeyAlgorithm = pk.getAlgorithm();
                    if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                        return new SimpleKeySelectorResult(pk);
                    }
                }
            }
            throw new KeySelectorException("No KeyValue element found!");
        }


        // This works only for DSA/RSA key types. Other key types will need to be added here.
        boolean algEquals(String algURI, String algName) {
            if (algName.equalsIgnoreCase("DSA") &&
                    algURI.equalsIgnoreCase("http://www.w3.org/2009/xmldsig11#dsa-sha256")) {
                return true;
            } else if (algName.equalsIgnoreCase("RSA") &&
                    algURI.equalsIgnoreCase("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256")) {
                return true;
            } else {
                return false;
            }
        }
    }

    class SimpleKeySelectorResult implements KeySelectorResult {
        private PublicKey pk;
        SimpleKeySelectorResult(PublicKey pk) {
            this.pk = pk;
        }

        public Key getKey() { return pk; }
    }
}//end TrustmarkXmlSignature