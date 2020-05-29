package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by brad on 12/10/15.
 */
public class LSResourceResolverImpl implements LSResourceResolver {

    private static Logger log = Logger.getLogger(LSResourceResolverImpl.class);


    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        log.info("Returning resource "+publicId+", " + systemId+", "+namespaceURI+", "+type);
        if( namespaceURI.equals(XmlConstants.XML_SIG_NAMESPACE_URI) ) {
            log.debug("Encountered XML Digital signature namespace, returning that...");
            String contentString = readString(XmlConstants.XML_SIG_SCHEMA_PATH);
            return new LSInputImpl(baseURI, publicId, systemId, contentString);
        }
        return null;
    }


    /**
     * Responsible for reading the resource path in as a string.
     */
    private String readString( String path ){
        InputStream inputStream = XmlHelper.class.getResourceAsStream(path);
        StringWriter inmemoryString = new StringWriter();
        try {
            IOUtils.copy(new InputStreamReader(inputStream), inmemoryString);
        }catch(Exception e){
            log.error("Error reading Resource["+path+"]", e);
            throw new UnsupportedOperationException("Unexpected error reading static resource["+path+"]: "+e.getMessage(), e);
        }
        return inmemoryString.toString();
    }//end toLSInput()


}
