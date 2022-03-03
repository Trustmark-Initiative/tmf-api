package edu.gatech.gtri.trustmark.v1_0.io;

import java.net.URI;

/**
 * Implementations resolve URIs.
 *
 * @author GTRI Trustmark Team
 */
public interface URIResolver {

    /**
     * Returns the document associated with the given URI.
     *
     * @param uri the URI
     * @return the document
     * @throws NullPointerException if uri is null
     * @throws ResolveException     if an exception occurs during resolution
     */
    String resolve(final URI uri) throws ResolveException;

    /**
     * Returns the document associated with the given URI and the given media
     * type.
     *
     * @param uri             the URI
     * @param mediaTypeString the media type
     * @return the document
     * @throws NullPointerException if uri is null
     * @throws NullPointerException if mediaTypeString is null
     * @throws ResolveException     if an exception occurs during resolution
     */
    default String resolve(final URI uri, final String mediaTypeString) throws ResolveException {

        return resolve(uri);
    }

    /**
     * Returns the document associated with the given URI.
     *
     * @param uriString the URI
     * @return the document
     * @throws NullPointerException if uriString is null
     * @throws ResolveException     if an exception occurs during resolution
     */
    String resolve(final String uriString) throws ResolveException;

    /**
     * Returns the document associated with the given URI and the given media
     * type.
     *
     * @param uriString       the URI
     * @param mediaTypeString the media type
     * @return the document
     * @throws NullPointerException if uriString is null
     * @throws NullPointerException if mediaTypeString is null
     * @throws ResolveException     if an exception occurs during resolution
     */
    default String resolve(final String uriString, final String mediaTypeString) throws ResolveException {

        return resolve(uriString);
    }
}
