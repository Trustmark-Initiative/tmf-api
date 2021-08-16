package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.*;

/**
 * Created by brad on 12/8/15.
 */
public abstract class AbstractURIResolver implements URIResolver {

    private static final Logger log = LogManager.getLogger(URIResolverSimple.class);

    @Override
    public String resolve(String uriString) throws ResolveException {
        try {
            return resolve(new URI(uriString));
        }catch(URISyntaxException uriSyntaxException){
            log.warn("Could not resolve invalid URI: "+uriString);
            throw new ResolveException("Not a valid URI["+uriString+"]", uriSyntaxException);
        }
    }


    /**
     * Given a URL, this method will attempt to download the content at the URL into a String.  May throw
     * IOExceptions for any errors.
     */
    protected String downloadUrl(URL url) throws IOException {
        if( url.getProtocol().equalsIgnoreCase("http") || url.getProtocol().equalsIgnoreCase("https") ){
            NetworkDownloader networkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);
            if( networkDownloader == null ){
                log.error("No NetworkDownloader instance is registered in the system.  Please call FactoryLoader.register() and place your implementation.");
                throw new UnsupportedOperationException("Missing NetworkDownloader in FactoryLoader, please load appropriately before calling this method again.");
            }
            HttpResponse response = networkDownloader.download(url);
            if( response.isBinary() ){
                throw new BinaryContentException("Cannot download binary content from URL: "+url, url, response.getContentType());
            }else{
                return response.getContent();
            }
        }else{
            throw new UnsupportedOperationException("Only HTTP/HTTPS is supported as a URL protocol right now.");
        }
    }

}
