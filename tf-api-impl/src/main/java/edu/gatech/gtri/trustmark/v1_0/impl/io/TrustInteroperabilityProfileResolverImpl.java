package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.*;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by brad on 12/9/15.
 */
public class TrustInteroperabilityProfileResolverImpl extends AbstractResolver implements TrustInteroperabilityProfileResolver {

    private static final Logger log = Logger.getLogger(TrustInteroperabilityProfileResolverImpl.class);

    @Override
    public TrustInteroperabilityProfile resolve(String tipString, Boolean validate) throws ResolveException {
        System.out.println("Reading a TrustInteroperabilityProfile from an incoming string (validate="+validate+"..."+tipString);
        if( log.isDebugEnabled() )
            log.debug("Incoming TrustInteroperabilityProfile string: \n"+tipString);

        if( isJson(tipString) ){
            log.debug("Determined the string to be JSON, attempting to parse...");
            return TrustInteroperabilityProfileJsonDeserializer.deserialize(tipString);
        }else if( isXml(tipString) ){
            log.debug("Determined the string to be XML, attempting to parse...");
            return TrustInteroperabilityProfileXmlDeserializer.deserialize(tipString);
        }else{
            log.warn("Trustmark string is neither JSON nor XML from what I can tell.  Abandoning parse...");
            throw new ParseException("TrustInteroperabilityProfile String contains invalid content.");
        }
    }//end Resolve()

    @Override
    public TrustInteroperabilityProfile resolve(URL tipURL) throws ResolveException {
        return resolve(tipURL, false);
    }

    @Override
    public TrustInteroperabilityProfile resolve(URL tipURL, Boolean validate) throws ResolveException {
        try {
            return resolve(tipURL.toURI(), validate);
        }catch(URISyntaxException urise){
            throw new ResolveException("Invalid URI given to resolve(URL) method!", urise);
        }
    }

    @Override
    public TrustInteroperabilityProfile resolve(URI uri) throws ResolveException {
        return resolve(uri, false);
    }//end resolve()

    @Override
    public TrustInteroperabilityProfile resolve(URI uri, Boolean validate) throws ResolveException {
        if( validate == null )
            validate = false;
        if( uri == null )
            throw new NullPointerException("The URI to resolve is a required argument and cannot be null.");

        log.info("Resolving TrustInteroperabilityProfile URI["+uri+"] with validate="+validate+"...");

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
    public TrustInteroperabilityProfile resolve(File file) throws ResolveException {
        return resolve(file, false);
    }

    @Override
    public TrustInteroperabilityProfile resolve(File file, Boolean validate) throws ResolveException {
        FileReader reader = null;
        try{
            reader = new FileReader(file);
        }catch(IOException ioe){
            throw new ResolveException("An error occurred while creating the reader for file["+file+"]: "+ioe.getMessage(), ioe);
        }
        return resolve(reader, validate);
    }

    @Override
    public TrustInteroperabilityProfile resolve(InputStream tipInputStream) throws ResolveException {
        return resolve(tipInputStream, false);
    }

    @Override
    public TrustInteroperabilityProfile resolve(InputStream tipInputStream, Boolean validate) throws ResolveException {
        InputStreamReader reader = new InputStreamReader(tipInputStream);
        return resolve(reader, validate);
    }

    @Override
    public TrustInteroperabilityProfile resolve(Reader tipReader) throws ResolveException {
        return resolve(tipReader, false);
    }

    @Override
    public TrustInteroperabilityProfile resolve(Reader tipReader, Boolean validate) throws ResolveException {
        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to in memory string...");
            IOUtils.copy(tipReader, inMemoryString);
            tipReader.close();
        }catch(IOException ioe){
            throw new ResolveException("An unexpected error occurred reading from the Reader: "+ioe.getMessage(), ioe);
        }
        return resolve(inMemoryString.toString(), validate);
    }

    @Override
    public TrustInteroperabilityProfile resolve(String tipString) throws ResolveException {
        return resolve(tipString, false);
    }



}//end TrustmarkResolverIMpl