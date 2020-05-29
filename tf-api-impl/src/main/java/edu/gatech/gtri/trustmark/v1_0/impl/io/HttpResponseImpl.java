package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brad on 12/8/15.
 */
public class HttpResponseImpl implements HttpResponse {

    private Map<String, List<String>> headers = new HashMap<>();
    private String contentType;
    private int responseCode;
    private String responseMessage;
    private byte[] data;

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    @Override
    public List<String> getHeader(String name) {
        return this.getHeaders().get(name.toLowerCase());
    }


    @Override
    public boolean isBinary() {
        return !isText();
    }
    public boolean isText() {
        if( contentType != null ){
            return contentType.startsWith("text/") || contentType.contains("json") || contentType.contains("xml");
        }else{
            return false; // We don't know what it is.  This should be a danger sign.
        }
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
