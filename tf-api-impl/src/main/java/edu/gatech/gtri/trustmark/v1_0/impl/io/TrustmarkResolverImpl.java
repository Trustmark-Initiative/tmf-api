package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
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
public class TrustmarkResolverImpl extends AbstractResolver implements TrustmarkResolver {

    private static final Logger log = Logger.getLogger(TrustmarkResolverImpl.class);

    @Override
    public Trustmark resolve(String trustmarkString, Boolean validate) throws ResolveException {
        log.info("Reading a Trustmark from an incoming Trustmark string (validate="+validate+"...");
        if( log.isDebugEnabled() )
            log.debug("Incoming Trustmark string: \n"+trustmarkString);

        if( isJson(trustmarkString) ){
            log.debug("Determined the string to be JSON, attempting to parse...");
            return TrustmarkJsonDeserializer.deserialize(trustmarkString);
        }else if( isXml(trustmarkString) ){
            log.debug("Determined the string to be XML, attempting to parse...");
            return TrustmarkXmlDeserializer.deserialize(trustmarkString);
        }else{
            log.warn("Trustmark string is neither JSON nor XML from what I can tell.  Abandoning parse...");
            throw new ParseException("Trustmark String contains invalid content.");
        }
    }//end Resolve()


    @Override
    public Trustmark resolve(URL url) throws ResolveException {
        return resolve(url, false);
    }

    @Override
    public Trustmark resolve(URL url, Boolean validate) throws ResolveException {
        try {
            return resolve(url.toURI(), validate);
        }catch(URISyntaxException urise){
            throw new ResolveException("Invalid URI["+url+"] given to resolve(url)!", urise);
        }
    }

    @Override
    public Trustmark resolve(URI uri) throws ResolveException {
        return resolve(uri, false);
    }//end resolve()

    @Override
    public Trustmark resolve(URI uri, Boolean validate) throws ResolveException {
        if( validate == null )
            validate = false;
        if( uri == null )
            throw new NullPointerException("The URI to resolve is a required argument and cannot be null.");

        log.info("Resolving Trustmark URI["+uri+"] with validate="+validate+"...");

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
    public Trustmark resolve(File file) throws ResolveException {
        return resolve(file, false);
    }

    @Override
    public Trustmark resolve(File file, Boolean validate) throws ResolveException {
        FileReader reader = null;
        try{
            reader = new FileReader(file);
        }catch(IOException ioe){
            throw new ResolveException("An error occurred while creating the reader for file["+file+"]: "+ioe.getMessage(), ioe);
        }
        return resolve(reader, validate);
    }

    @Override
    public Trustmark resolve(InputStream trustmarkInputStream) throws ResolveException {
        return resolve(trustmarkInputStream, false);
    }

    @Override
    public Trustmark resolve(InputStream trustmarkInputStream, Boolean validate) throws ResolveException {
        InputStreamReader reader = new InputStreamReader(trustmarkInputStream);
        return resolve(reader, validate);
    }

    @Override
    public Trustmark resolve(Reader trustmarkReader) throws ResolveException {
        return resolve(trustmarkReader, false);
    }

    @Override
    public Trustmark resolve(Reader trustmarkReader, Boolean validate) throws ResolveException {
        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to in memory string...");
            IOUtils.copy(trustmarkReader, inMemoryString);
        }catch(IOException ioe){
            throw new ResolveException("An unexpected error occurred reading from the Reader: "+ioe.getMessage(), ioe);
        }
        return resolve(inMemoryString.toString(), validate);
    }

    @Override
    public Trustmark resolve(String trustmarkString) throws ResolveException {
        return resolve(trustmarkString, false);
    }



}//end TrustmarkResolverIMpl