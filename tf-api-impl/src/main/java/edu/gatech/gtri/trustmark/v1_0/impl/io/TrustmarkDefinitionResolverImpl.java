package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.*;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkDefinitionResolverImpl extends AbstractResolver implements TrustmarkDefinitionResolver {


    private static final Logger log = Logger.getLogger(TrustmarkResolverImpl.class);

    @Override
    public TrustmarkDefinition resolve(String tdString, Boolean validate) throws ResolveException {
        log.info("Reading a TrustmarkDefinition from an incoming string (validate="+validate+"...");
        if( log.isDebugEnabled() )
            log.debug("Incoming TrustmarkDefinition string: \n"+tdString);

        TrustmarkDefinition td = null;
        if( isJson(tdString) ){
            log.debug("Determined the string to be JSON, attempting to parse...");
            td = TrustmarkDefinitionJsonDeserializer.deserialize(tdString);
        }else if( isXml(tdString) ){
            log.debug("Determined the string to be XML, attempting to parse...");
            td = TrustmarkDefinitionXmlDeserializer.deserialize(tdString);
        }else{
            log.warn("TrustmarkDefinition string is neither JSON nor XML from what I can tell.  Abandoning parse...");
            throw new ParseException("TrustmarkDefinition String contains invalid content.  Expecting XML or JSON.");
        }

        if( validate ){
            Collection<ValidationResult> results = FactoryLoader.getInstance(TrustmarkDefinitionUtils.class).validate(td);
            if( results != null && results.size() > 0 ){
                log.warn("Error - TrustmarkDefinition validation results: ");
                ValidationResult firstFatalResult = null;
                for( ValidationResult result : results ) {
                    log.warn("   " + result.getSeverity() + ":  " + result.getMessage());
                    if (result.getSeverity() == ValidationSeverity.FATAL)
                        firstFatalResult = result;
                }
                if( firstFatalResult != null ){
                    log.error("Encountered fatal error: "+firstFatalResult);
                    throw new ResolveException("Error while validating Trustmark Definition: "+firstFatalResult.getMessage());
                }
            }
        }

        return td;
    }//end Resolve()


    @Override
    public TrustmarkDefinition resolve(URL url) throws ResolveException {
        return resolve(url, false);
    }

    @Override
    public TrustmarkDefinition resolve(URL url, Boolean validate) throws ResolveException {
        try {
            return resolve(url.toURI(), validate);
        }catch(URISyntaxException urise){
            throw new ResolveException("Invalid URI["+url+"] given to resolve(url)!", urise);
        }
    }

    @Override
    public TrustmarkDefinition resolve(URI uri) throws ResolveException {
        return resolve(uri, false);
    }//end resolve()

    @Override
    public TrustmarkDefinition resolve(URI uri, Boolean validate) throws ResolveException {
        if( validate == null )
            validate = false;
        if( uri == null )
            throw new NullPointerException("The URI to resolve is a required argument and cannot be null.");

        log.info("Resolving TrustmarkDefinition URI["+uri+"] with validate="+validate+"...");

        log.debug("Getting URIResolver...");
        URIResolver uriResolver = FactoryLoader.getInstance(URIResolver.class);
        if( uriResolver == null ) {
            log.error("No URIResolver instance is registered in the system.  Please call FactoryLoader.register() and place your implementation.");
            throw new UnsupportedOperationException("Missing URIResolver in FactoryLoader, please load appropriately before calling this method again.");
        }
        log.debug("Resolving URI["+uri+"]...");
        String source = uriResolver.resolve(uri);
        return resolve(source, validate);
    }

    @Override
    public TrustmarkDefinition resolve(File file) throws ResolveException {
        return resolve(file, false);
    }

    @Override
    public TrustmarkDefinition resolve(File file, Boolean validate) throws ResolveException {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        }catch(IOException ioe){
            throw new ResolveException("An error occurred while creating the reader for file["+file+"]: "+ioe.getMessage(), ioe);
        }
        return resolve(reader, validate);
    }

    @Override
    public TrustmarkDefinition resolve(InputStream tdInputStream) throws ResolveException {
        return resolve(tdInputStream, false);
    }

    @Override
    public TrustmarkDefinition resolve(InputStream tdInputStream, Boolean validate) throws ResolveException {
        InputStreamReader reader = new InputStreamReader(tdInputStream);
        return resolve(reader, validate);
    }

    @Override
    public TrustmarkDefinition resolve(Reader tdReader) throws ResolveException {
        return resolve(tdReader, false);
    }

    @Override
    public TrustmarkDefinition resolve(Reader tdReader, Boolean validate) throws ResolveException {
        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to in memory string...");
            IOUtils.copy(tdReader, inMemoryString);
            tdReader.close();
        }catch(IOException ioe){
            throw new ResolveException("An unexpected error occurred reading from the Reader: "+ioe.getMessage(), ioe);
        }
        return resolve(inMemoryString.toString(), validate);
    }

    @Override
    public TrustmarkDefinition resolve(String tdString) throws ResolveException {
        return resolve(tdString, false);
    }



}
