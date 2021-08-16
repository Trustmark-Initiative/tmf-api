package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by brad on 12/9/15.
 */
public class XmlHelper {

    private static final Logger log = LogManager.getLogger(XmlHelper.class);

    /**
     * Returns true if the XML File uses a legacy namespace.
     */
    public static Boolean usesLegacyNamespace(Element rootElement){
        boolean legacy = false;
        for( String ns : TrustmarkFrameworkConstants.OLD_NS_PREFIX_URIS ){
            if( rootElement.getNamespaceURI().equals(ns) ){
                legacy = true;
                break;
            }
        }
        return legacy;
    }

    /**
     * Uses DOM4j to parse XML and return the root element of the document.
     */
    public static Element readWithDom4j(String xml) throws ParseException {
        try {
            log.debug("Reading XML with DOM4j...");
            SAXReader reader = new SAXReader();
            reader.setIgnoreComments(false);
            reader.setIncludeInternalDTDDeclarations(false);
            reader.setIncludeExternalDTDDeclarations(false);
            Document document = reader.read(new StringReader(xml));
            Element rootNode = document.getRootElement();
            rootNode.addNamespace(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, TrustmarkFrameworkConstants.NAMESPACE_URI);
            return rootNode;
        }catch(Throwable t){
            log.error("Error reading xml", t);
            throw new ParseException("Unable to read XML data: "+t.getMessage(), t);
        }
    }//end parse()

    /**
     * Checks the XML against the Schema file to ensure correctness of the document.
     */
    public static void validateXml( String xml ) throws ParseException {
        log.debug("Validating XML string...");

        Element e = readWithDom4j(xml);
        if( usesLegacyNamespace(e) ){
            log.warn("Cannot parse XML, it is using a legacy namespace: "+e.getNamespaceURI()+", the current namespace is: "+TrustmarkFrameworkConstants.NAMESPACE_URI);
            throw new ParseException("This XML uses an out-dated namespace["+e.getNamespaceURI()+"].  Please convert the file to the current namespace["+TrustmarkFrameworkConstants.NAMESPACE_URI+"]");
        }

        Source trustmarkFrameworkSchema = new StreamSource( XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH) );
        Source trustmarkFrameworkSchema13 = new StreamSource( XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_3) );
        Source trustmarkFrameworkSchema14 = new StreamSource( XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_4) );

        Source[] schemas = new Source[]{trustmarkFrameworkSchema, trustmarkFrameworkSchema13, trustmarkFrameworkSchema14};
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(new LSResourceResolverImpl());
        Schema schema = null;
        try{
            schema = schemaFactory.newSchema(schemas);
        }catch(Throwable t){
            log.error("Unable to compile trustmark framework schema!", t);
            throw new UnsupportedOperationException("Unable to compile trustmark framework schema!", t);
        }
        Validator validator = schema.newValidator();
        try{
            validator.validate(new StreamSource(new StringReader(xml)));
        }catch(SAXException saxe){
            log.warn("The given XML is invalid against the Trustmark Framework 1.0 schema", saxe);
            throw new ParseException("Invalid XML", saxe);
        }catch(IOException ioe){
            log.warn("Unexpected IO exception!", ioe);
            throw new ParseException("An unexpected IO error occurred validating the document!", ioe);
        }

        log.info("XML is valid to schema.");
    }//end validateXml()


}//end XmlHelper