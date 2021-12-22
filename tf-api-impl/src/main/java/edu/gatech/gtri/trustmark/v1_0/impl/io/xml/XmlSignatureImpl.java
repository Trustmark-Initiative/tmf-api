package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureFailure;
import edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureKeySelector;
import edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureUtility;
import edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import static edu.gatech.gtri.trustmark.v1_0.impl.trust.XmlSignatureUtility.sign;
import static edu.gatech.gtri.trustmark.v1_0.trust.XmlSignatureValidatorFailure.messageFor;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.function.Function.identity;

/**
 * Generate the XML Signature for the Trustmark xml string.
 * <p>
 * Created by robert on 8/29/2020.
 */
public final class XmlSignatureImpl {

    private static final Logger log = LoggerFactory.getLogger(XmlSignatureImpl.class);

    public XmlSignatureImpl() {
    }

    public String generateXmlSignature(
            final X509Certificate x509Certificate,
            final PrivateKey privateKey,
            final String attributeName,
            final String xmlString) throws Exception {

        final InputStream targetStream = new ByteArrayInputStream(xmlString.getBytes());

        return generateXmlSignature(x509Certificate, privateKey, targetStream, attributeName);
    }

    public String generateXmlSignature(
            final X509Certificate x509Certificate,
            final PrivateKey privateKey,
            final String attributeName,
            final String path,
            final String xmlFile) throws Exception {

        final InputStream targetStream = new FileInputStream(path + "/" + xmlFile);

        return generateXmlSignature(x509Certificate, privateKey, targetStream, attributeName);
    }

    public String generateXmlSignature(
            final X509Certificate x509Certificate,
            final PrivateKey privateKey,
            final InputStream xmlStream,
            final String attributeName) throws Exception {

        final F1<Document, Attr> fAttr = document -> (Attr) document.getDocumentElement().getAttributes().getNamedItem(attributeName);

        final Document document = documentFor(
                new InputSource(xmlStream),
                fAttr);

        final Validation<NonEmptyList<XmlSignatureFailure>, Document> documentValidation = sign(
                document,
                uriFor(document, fAttr),
                privateKey,
                x509Certificate);

        if (documentValidation.isFail()) {

            documentValidation
                    .f().forEach(nel -> nel.forEach(xmlSignatureFailure -> log.error(XmlSignatureFailure.messageFor(xmlSignatureFailure))));

            throw documentValidation.fail().head().match(
                    identity(),
                    identity(),
                    identity(),
                    identity(),
                    identity(),
                    identity());

        } else {
            // Output the resulting document.
            final StringWriter stringWriter = new StringWriter();
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(documentValidation.success()), new StreamResult(stringWriter));

            return stringWriter.toString();
        }
    }

    public boolean validateXmlSignature(
            final String attributeName,
            final String xmlString) throws Exception {

        return validateXmlSignature(xmlString, document -> (Attr) document.getDocumentElement().getAttributes().getNamedItem(attributeName));
    }

    public boolean validateXmlSignature(
            final String xmlString,
            final F1<Document, Attr> fAttr) throws Exception {

        final Validation<NonEmptyList<XmlSignatureValidatorFailure>, XMLSignature> xmlSignatureValidation =
                XmlSignatureUtility.validate(documentFor(
                        new InputSource(new StringReader(xmlString)),
                        fAttr), new XmlSignatureKeySelector());

        if (xmlSignatureValidation.isFail()) {

            xmlSignatureValidation
                    .f().forEach(nel -> nel.forEach(xmlSignatureValidationFailure -> log.error(messageFor(xmlSignatureValidationFailure))));

            final Either<Exception, Boolean> either = xmlSignatureValidation.fail().head()
                    .match(
                            (document) -> left(new Exception("The system did not find a signature element.")),
                            (document, marshalException) -> left(marshalException),
                            (document, xmlSignatureException) -> left(xmlSignatureException),
                            (document) -> right(false),
                            (document, reference) -> right(false));

            if (either.isLeft()) {
                throw either.left().value();
            } else {
                return either.right().value();
            }
        } else {
            return true;
        }
    }

    public static String toString(Document doc) {
        try {
            final StringWriter stringWriter = new StringWriter();
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    private Document documentFor(
            final InputSource inputSource,
            final F1<Document, Attr> fAttr) throws Exception {

        // if setSchema worked, we could use this rather than require the client specify the attribute
        // final Source sourceSchemaForTrustmark = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH));
        // final Source sourceSchemaForTrustmark13 = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_3));
        // final Source sourceSchemaForTrustmark14 = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_4));
        // final Source sourceSchemaForXmlSignature = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.XML_SIG_SCHEMA_PATH));

        // final Source[] sourceSchemaArray = new Source[]{sourceSchemaForTrustmark, sourceSchemaForTrustmark13, sourceSchemaForTrustmark14, sourceSchemaForXmlSignature};
        // final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // schemaFactory.setResourceResolver(new LSResourceResolverImpl());
        // final Schema schema = schemaFactory.newSchema(sourceSchemaArray);

        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        // documentBuilderFactory.setSchema(schema);

        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        final Document document = documentBuilder.parse(inputSource);
        // if setSchema worked, we could use this rather than require the client specify the attribute
        document.getDocumentElement().setIdAttributeNode(fAttr.f(document), true);

        return document;
    }

    private String uriFor(
            final Document document,
            final F1<Document, Attr> fAttr) {

        return fromNull(fAttr.f(document))
                .map(Node::getNodeValue)
                .map(String::trim)
                .filter(String::isEmpty)
                .map(uri -> "#" + uri)
                .orSome("");
    }
}
