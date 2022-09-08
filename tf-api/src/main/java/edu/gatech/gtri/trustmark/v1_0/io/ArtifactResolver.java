package edu.gatech.gtri.trustmark.v1_0.io;

import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F4;
import org.gtri.fj.function.F5;
import org.gtri.fj.function.F6;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * Implementations resolve an artifact of the given type.
 *
 * @param <T1> the type of the artifact
 */
public interface ArtifactResolver<T1> {

    /**
     * Parses the artifact from the given URL, without doing any validation.
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param url               the url
     * @param onArtifactSuccess the artifact success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URL url,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URL, and validates according to the
     * validate parameter (true = validate, null or false = do not validate)
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param url               the url
     * @param onArtifactSuccess the artifact success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URL url,
            final Boolean validate,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onServerSuccess,
            final F4<URL, ResolveException, URL, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URL, and validates according to the
     * validate parameter (true = validate, null or false = do not validate)
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param url               the url
     * @param onArtifactSuccess the artifact success function
     * @param onContextSuccess  the context success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URL url,
            final Boolean validate,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onContextSuccess,
            final F5<URL, ResolveException, URL, ResolveException, URL, T2> onServerSuccess,
            final F6<URL, ResolveException, URL, ResolveException, URL, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URL, without doing any validation.
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param url               the url
     * @param onArtifactSuccess the artifact success function
     * @param onContextSuccess  the context success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URL url,
            final F2<URL, T1, T2> onArtifactSuccess,
            final F3<URL, ResolveException, URL, T2> onContextSuccess,
            final F5<URL, ResolveException, URL, ResolveException, URL, T2> onServerSuccess,
            final F6<URL, ResolveException, URL, ResolveException, URL, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URI, without doing any validation.
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param uri               the uri
     * @param onArtifactSuccess the artifact success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URI uri,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URI, and validates according to the
     * validate parameter (true = validate, null or false = do not validate)
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URL of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URL of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param uri               the uri
     * @param onArtifactSuccess the artifact success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URI uri,
            final Boolean validate,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onServerSuccess,
            final F4<URI, ResolveException, URI, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URI, and validates according to the
     * validate parameter (true = validate, null or false = do not validate)
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URI of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URI of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param uri               the uri
     * @param onArtifactSuccess the artifact success function
     * @param onContextSuccess  the context success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URI uri,
            final Boolean validate,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T2> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URI, without doing any validation.
     *
     * <ul>
     * <li>On success, invokes onArtifactSuccess with the artifact.</li>
     * <li>On failure, attempts to resolve the server:
     * <ul>
     * <li>On success, invokes onServerSuccess with the URI of the artifact, the exception from that URL, and the URL of the server;</li>
     * <li>On failure, invokes onServerFailure with the URI of the artifact, the exception from that URL, the URL of the server, and the exception from that server.</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param uri               the uri
     * @param onArtifactSuccess the artifact success function
     * @param onContextSuccess  the context success function
     * @param onServerSuccess   the server success function
     * @param onServerFailure   the server failure function
     * @param <T2>              the type of the output of the function
     * @return the output of the function
     */
    <T2> T2 resolve(
            final URI uri,
            final F2<URI, T1, T2> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T2> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T2> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T2> onServerFailure);

    /**
     * Parses the artifact from the given URL, without doing any validation.
     *
     * @param url the url
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final URL url) throws ResolveException;

    /**
     * Parses the artifact from the given URL, and validates according to the
     * validate parameter (true = validate, null or false = do not validate).
     *
     * @param url      the url
     * @param validate whether to validate (true = validate, null or false = do
     *                 not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final URL url, final Boolean validate) throws ResolveException;

    /**
     * Parses the artifact from the given URI, without doing any validation.
     *
     * @param uri the uri
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final URI uri) throws ResolveException;

    /**
     * Parses the artifact from the given URI, and validates according to the
     * validate parameter (true = validate, null or false = do not validate).
     *
     * @param uri      the uri
     * @param validate whether to validate (true = validate, null or false = do
     *                 not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final URI uri, final Boolean validate) throws ResolveException;

    /**
     * Parses the artifact from the given File, without doing any validation.
     *
     * @param file the file
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final File file) throws ResolveException;

    /**
     * Parses the artifact from the given File, and validates according to the
     * validate parameter (true = validate, null or false = do not validate).
     *
     * @param file     the file
     * @param validate whether to validate (true = validate, null or false = do
     *                 not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final File file, final Boolean validate) throws ResolveException;

    /**
     * Parses the artifact from the given InputStream, without doing any
     * validation.
     *
     * @param inputStream the inputStream
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final InputStream inputStream) throws ResolveException;

    /**
     * Parses the artifact from the given InputStream, and validates according
     * to the validate parameter (true = validate, null or false = do not
     * validate).
     *
     * @param inputStream the inputStream
     * @param validate    whether to validate (true = validate, null or false =
     *                    do not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final InputStream inputStream, final Boolean validate) throws ResolveException;

    /**
     * Parses the artifact from the given Reader, without doing any validation.
     *
     * @param reader the reader
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final Reader reader) throws ResolveException;

    /**
     * Parses the artifact from the given Reader, and validates according to the
     * validate parameter (true = validate, null or false = do not validate).
     *
     * @param reader   the reader
     * @param validate whether to validate (true = validate, null or false = do
     *                 not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final Reader reader, final Boolean validate) throws ResolveException;

    /**
     * Parses the artifact from the given raw String data, without doing any
     * validation.
     *
     * @param string the string
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final String string) throws ResolveException;

    /**
     * Parses the artifact from the given raw String data, and validates
     * according to the validate parameter (true = validate, null or false = do
     * not validate).
     *
     * @param string   the string
     * @param validate whether to validate (true = validate, null or false = do
     *                 not validate)
     * @return the artifact
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    T1 resolve(final String string, final Boolean validate) throws ResolveException;
}
