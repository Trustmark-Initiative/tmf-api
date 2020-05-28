package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractResolver;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.AbstractDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Nicholas on 01/23/2017.
 */
public abstract class AbstractDocumentResolver<T> extends AbstractResolver {
    
    ////// Constants //////
    
    private static final Logger log = Logger.getLogger(AbstractDocumentResolver.class);
    
    
    ////// Instance Methods - Abstract //////
    
    public abstract Class<T> getSupportedType();
    
    
    ////// Instance Methods - Concrete / Validation //////
    
    public Element getValidatedXml(String xmlString) throws ParseException {
        XmlHelper.validateXml(xmlString);
        return XmlHelper.readWithDom4j(xmlString);
    }
    
    public JSONObject getValidatedJson(String jsonString) throws ParseException {
        JSONObject result = new JSONObject(jsonString);
        AbstractDeserializer.isSupported(result);
        return result;
    }
    
    
    ////// Instance Methods - Concrete / Resolution //////
    
    public T resolve(URL url) throws ResolveException { return this.resolve(url, false); }
    public T resolve(URL url, Boolean validate) throws ResolveException {
        try {
            return resolve(url.toURI(), validate);
        }
        catch (URISyntaxException urise) {
            String message = String.format("Invalid URI[%s] given to resolve(url)!", url);
            throw new ResolveException(message, urise);
        }
    }
    
    
    public T resolve(URI uri) throws ResolveException { return this.resolve(uri, false); }
    public T resolve(URI uri, Boolean validate) throws ResolveException {
        if (validate == null) { validate = false; }
        if (uri == null) {
            throw new NullPointerException("The URI to resolve is a required argument and cannot be null.");
        }
        
        log.info("Resolving " + this.getSupportedType().getSimpleName() + " URI[" + uri + "] with validate=" + validate + "...");
        
        log.debug("Getting URIResolver...");
        URIResolver uriResolver = FactoryLoader.getInstance(URIResolver.class);
        if (uriResolver == null) {
            log.error("No URIResolver instance is registered in the system.  Please call FactoryLoader.register() and place your implementation.");
            throw new UnsupportedOperationException("Missing URIResolver in FactoryLoader, please load appropriately before calling this method again.");
        }
        log.debug("Resolving URI[" + uri + "]...");
        String source = uriResolver.resolve(uri);
        return resolve(source, validate);
    }
    
    
    public T resolve(File file) throws ResolveException { return this.resolve(file, false); }
    public T resolve(File file, Boolean validate) throws ResolveException {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        }
        catch (IOException ioe) {
            String message = String.format("An error occurred while creating the reader for file[%s]: %s", file, ioe.getMessage());
            throw new ResolveException(message, ioe);
        }
        return resolve(reader, validate);
    }
    
    
    public T resolve(InputStream inputStream) throws ResolveException { return this.resolve(inputStream, false); }
    public T resolve(InputStream inputStream, Boolean validate) throws ResolveException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        return resolve(reader, validate);
    }
    
    
    public T resolve(Reader reader) throws ResolveException { return this.resolve(reader, false); }
    public T resolve(Reader reader, Boolean validate) throws ResolveException {
        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to in memory string...");
            IOUtils.copy(reader, inMemoryString);
        }
        catch (IOException ioe) {
            String message = String.format("An unexpected error occurred reading from the Reader: %s", ioe.getMessage());
            throw new ResolveException(message, ioe);
        }
        return resolve(inMemoryString.toString(), validate);
    }
    
    
    public T resolve(String rawString) throws ResolveException { return this.resolve(rawString, false); }
    public T resolve(String rawString, Boolean validate) throws ResolveException {
        log.info("Reading " + this.getSupportedType().getSimpleName() + " from an incoming string (validate=" + validate + "...");
        if (log.isDebugEnabled()) {
            log.debug("Incoming " + this.getSupportedType().getSimpleName() + " string: \n" + rawString);
        }
        Codec<T> codec = Codec.loadCodecFor(this.getSupportedType());
        if (isJson(rawString)) {
            log.debug("Determined the string to be JSON, attempting to parse...");
            JSONObject root = this.getValidatedJson(rawString);
            return codec.jsonDeserializer.deserializeRootObjectNode(root, rawString);
        }
        else if (isXml(rawString)) {
            log.debug("Determined the string to be XML, attempting to parse...");
            Element root = this.getValidatedXml(rawString);
            return codec.xmlDeserializer.deserializeRootObjectNode(root, rawString);
        }
        else {
            log.warn("String is neither JSON nor XML from what I can tell.  Abandoning parse...");
            throw new ParseException(this.getSupportedType().getSimpleName() + " String contains invalid content.");
        }
    }
    
}
