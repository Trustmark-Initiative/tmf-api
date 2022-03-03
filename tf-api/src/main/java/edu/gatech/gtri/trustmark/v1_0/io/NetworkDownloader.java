package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

/**
 * Implementations return the response produced from the request for the given
 * URL.
 *
 * TODO: Move to impl; currently in use in the assessment tool, but we could substitute a plain URLConnection for that use.
 *
 * @author GTRI Trustmark Team
 */
public interface NetworkDownloader {

    /**
     * Returns the response produced from the request for the given URL.
     *
     * @param url the URL
     * @return the response produced
     * @throws NullPointerException if url is null
     * @throws IOException          if an error occurs when requesting the URL
     */
    HttpResponse download(final URL url) throws IOException;

    /**
     * Returns the response produced from the request for the given URL and the
     * given accept header value.
     *
     * @param url    the URL
     * @param accept the accept header value
     * @return the response produced
     * @throws NullPointerException if url is null
     * @throws IOException          if an error occurs when requesting the URL
     */
    default HttpResponse download(final URL url, final String accept) throws IOException {

        requireNonNull(url);

        return download(url);
    }
}
