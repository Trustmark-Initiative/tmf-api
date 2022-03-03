package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.gtri.fj.data.Option;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Implementations resolve URIs by converting the URI to a URL and downloading
 * the URL.
 *
 * @author GTRI Trustmark Team
 */
public abstract class AbstractURIResolver implements URIResolver {

    @Override
    public final String resolve(final String uriString) throws ResolveException {

        requireNonNull(uriString);

        try {
            return resolve(new URI(uriString));

        } catch (final URISyntaxException uriSyntaxException) {

            throw new ResolveException(format("The system could not convert the given uri string ('%s') to a URI: %s", uriString, uriSyntaxException.getMessage()), uriSyntaxException);
        }
    }

    @Override
    public final String resolve(final String uriString, final String mediaTypeString) throws ResolveException {

        requireNonNull(uriString);
        requireNonNull(mediaTypeString);

        try {
            return resolve(new URI(uriString), mediaTypeString);

        } catch (final URISyntaxException uriSyntaxException) {

            throw new ResolveException(format("The system could not convert the given uri string ('%s') and media type string ('%s') to a URI: %s", uriString, mediaTypeString, uriSyntaxException.getMessage()), uriSyntaxException);
        }
    }

    /**
     * Returns the document associated with the given URI and the given media
     * type.
     *
     * @param uri                   the URI
     * @param mediaTypeStringOption the media type string
     * @return the document
     * @throws NullPointerException if the uri is null
     * @throws NullPointerException if the mediaTypeOption is null
     * @throws ResolveException     if the system could not convert the given
     *                              URI to a URL.
     * @throws ResolveException     if the system could not instantiate
     *                              NetworkDownloader.class
     * @throws ResolveException     if the URL protocol is not HTTP or HTTPS
     * @throws ResolveException     if the system could not download the
     *                              document associated with the URL
     * @throws ResolveException     if the document associated with the URL is
     *                              binary
     */
    public final String downloadUri(final URI uri, final Option<String> mediaTypeStringOption) throws ResolveException {

        requireNonNull(uri);
        requireNonNull(mediaTypeStringOption);

        try {
            return downloadUrl(uri.toURL(), mediaTypeStringOption);
        } catch (final MalformedURLException malformedURLException) {
            throw new ResolveException(format("The system could not convert the given URI ('%s') and given media type ('%s') to a URL: %s", uri, mediaTypeStringOption.toNull(), malformedURLException.getMessage(), malformedURLException));
        } catch (final IOException ioException) {
            throw new ResolveException(format("The system could not resolve the given URI ('%s') and given media type ('%s'): %s", uri, mediaTypeStringOption.toNull(), ioException.getMessage()), ioException);
        }
    }

    /**
     * Returns the document associated with the given URL and the given media
     * type.
     *
     * @param url                   the URL
     * @param mediaTypeStringOption the media type string
     * @return the document
     * @throws NullPointerException if the url is null
     * @throws NullPointerException if the mediaTypeOption is null
     * @throws IOException          if the system could not instantiate
     *                              NetworkDownloader.class
     * @throws IOException          if the URL protocol is not HTTP or HTTPS
     * @throws IOException          if the system could not download the
     *                              document associated with the URL
     * @throws IOException          if the document associated with the URL is
     *                              binary
     */
    public final String downloadUrl(final URL url, final Option<String> mediaTypeStringOption) throws IOException {

        requireNonNull(url);
        requireNonNull(mediaTypeStringOption);

        final String protocolHttp = "http";
        final String protocolHttps = "https";

        if (url.getProtocol().equalsIgnoreCase(protocolHttp) || url.getProtocol().equalsIgnoreCase(protocolHttps)) {

            final HttpResponse httpResponse = downloadUrlHelper(url, mediaTypeStringOption);

            if (!httpResponse.isBinary()) {

                return httpResponse.getContent();

            } else {

                throw new BinaryContentException(format("The system could not convert the content at the given URL ('%s') and media type string ('%s') to a document; the content was binary.", url, mediaTypeStringOption.toNull()), url, httpResponse.getContentType());
            }

        } else {

            throw new IOException(format("The system could not download the document at the given URL ('%s') and media type string ('%s'); the protocol ('%s') is not '%s' or '%s'.", url, mediaTypeStringOption.toNull(), url.getProtocol(), protocolHttp, protocolHttps));
        }
    }

    private HttpResponse downloadUrlHelper(
            final URL url,
            final Option<String> mediaTypeStringOption) throws IOException {

        final NetworkDownloader networkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);

        if (networkDownloader != null) {

            final HttpResponse httpResponse = mediaTypeStringOption.isSome() ?
                    networkDownloader.download(url, mediaTypeStringOption.some()) :
                    networkDownloader.download(url);

            if (httpResponse.getResponseCode() == 302) {

                return mediaTypeStringOption.isSome() ?
                        networkDownloader.download(new URL(httpResponse.getHeader("Location").get(0)), mediaTypeStringOption.some()) :
                        networkDownloader.download(new URL(httpResponse.getHeader("Location").get(0)));
            } else {

                return httpResponse;
            }
        } else {

            throw new IOException(format("The system could not instantiate '%s'.", NetworkDownloader.class));
        }
    }
}
