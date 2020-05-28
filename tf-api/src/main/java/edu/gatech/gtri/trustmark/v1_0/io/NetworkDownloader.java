package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.IOException;
import java.net.URL;

/**
 * Created by brad on 12/8/15.
 */
public interface NetworkDownloader {

    /**
     * Does the operation of downloading a resource.  This abstraction is meant to provide implementations a way
     * to get around having the URI resolver perform this operation.  It also makes things easier to test.
     */
    public HttpResponse download(URL url) throws IOException;

}
