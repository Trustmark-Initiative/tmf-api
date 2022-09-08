package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class XmlHelper {

    private static class XmlHelperLSResourceResolver implements LSResourceResolver {

        @Override
        public LSInput resolveResource(
                final String type,
                final String namespaceURI,
                final String publicId,
                final String systemId,
                final String baseURI) {

            if (namespaceURI.equals(XmlConstants.XML_SIG_NAMESPACE_URI)) {
                return new LSInputImpl(baseURI, publicId, systemId, readString(XmlConstants.XML_SIG_SCHEMA_PATH));
            } else {
                return null;
            }
        }

        private String readString(final String path) {
            try {
                final StringWriter stringWriter = new StringWriter();

                IOUtils.copy(new InputStreamReader(XmlHelper.class.getResourceAsStream(path)), stringWriter);

                return stringWriter.toString();
            } catch (final IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }

    private static GenericObjectPoolConfig<Validator> validatorPoolConfiguration = new GenericObjectPoolConfig<>();

    static {
        validatorPoolConfiguration.setMaxTotal(1);
    }

    private static final ObjectPool<Validator> validatorPool = new GenericObjectPool<>(new BasePooledObjectFactory<Validator>() {
        @Override
        public synchronized Validator create() throws Exception {

            final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setResourceResolver(new XmlHelperLSResourceResolver());

            return schemaFactory
                    .newSchema(new Source[]{
                            new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH)),
                            new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_3)),
                            new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_4))})
                    .newValidator();
        }

        @Override
        public PooledObject<Validator> wrap(final Validator validator) {
            return new DefaultPooledObject<>(validator);
        }
    }, validatorPoolConfiguration);


    /**
     * Return true if the element namespace URI is among the legacy namespace
     * URIs.
     *
     * @param element the element
     * @return true if the element namespace URI is among the legacy namespace
     * URIs
     */
    private static boolean legacyNamespace(final Element element) {

        return Arrays
                .stream(TrustmarkFrameworkConstants.OLD_NS_PREFIX_URIS)
                .anyMatch(uri -> element.getNamespaceURI().equals(uri));
    }

    /**
     * Return XML from an XML string.
     *
     * @param xml
     * @return
     * @throws ParseException if the XML string is not well-formed
     */
    public static Element readWithDom4j(final String xml) throws ParseException {

        requireNonNull(xml);

        try {
            final SAXReader reader = new SAXReader();
            reader.setIgnoreComments(false);
            reader.setIncludeInternalDTDDeclarations(false);
            reader.setIncludeExternalDTDDeclarations(false);

            final Element element = reader.read(new StringReader(xml)).getRootElement();
            element.addNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, TrustmarkFrameworkConstants.NAMESPACE_URI);

            return element;

        } catch (final DocumentException documentException) {
            throw new ParseException(documentException);
        }
    }

    /**
     * Validate an XML string against the Trustmark Framework Schema.
     *
     * @param xml the xml string
     * @throws ParseException if the XML namespace URI is a legacy namespace URI
     *                        OR if the XML is not valid against the Trustmark
     *                        Framework Schema.
     */
    public static void validateXml(final String xml) throws ParseException {

        requireNonNull(xml);

        if (legacyNamespace(readWithDom4j(xml))) {
            throw new ParseException(format("The system cannot validate the namespace URI '%s' because it is a legacy namespace URI.", readWithDom4j(xml).getNamespaceURI()));
        }

        try {
            validateXmlHelper(xml, validatorPool.borrowObject());
        } catch (final Exception exception) {
            throw new ParseException(exception);
        }
    }

    private static void validateXmlHelper(final String xml, final Validator validator) throws ParseException {

        requireNonNull(xml);
        requireNonNull(validator);

        try {
            validator.validate(new StreamSource(new StringReader(xml)));
            validatorPool.returnObject(validator);
        } catch (final SAXException saxException) {

            try {
                validatorPool.returnObject(validator);
            } catch (final Exception exception) {
                throw new ParseException(exception);
            }

            throw new ParseException(saxException);

        } catch (final IOException ioException) {

            try {
                validatorPool.returnObject(validator);
            } catch (final Exception exception) {
                throw new ParseException(exception);
            }

            throw new ParseException(ioException);

        } catch (final Exception exception) {

            throw new ParseException(exception);
        }
    }
}
