package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.SessionResolver;
import org.gtri.fj.data.Option;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;

/**
 * Returns the response produced from the request for the given URL.
 *
 * @author GTRI Trustmark Team
 */
public final class NetworkDownloaderImpl implements NetworkDownloader {

    public HttpResponse download(final URL url) throws IOException {

        requireNonNull(url);

        return download(url, none());
    }

    public HttpResponse download(final URL url, final String accept) throws IOException {

        requireNonNull(url);
        requireNonNull(accept);

        return download(url, some(accept));
    }

    private HttpResponse download(final URL url, final Option<String> acceptOption) throws IOException {

        requireNonNull(url);
        requireNonNull(acceptOption);

        final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        acceptOption.forEach(accept -> httpURLConnection.setRequestProperty("Accept", accept));

        // if the session resolver exists, set the JSESSIONID
        final SessionResolver sessionResolver = SessionResolver.getSessionResolver();
        if (sessionResolver != null) {
            httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=" + sessionResolver.getSessionId());
        }

        final HttpResponseImpl httpResponse = new HttpResponseImpl(
                httpURLConnection.getHeaderFields(),
                httpURLConnection.getHeaderField("Content-Type"),
                httpURLConnection.getResponseCode(),
                httpURLConnection.getResponseMessage(),
                toByteArray(httpURLConnection.getInputStream()));

        return httpResponse;
    }
}
