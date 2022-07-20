package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F5;
import org.gtri.fj.function.F6;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.Try1;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.iteratorList;

public abstract class AbstractResolverFromURIResolver<T0> extends AbstractResolver<T0> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolverFromURIResolver.class);
    private final URIResolver uriResolver;
    private final URIIteratorStrategy uriIteratorStrategy;

    public AbstractResolverFromURIResolver(
            final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList,
            final Try1<T0, T0, ResolveException> validator,
            final URIResolver uriResolver,
            final URIIteratorStrategy uriIteratorStrategy) {

        super(deserializerList, validator);

        requireNonNull(uriResolver);
        requireNonNull(uriIteratorStrategy);

        this.uriResolver = uriResolver;
        this.uriIteratorStrategy = uriIteratorStrategy;
    }

    @Override
    public final T0 resolve(final URI uri, final Boolean validateNullable) throws ResolveException {

        requireNonNull(uri);

        final boolean validate = validateNullable == null ? false : validateNullable;
        final Map<URI, ResolveException> map = new HashMap<>();

        for (final P2<URI, Option<String>> uriInner : iteratorList(uriIteratorStrategy.uriIterator(uri))) {
            try {

                log.debug(validate ?
                        "Resolving and validating entity URI (" + uriInner._1() + ") ..." :
                        "Resolving entity URI (" + uriInner._1() + ") ...");

                return uriInner._2().isSome() ?
                        resolve(uriResolver.resolve(uriInner._1(), uriInner._2().some()), uriInner._1(), validate) :
                        resolve(uriResolver.resolve(uriInner._1()), uriInner._1(), validate);

            } catch (final ResolveException resolveException) {

                map.put(uriInner._1(), resolveException);
            }
        }

        throw map.getOrDefault(uri, new ResolveException(format("The system could not resolve the URI ('%s').", uri)));
    }

    @Override
    public <T1> T1 resolve(
            final URI artifactUri,
            final Boolean validateNullable,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T1> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T1> onServerFailure) {

        return Either.reduce(Try.<T0, ResolveException>f(() -> resolve(artifactUri, validateNullable))._1()
                .map(artifact -> onArtifactSuccess.f(artifactUri, artifact))
                .f().map(artifactException -> resolveContext(
                        artifactUri,
                        (contextUri) -> onContextSuccess.f(artifactUri, artifactException, contextUri),
                        (contextUri, contextException) -> resolveServer(
                                artifactUri,
                                (serverUri) -> onServerSuccess.f(artifactUri, artifactException, contextUri, contextException, serverUri),
                                (serverUri, serverException) -> onServerFailure.f(artifactUri, artifactException, contextUri, contextException, serverUri, serverException))))
                .toEither());
    }

    private <T1> T1 resolveContext(
            final URI artifactUri,
            final F1<URI, T1> onContextSuccess,
            final F2<URI, ResolveException, T1> onContextFailure) {

        if (artifactUri.getPath().split("/").length < 2) {

            return onContextFailure.f(artifactUri, new ResolveException(format("The system could not identify the context path for '%s'.", artifactUri)));
        } else {
            try {
                final URI contextUri = new URI(artifactUri.getScheme(), artifactUri.getAuthority(), "/" + artifactUri.getPath().split("/")[1], null, null);
                final HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(contextUri.toString())).openConnection();

                if (httpURLConnection.getResponseCode() == 200) {

                    return onContextSuccess.f(contextUri);

                } else {

                    return onContextFailure.f(contextUri, new ResolveException(format("%s: %s", httpURLConnection.getResponseMessage(), httpURLConnection.getResponseMessage())));
                }
            } catch (final URISyntaxException uriSyntaxException) {

                return onContextFailure.f(artifactUri, new ResolveException(uriSyntaxException));

            } catch (final MalformedURLException malformedURLException) {

                return onContextFailure.f(artifactUri, new ResolveException(malformedURLException));

            } catch (final IOException ioException) {
                return onContextFailure.f(artifactUri, new ResolveException(ioException));
            }
        }
    }

    private <T1> T1 resolveServer(
            final URI artifactUri,
            final F1<URI, T1> onServerSuccess,
            final F2<URI, ResolveException, T1> onServerFailure) {

        try {
            final URI serverUri = new URI(artifactUri.getScheme(), artifactUri.getAuthority(), null, null, null);
            final HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(serverUri.toString())).openConnection();

            if (httpURLConnection.getResponseCode() == 200) {

                return onServerSuccess.f(serverUri);

            } else {

                return onServerFailure.f(serverUri, new ResolveException(format("%s: %s", httpURLConnection.getResponseMessage(), httpURLConnection.getResponseMessage())));
            }
        } catch (final URISyntaxException uriSyntaxException) {

            return onServerFailure.f(artifactUri, new ResolveException(uriSyntaxException));

        } catch (final MalformedURLException malformedURLException) {

            return onServerFailure.f(artifactUri, new ResolveException(malformedURLException));

        } catch (final IOException ioException) {

            return onServerFailure.f(artifactUri, new ResolveException(ioException));
        }
    }
}
