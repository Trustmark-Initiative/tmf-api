package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Implementations construct URI resolvers.
 *
 * TODO: Remove; no implementations.
 *
 * @author GTRI Trustmark Team
 */
public interface URIResolverFactory {

    /**
     * Returns a URI resolver that attempts to resolve a given URI.
     *
     * @return the URI resolver
     */
    URIResolver createSimpleResolver();

    /**
     * Returns a URI resolver that attempts to resolve a given URI from its
     * cache, if the timeout has not expired; otherwise, it attempts to resolve
     * the given URI as usual.
     *
     * @param timeout the timeout in milliseconds
     * @return the URI resolver
     */
    URIResolver createTimeoutBasedCacheResolver(final long timeout);

    /**
     * Returns a URI resolver that attempts to resolve a given URI, respecting
     * the Cache-Control header, if relevant.
     *
     * @return the URI resolver
     */
    URIResolver createHttpHonoringResolver();
}
