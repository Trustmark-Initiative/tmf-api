package edu.gatech.gtri.trustmark.v1_0.impl.io;

import org.gtri.fj.data.Option;
import org.gtri.fj.function.Try;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.util.Iterator;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.APPLICATION_JSON;
import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.APPLICATION_XML;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.product.P.p;

/**
 * Try the URI without the query string, first with the "application/xml" media
 * type, then with "application/json" media type, and finally the original URI.
 */
public final class URIIteratorStrategyAcceptXmlAcceptJson implements URIIteratorStrategy {

    @Override
    public Iterator<P2<URI, Option<String>>> uriIterator(final URI uri) {

        // if the given URI is valid, then this URI should be as well.
        final URI uriWithoutQuery = Try.f(() -> new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), null, uri.getFragment()))._1().toOption().some();

        return arrayList(
                p(uriWithoutQuery, some(APPLICATION_XML.getMediaType())),
                p(uriWithoutQuery, some(APPLICATION_JSON.getMediaType())),
                p(uri, Option.<String>none())).iterator();
    }
}
