package edu.gatech.gtri.trustmark.v1_0.io;

import java.util.List;
import java.util.Map;

/**
 * Implementations represent an HTTP response.
 *
 * TODO: Move to impl; currently in use in the assessment tool, but we could substitute a plain URLConnection for that use.
 *
 * @author GTRI Trustmark Team
 */
public interface HttpResponse {

    /**
     * Returns the value of the header with the given name.
     *
     * @param name the name
     * @return the value of the header with the name
     */
    List<String> getHeader(String name);

    /**
     * Returns the headers.
     *
     * @return the headers
     */
    Map<String, List<String>> getHeaders();

    /**
     * Returns the response code.
     *
     * @return the response code
     */
    int getResponseCode();

    /**
     * Returns the response message.
     *
     * @return the response message
     */
    String getResponseMessage();

    /**
     * Returns the value of the Content-Type header.
     *
     * @return the value of the Content-Type header
     */
    String getContentType();

    /**
     * Returns true if the value of the Content-Type header is null, or if the
     * value of the Content-Type header is not null, does not start with
     * "text/", does not contain "json", and does not contain "xml"; false
     * otherwise.
     *
     * @return true if the value of the Content-Type header is null, or if the
     * value of the Content-Type header is not null, does not start with
     * "text/", does not contain "json", and does not contain "xml"; false
     * otherwise
     */
    boolean isBinary();

    /**
     * Returns the string representation of the content.
     *
     * @return the string representation of the content
     */
    String getContent();

    /**
     * Returns the content.
     *
     * @return the content
     */
    byte[] getBinaryContent();

    default  void setHeaders(final Map<String, List<String>> headers) {

    }

    default  void setContentType(final String contentType) {

    }

    default  void setResponseCode(final int responseCode) {

    }

    default  void setResponseMessage(final String responseMessage) {

    }

    default  void setData(final byte[] data) {

    }
}

