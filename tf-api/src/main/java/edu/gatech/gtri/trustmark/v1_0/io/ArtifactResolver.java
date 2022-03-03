package edu.gatech.gtri.trustmark.v1_0.io;

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
