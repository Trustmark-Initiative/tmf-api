package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Created by brad on 12/7/15.
 */
public interface URIResolverFactory {

    /**
     * Simple implementation which does nothing fancy.  It tries to immediately resolve the URI each time.
     */
    public URIResolver createSimpleResolver();

    /**
     * This version maintains a cache of URI values, and only refreshes them from the internet if the cache has expired.
     * The timeout is based on milliseconds.
     */
    public URIResolver createTimeoutBasedCacheResolver(long timeout);

    /**
     * Creates a version of the URIResolver interface which honors the timeout information given by an HTTP service.
     */
    public URIResolver createHttpHonoringResolver();

}//end URIResolverFactory