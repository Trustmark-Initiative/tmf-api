package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by brad on 2/5/16.
 */
public class IOUtils {
    public static final Logger log = LogManager.getLogger(IOUtils.class);

    /**
     * Reads the next amount of characters from the input stream.
     */
    public static String readNextNChars(Reader reader, int n) throws IOException {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        int nextChar = -1;
        while( (count < n) && ((nextChar=reader.read()) >= 0) ){
            builder.append((char) nextChar);
            count++;
        }
        return builder.toString();
    }


    /**
     * In this case, we are reading something like "blah" and expecting to return blah.
     */
    public static String readNextDoubleQuotedValue(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean hitFirstDoubleQuote = false;
        int nextChar = -1;
        while( ((nextChar=reader.read()) >= 0) ){
            char next = (char) nextChar;
            if( next == '"' ) {
                if( !hitFirstDoubleQuote ) {
                    hitFirstDoubleQuote = true;
                }else{
                    return builder.toString();
                }
            }else if( hitFirstDoubleQuote ){
                builder.append(next);
            }
        }
        // Uh-oh!  We hit the EOF and didn't encounter enough double quotes!
        throw new IOException("Could not find double quoted value in string, cannot return!");
    }


    public static String readFirstBits(File file) throws IOException {
        int toRead = 5096; // Number of characters to read...
        StringBuilder data = new StringBuilder();
        FileReader reader = new FileReader(file);
        char[] buffer = new char[1024];
        int readTotal = 0;
        int readCurrent = -1;
        while( (readCurrent=reader.read(buffer)) > -1 ){
            for( int i = 0; (i < readCurrent) && ( readTotal < toRead); i++ ) {
                data.append(buffer[i]);
                readTotal++;
            }
            if( readTotal >= toRead )
                break;
        }
        return data.toString();
    }

    public static JSONObject fetchJSON(String urlString) throws RemoteException {
        URL url = null;
        try {
            url = new URL(urlString);
        }catch(MalformedURLException me){
            throw new UnsupportedOperationException("URL["+urlString+"] is not a valid URL", me);
        }
        return fetchJSON(url);
    }


    public static JSONObject fetchJSON(URL url) throws RemoteException {
        NetworkDownloader nd = resolveNetworkDownloader();
        HttpResponse httpResponse = null;
        try {
            log.debug("Resolving content from URL: "+url);
            httpResponse = nd.download(url);
        }catch(IOException ioe){
            throw new RemoteException("An error occurred while downloading the URL: "+url.toString(), ioe);
        }
        if( httpResponse.getResponseCode() == 200 ){
            log.info("Successfully received 200 response from URL: "+url);
            String response = httpResponse.getContent();
            try{
                log.debug("Parsing JSON: \n"+response);
                return new JSONObject(response);
            }catch(JSONException jsone){
                throw new RemoteException("Expecting valid JSON response from server, but it was not.", jsone);
            }
        }else{
            log.error("Unable to download url["+url+"], received non-200 response: "+httpResponse.getResponseCode()+" - "+httpResponse.getResponseMessage());
            throw new RemoteException("Invalid Response Code["+httpResponse.getResponseCode()+"] when fetching URL: "+url);
        }
    }


    public static NetworkDownloader resolveNetworkDownloader(){
        NetworkDownloader downloader = FactoryLoader.getInstance(NetworkDownloader.class);
        if( downloader == null ) throw new NullPointerException("Missing a "+NetworkDownloader.class.getName()+" implementation, network activity not possible.");
        return downloader;
    }


    public static void writeToFile(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.flush();
        fileWriter.close();
    }

}
