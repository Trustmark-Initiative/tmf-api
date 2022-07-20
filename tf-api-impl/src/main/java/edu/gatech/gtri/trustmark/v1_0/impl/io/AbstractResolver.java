package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.apache.commons.io.IOUtils;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;
import org.gtri.fj.function.F5;
import org.gtri.fj.function.F6;
import org.gtri.fj.function.Try;
import org.gtri.fj.function.Try1;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Either.reduce;

public abstract class AbstractResolver<T0> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolver.class);

    private final Map<String, WeakReference<T0>> map = new HashMap<>();
    private final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList;

    private final Try1<T0, T0, ResolveException> validator;

    public AbstractResolver(
            final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList,
            final Try1<T0, T0, ResolveException> validator) {

        deserializerList.forEach(p -> {
            requireNonNull(p);
            requireNonNull(p._1());
            requireNonNull(p._2());
        });
        requireNonNull(validator);

        this.deserializerList = deserializerList;
        this.validator = validator;
    }

    public <T1> T1 resolve(
            final URL url,
            final F2<URL, T0, T1> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T1> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T1> onServerFailure) {

        requireNonNull(url);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(url, false, onArtifactSuccess, onServerSuccess, onServerFailure);
    }

    public <T1> T1 resolve(
            final URL url,
            final Boolean validate,
            final F2<URL, T0, T1> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T1> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T1> onServerFailure) {

        requireNonNull(url);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(
                url,
                validate,
                onArtifactSuccess,
                onServerSuccess,
                (artifactUrl, artifactException, contextUrl, contextException, serverUrl) -> onServerSuccess.f(artifactUrl, artifactException, serverUrl),
                (artifactUrl, artifactException, contextUrl, contextException, serverUrl, serverException) -> onServerFailure.f(artifactUrl, artifactException, serverUrl, serverException));
    }

    public <T1> T1 resolve(
            final URL url,
            final F2<URL, T0, T1> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T1> onContextSuccess,
            final F5<URL, ResolveException, URL, ResolveException, URL, T1> onServerSuccess,
            final F6<URL, ResolveException, URL, ResolveException, URL, ResolveException, T1> onServerFailure) {

        requireNonNull(url);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onContextSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(url, false, onArtifactSuccess, onContextSuccess, onServerSuccess, onServerFailure);
    }

    public <T1> T1 resolve(
            final URL url,
            final Boolean validate,
            final F2<URL, T0, T1> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T1> onContextSuccess,
            final F5<URL, ResolveException, URL, ResolveException, URL, T1> onServerSuccess,
            final F6<URL, ResolveException, URL, ResolveException, URL, ResolveException, T1> onServerFailure) {

        requireNonNull(url);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        try {
            return reduce(resolve(
                    url.toURI(),
                    validate,
                    (artifactUri, artifact) -> Try.f(() -> onArtifactSuccess.f(new URL(artifactUri.toString()), artifact))._1(),
                    (artifactUri, artifactException, contextUri) -> Try.f(() -> onContextSuccess.f(new URL(artifactUri.toString()), artifactException, new URL(contextUri.toString())))._1(),
                    (artifactUri, artifactException, contextUri, contextException, serverUri) -> Try.f(() -> onServerSuccess.f(new URL(artifactUri.toString()), artifactException, new URL(contextUri.toString()), contextException, new URL(serverUri.toString())))._1(),
                    (artifactUri, artifactException, contextUri, contextException, serverUri, serverException) -> Try.f(() -> onServerFailure.f(new URL(artifactUri.toString()), artifactException, new URL(contextUri.toString()), contextException, new URL(serverUri.toString()), serverException))._1())
                    .f().map(malformedURLException -> onServerFailure.f(url, new ResolveException(malformedURLException), url, new ResolveException(malformedURLException), url, new ResolveException(malformedURLException)))
                    .toEither());
        } catch (final URISyntaxException uriSyntaxException) {
            return onServerFailure.f(url, new ResolveException(uriSyntaxException), url, new ResolveException(uriSyntaxException), url, new ResolveException(uriSyntaxException));
        }
    }

    public <T1> T1 resolve(
            final URI uri,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T1> onServerFailure) {

        requireNonNull(uri);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(
                uri,
                false,
                onArtifactSuccess,
                onServerSuccess,
                (artifactUri, artifactException, contextUri, contextException, serverUri) -> onServerSuccess.f(artifactUri, artifactException, serverUri),
                (artifactUri, artifactException, contextUri, contextException, serverUri, serverException) -> onServerFailure.f(artifactUri, artifactException, serverUri, serverException));
    }

    public <T1> T1 resolve(
            final URI uri,
            final Boolean validate,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T1> onServerFailure) {

        requireNonNull(uri);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(
                uri,
                validate,
                onArtifactSuccess,
                onServerSuccess,
                (artifactUri, artifactException, contextUri, contextException, serverUri) -> onServerSuccess.f(artifactUri, artifactException, serverUri),
                (artifactUri, artifactException, contextUri, contextException, serverUri, serverException) -> onServerFailure.f(artifactUri, artifactException, serverUri, serverException));
    }

    public <T1> T1 resolve(
            final URI uri,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T1> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T1> onServerFailure) {

        requireNonNull(uri);
        requireNonNull(onArtifactSuccess);
        requireNonNull(onContextSuccess);
        requireNonNull(onServerSuccess);
        requireNonNull(onServerFailure);

        return resolve(uri, false, onArtifactSuccess, onContextSuccess, onServerSuccess, onServerFailure);
    }

    public final T0 resolve(final URL url) throws ResolveException {
        requireNonNull(url);

        return resolve(url, false);
    }

    public final T0 resolve(final URL url, final Boolean validate) throws
            ResolveException {
        requireNonNull(url);

        try {
            return resolve(url.toURI(), validate);
        } catch (URISyntaxException uriSyntaxException) {
            throw new ResolveException(uriSyntaxException);
        }
    }

    public final T0 resolve(final URI uri) throws ResolveException {
        requireNonNull(uri);

        return resolve(uri, false);
    }

    public final T0 resolve(final File file) throws ResolveException {
        requireNonNull(file);

        return resolve(file, false);
    }

    public final T0 resolve(final File file, final Boolean validate) throws
            ResolveException {
        requireNonNull(file);

        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (IOException ioException) {
            throw new ResolveException(ioException);
        }
        return resolve(reader, validate);
    }

    public final T0 resolve(final InputStream inputStream) throws
            ResolveException {
        requireNonNull(inputStream);

        return resolve(inputStream, false);
    }

    public final T0 resolve(final InputStream inputStream,
                            final Boolean validate) throws ResolveException {
        requireNonNull(inputStream);

        return resolve(new InputStreamReader(inputStream), validate);
    }

    public final T0 resolve(final Reader reader) throws ResolveException {
        requireNonNull(reader);

        return resolve(reader, false);
    }

    public final T0 resolve(final Reader reader, final Boolean validate) throws
            ResolveException {
        requireNonNull(reader);

        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to string ...");
            IOUtils.copy(reader, inMemoryString);
            reader.close();
        } catch (IOException ioException) {
            throw new ResolveException(ioException);
        }
        return resolve(inMemoryString.toString(), validate);
    }

    public final T0 resolve(final String string) throws ResolveException {
        requireNonNull(string);

        return resolve(string, false);
    }

    public final T0 resolve(final String string,
                            final Boolean validateNullable) throws ResolveException {
        requireNonNull(string);

        return resolve(string, null, validateNullable);
    }

    public final T0 resolve(final String string, final URI uri, final Boolean validateNullable) throws ResolveException {
        try {
            requireNonNull(string);

            final boolean validate = validateNullable == null ? false : validateNullable;

            log.debug(validate ?
                    "Reading and validating an entity from a string ..." :
                    "Reading an entity from a string ...");

            if (log.isDebugEnabled())
                log.debug(string);

            // TODO: Remove inappropriate use of DatatypeConverter outside of XML context.
            final String key = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8)));

            if (!map.containsKey(key) || map.get(key).get() == null) {
                T0 entity = null;

                for (P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>> p : deserializerList.toCollection()) {
                    if (p._1().f(string)) {
                        log.debug("The system could deserialize the string.");
                        entity = p._2().f(string, uri);
                        break;
                    }
                }

                if (entity == null) {
                    throw new ParseException("The system could not deserialize the string.");
                }

                if (validate) {
                    validator.f(entity);
                }

                map.put(key, new WeakReference<>(entity));
            }

            return map.get(key).get();

        } catch (final NoSuchAlgorithmException noSuchAlgorithmException) {

            throw new ResolveException(noSuchAlgorithmException);
        }
    }

    public abstract T0 resolve(
            final URI uri,
            final Boolean validate) throws ResolveException;

    public abstract <T1> T1 resolve(
            final URI uri,
            final Boolean validate,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T1> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T1> onServerFailure);
}
