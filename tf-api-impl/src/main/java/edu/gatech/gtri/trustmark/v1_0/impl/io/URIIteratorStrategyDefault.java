package edu.gatech.gtri.trustmark.v1_0.impl.io;

import org.gtri.fj.data.Option;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.util.Iterator;

import static org.gtri.fj.data.List.single;
import static org.gtri.fj.product.P.p;

/**
 * Try the given URI.
 */
public final class URIIteratorStrategyDefault implements URIIteratorStrategy {

    @Override
    public Iterator<P2<URI, Option<String>>> uriIterator(final URI uri) {

        return single(p(uri, Option.<String>none())).iterator();
    }
}
