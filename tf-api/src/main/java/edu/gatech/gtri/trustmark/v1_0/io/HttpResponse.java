package edu.gatech.gtri.trustmark.v1_0.io;

import java.util.List;
import java.util.Map;

/**
 * Describes metadata available from the HTTP process.
 */
public interface HttpResponse {

    public List<String> getHeader(String name);

    public Map<String, List<String>> getHeaders();

    public int getResponseCode();

    public String getResponseMessage();

    public String getContentType();

    public boolean isBinary();

    public String getContent();

    public byte[] getBinaryContent();

}

