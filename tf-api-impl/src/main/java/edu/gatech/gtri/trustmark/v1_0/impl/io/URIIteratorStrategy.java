package edu.gatech.gtri.trustmark.v1_0.impl.io;

import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.util.Iterator;

/**
 * Implementations are strategies to resolve URIs.
 */
public interface URIIteratorStrategy {

    /**
     * Return an iterator of URIs to attempt to resolve from the given URI.
     *
     * @param uri the URI
     * @return an iterator of URIs and optional media types to attempt to
     * resolve.
     */
    Iterator<P2<URI, Option<String>>> uriIterator(final URI uri);
}
