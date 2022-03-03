package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Implementations represent an HTTP response.
 *
 * @author GTRI Trustmark Team
 */
public final class HttpResponseImpl implements HttpResponse {

    private Map<String, List<String>> headers;
    private String contentType;
    private int responseCode;
    private String responseMessage;
    private byte[] data;

    public HttpResponseImpl(
            final Map<String, List<String>> headers,
            final String contentType,
            final int responseCode,
            final String responseMessage,
            final byte[] data) {

        requireNonNull(headers);
        requireNonNull(data);

        this.headers = headers;
        this.contentType = contentType;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, List<String>> headers) {

        requireNonNull(headers);

        this.headers = headers;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(final String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public List<String> getHeader(String name) {
        return this.getHeaders().get(name);
    }

    @Override
    public boolean isBinary() {
        return !isText();
    }

    public boolean isText() {
        return contentType != null && (contentType.startsWith("text/") || contentType.contains("json") || contentType.contains("xml"));
    }

    public byte[] getData() {
        return data;
    }

    public void setData(final byte[] data) {

        requireNonNull(data);

        this.data = data;
    }

    @Override
    public String getContent() {
        return new String(data);
    }

    @Override
    public byte[] getBinaryContent() {
        return data;
    }
}
