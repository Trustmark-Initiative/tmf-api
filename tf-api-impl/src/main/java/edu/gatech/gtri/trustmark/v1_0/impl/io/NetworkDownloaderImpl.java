package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.SessionResolver;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by brad on 12/8/15.
 */
public class NetworkDownloaderImpl implements NetworkDownloader {

    private static final Logger log = Logger.getLogger(NetworkDownloaderImpl.class);


    protected byte[] exhaustStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        int read;
        while( (read=inputStream.read(buffer)) > 0 ){
            bytesOut.write(buffer, 0, read);
        }
        return bytesOut.toByteArray();
    }

    /**
     * Takes a reader and exhausts it, returning the resulting string.
     */
    protected String exhaustStream(Reader stringReader) throws IOException {
        StringWriter stringWriter = new StringWriter();
        char[] charBuffer = new char[1024];
        int charsRead = 0;
        int currentCharsRead = 0;
        while( (currentCharsRead = stringReader.read(charBuffer)) > 0 ){
            charsRead += currentCharsRead;
            for( int i = 0; i < currentCharsRead; i++ )
                stringWriter.append(charBuffer[i]);
        }
        return stringWriter.toString();
    }//end exhaust

    /**
     * Given a URL, this method will attempt to download the content at the URL into a String.  May throw
     * IOExceptions for any errors.
     */
    public HttpResponse download(URL url) throws IOException {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            updateHttpURLConnection(urlConnection);
            HttpResponseImpl metadata = new HttpResponseImpl();
            metadata.setResponseCode(urlConnection.getResponseCode());
            metadata.setResponseMessage(urlConnection.getResponseMessage());
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            metadata.setHeaders(headers);
            metadata.setContentType(urlConnection.getHeaderField("Content-Type"));
            byte[] data = exhaustStream(urlConnection.getInputStream());
            metadata.setData(data);
            return metadata;
        }catch(FileNotFoundException fnfe){
            log.error("Caught a FileNotFoundException, most likely because the server's response code was not less than 400 (ie, 403, 404 or 500 are most common).  Please check the request from the server's logs to find out what's going on.");
            throw fnfe;
        }
    }


    protected void updateHttpURLConnection(HttpURLConnection urlConn){
        SessionResolver resolver = SessionResolver.getSessionResolver();
        if(resolver!=null){
            urlConn.setRequestProperty("Cookie", "JSESSIONID="+resolver.getSessionId());
        }
    }

}
