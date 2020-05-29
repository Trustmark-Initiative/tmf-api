package edu.gatech.gtri.trustmark.v1_0.impl.io;

import java.io.IOException;
import java.net.URL;

/**
 * Created by brad on 12/8/15.
 */
public class BinaryContentException extends IOException {

    private URL url;
    private String contentType;

    public URL getUrl() {
        return url;
    }
    public String getContentType() {
        return contentType;
    }


    public BinaryContentException(String message, URL url, String contentType) {
        super(message);
        this.url = url;
        this.contentType = contentType;
    }

}
