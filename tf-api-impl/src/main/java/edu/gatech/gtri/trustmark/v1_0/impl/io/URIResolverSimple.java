package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;

import java.net.URI;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;

/**
 * Resolves the given URI by converting the URI to a URL and downloading the
 * URL.
 *
 * @author GTRI Trustmark Team
 */
public final class URIResolverSimple extends AbstractURIResolver {

    @Override
    public String resolve(final URI uri) throws ResolveException {

        requireNonNull(uri);

        return downloadUri(uri, none());
    }

    @Override
    public String resolve(final URI uri, final String mediaTypeString) throws ResolveException {

        requireNonNull(uri);
        requireNonNull(mediaTypeString);

        return downloadUri(uri, some(mediaTypeString));
    }
}
