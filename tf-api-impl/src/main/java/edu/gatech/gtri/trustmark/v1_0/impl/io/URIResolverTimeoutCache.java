package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brad on 12/8/15.
 */
public class URIResolverTimeoutCache extends AbstractURIResolver {

    private static final Logger log = LoggerFactory.getLogger(URIResolverTimeoutCache.class);

    public static final long DEFAULT_TIMEOUT = 60l * 60l * 24l;  // one day in millis

    private long timeout = DEFAULT_TIMEOUT;

    /**
     * used to synchronize the cache.
     */
    private static Boolean CACHE_LOCK = true;
    public static Map<String, String> URI_CACHE = new HashMap<String, String>();
    public static Map<String, Long> URI_TIME_CACHE = new HashMap<String, Long>();


    public URIResolverTimeoutCache(long timeout){
        this.timeout = timeout;
    }

    @Override
    public String resolve(URI uri) throws ResolveException {
        String uriString = uri.toString();
        boolean timedOut = false;
        String value = null;
        synchronized (CACHE_LOCK){
            if (URI_TIME_CACHE.containsKey(uriString)) {
                long cacheTime = URI_TIME_CACHE.get(uriString);
                if( (System.currentTimeMillis() - cacheTime) > this.timeout ){
                    timedOut = true;
                }else{
                    value = URI_CACHE.get(uriString);
                }
            }else{
                timedOut = true;
            }
            if( timedOut ){
                URL url = null;
                try{ url = uri.toURL(); }catch(MalformedURLException murle){throw new ResolveException("Could not convert URI to URL", murle); }
                try {
                    log.debug("Downloading URL: "+url.toExternalForm());
                    value = this.downloadUrl(url);
                }catch(IOException ioe){
                    throw new ResolveException("Error while downloading URL: "+url.toExternalForm(), ioe);
                }
                URI_TIME_CACHE.put(uriString, System.currentTimeMillis());
                URI_CACHE.put(uriString, value);
            }
        }
        return value;
    }//end resolve()


}//end URIResolver
