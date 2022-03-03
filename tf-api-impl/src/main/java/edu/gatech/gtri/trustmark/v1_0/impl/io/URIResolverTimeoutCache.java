package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.data.Option;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.product.P.p;

/**
 * Resolves the given URI from its cache, if the timeout has not expired;
 * otherwise, it attempts to resolve the given URI by converting the URI to a
 * URL and downloading the URL.
 *
 * @author GTRI Trustmark Team
 */
public final class URIResolverTimeoutCache extends AbstractURIResolver {

    private static final long DEFAULT_TIMEOUT = 60l * 60l * 24l;

    private final long timeout;

    /**
     * Resolves the given URI from its cache, if the cache is not older than one
     * day.
     */
    public URIResolverTimeoutCache() {
        this.timeout = DEFAULT_TIMEOUT;
    }

    /**
     * Resolves the given URI from its cache, if the timeout has not expired;
     * otherwise, it attempts to resolve the given URI by converting the URI to
     * a URL and downloading the URL.
     *
     * @param timeout the timeout
     */
    public URIResolverTimeoutCache(final long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String resolve(final URI uri) throws ResolveException {

        requireNonNull(uri);

        return resolveHelper(uri, none(), timeout, this::downloadUri);
    }

    @Override
    public String resolve(final URI uri, final String mediaTypeString) throws ResolveException {

        requireNonNull(uri);
        requireNonNull(mediaTypeString);

        return resolveHelper(uri, some(mediaTypeString), timeout, this::downloadUri);
    }

    private static final Map<P2<String, Option<String>>, P2<Long, String>> URI_CACHE = new HashMap<>();

    private static synchronized String resolveHelper(final URI uri, final Option<String> mediaTypeStringOption, final long timeout, Try2<URI, Option<String>, String, ResolveException> downloadUri) throws ResolveException {

        requireNonNull(uri);
        requireNonNull(mediaTypeStringOption);
        requireNonNull(downloadUri);

        final String uriString = uri.toString();
        final long currentTimeMillis = System.currentTimeMillis();

        return URI_CACHE.containsKey(p(uriString, mediaTypeStringOption)) && currentTimeMillis - URI_CACHE.get(p(uriString, mediaTypeStringOption))._1() <= timeout ?
                URI_CACHE.get(p(uriString, mediaTypeStringOption))._2() :
                cache(uriString, mediaTypeStringOption, currentTimeMillis, downloadUri.f(uri, mediaTypeStringOption));
    }

    private static String cache(final String uriString, final Option<String> mediaTypeStringOption, final long currentTimeMillis, final String content) {

        requireNonNull(uriString);
        requireNonNull(mediaTypeStringOption);
        requireNonNull(content);

        URI_CACHE.put(p(uriString, mediaTypeStringOption), p(currentTimeMillis, content));

        return content;
    }
}
