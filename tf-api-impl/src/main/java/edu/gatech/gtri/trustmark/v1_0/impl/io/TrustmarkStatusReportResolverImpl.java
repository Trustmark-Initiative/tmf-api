package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.*;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkStatusReportResolverImpl extends AbstractResolver implements TrustmarkStatusReportResolver {

    private static Logger log = Logger.getLogger(TrustmarkStatusReportResolverImpl.class);

    @Override
    public TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException {
        log.info("Resolving status report for Trustmark["+trustmark.getIdentifier()+"]...");
        TrustmarkStatusReportCache cache = FactoryLoader.getInstance(TrustmarkStatusReportCache.class);
        if( cache != null && cache.hasValidCacheEntry(trustmark) ) {
            log.debug("Returning valid cache entry...");
            return cache.getReportFromCache(trustmark);
        }

        URL statusReportUrl = trustmark.getStatusURL();
        log.debug("Downloading latest status report from URL["+statusReportUrl+"]...");
        NetworkDownloader networkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);
        HttpResponse response = null;
        try{
            response = networkDownloader.download(statusReportUrl);
        }catch(IOException ioe){
            throw new ResolveException("Cannot download the status report from URL["+statusReportUrl+"]: "+ioe.getMessage(), ioe);
        }

        String statusReportData = response.getContent();
        if( StringUtils.isBlank(statusReportData) )
            throw new ParseException("The status URL["+statusReportUrl+"] did not return a status report");

        log.debug("StatusReportData: \n"+statusReportData);

        TrustmarkStatusReport tsr = null;
        if( isJson(statusReportData) ){
            log.debug("Encountered JSON data, parsing...");
            tsr = TrustmarkStatusReportJsonDeserializer.deserialize(statusReportData);
        }else if( isXml(statusReportData) ){
            log.debug("Encountered XML data, parsing...");
            tsr = TrustmarkStatusReportXmlDeserializer.deserialize(statusReportData);
        }else{
            throw new ParseException("The incoming TrustmarkStatusReport is in an invalid format!");
        }
        

        if( cache != null )
            cache.updateCache(trustmark, tsr);

        return tsr;
    }//end resolve()


    @Override
    public TrustmarkStatusReport resolve(URL url) throws ResolveException {
        try {
            return resolve(url.toURI());
        }catch(URISyntaxException urise){
            throw new ResolveException("Invalid URI["+url+"] given to resolve(url)!", urise);
        }
    }

    @Override
    public TrustmarkStatusReport resolve(URI uri) throws ResolveException {
        if( uri == null )
            throw new NullPointerException("The URI to resolve is a required argument and cannot be null.");

        log.info("Resolving TrustmarkStatusReport URI["+uri+"]...");

        log.debug("Getting URIResolver...");
        URIResolver uriResolver = FactoryLoader.getInstance(URIResolver.class);
        if( uriResolver == null ) {
            log.error("No URIResolver instance is registered in the system.  Please call FactoryLoader.register() and place your implementation.");
            throw new UnsupportedOperationException("Missing URIResolver in FactoryLoader, please load appropriately before calling this method again.");
        }
        log.debug("Resolving URI["+uri+"]...");
        String source = uriResolver.resolve(uri);
        return resolve(source);
    }

    @Override
    public TrustmarkStatusReport resolve(File file) throws ResolveException {
        FileReader reader = null;
        try{
            reader = new FileReader(file);
        }catch(IOException ioe){
            throw new ResolveException("An error occurred while creating the reader for file["+file+"]: "+ioe.getMessage(), ioe);
        }
        return resolve(reader);
    }

    @Override
    public TrustmarkStatusReport resolve(InputStream trustmarkInputStream) throws ResolveException {
        InputStreamReader reader = new InputStreamReader(trustmarkInputStream);
        return resolve(reader);
    }

    @Override
    public TrustmarkStatusReport resolve(Reader trustmarkReader) throws ResolveException {
        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to in memory string...");
            org.apache.commons.io.IOUtils.copy(trustmarkReader, inMemoryString);
        }catch(IOException ioe){
            throw new ResolveException("An unexpected error occurred reading from the Reader: "+ioe.getMessage(), ioe);
        }
        return resolve(inMemoryString.toString());
    }

    @Override
    public TrustmarkStatusReport resolve(String utf8EncodedTrustmarkString) throws ResolveException {
        String statusReportData = utf8EncodedTrustmarkString;
        if( StringUtils.isBlank(statusReportData) )
            throw new ParseException("There is no content to parse into a TrustmarkStatusReport object.");

        log.debug("StatusReportData: \n"+statusReportData);

        TrustmarkStatusReport tsr = null;
        if( isJson(statusReportData) ){
            log.debug("Encountered JSON data, parsing...");
            tsr = TrustmarkStatusReportJsonDeserializer.deserialize(statusReportData);
        }else if( isXml(statusReportData) ){
            log.debug("Encountered XML data, parsing...");
            tsr = TrustmarkStatusReportXmlDeserializer.deserialize(statusReportData);
        }else{
            throw new ParseException("The incoming TrustmarkStatusReport is in an invalid format!  Expecting JSON or XML.");
        }

        return tsr;
    }



}//end resolve()