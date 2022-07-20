package edu.gatech.gtri.trustmark.v1_0.util;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 5/9/17
 */
public class UrlUtils {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================

    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================

    /**
     * Walks the given string (which is a URL) and returns true if the URL string is already encoded (
     * @param urlString
     * @return
     */
    public static boolean isEncoded(String urlString) {
        try {
            String decoded = URLDecoder.decode(urlString.replace("+", " "), "UTF-8");
            return !urlString.equals(decoded);
        }catch(Throwable t){
            throw new RuntimeException(t);
        }
    }


    // Props to: http://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection
    public static Map<String, List<String>> splitQuery(URL url) {
        try {
            final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
            if( url.getQuery() != null ) {
                final String[] pairs = url.getQuery().split("&");
                for (String pair : pairs) {
                    final int idx = pair.indexOf("=");
                    final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                    if (!query_pairs.containsKey(key)) {
                        query_pairs.put(key, new LinkedList<>());
                    }
                    final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                    query_pairs.get(key).add(value);
                }
            }
            return query_pairs;
        }catch(UnsupportedEncodingException uee){
            throw new RuntimeException("UTF-8 is not a supported encoding??  WHAT THE HELL IS THIS?", uee);
        }
    }//end splitQuery()


    /**
     * Formats the given Map of parameters as a String (URL Encoded).
     * @param data
     * @return
     */
    public static String toQueryString(Map<String, ? extends Collection<String>> data) {
        try {
            StringBuilder builder = new StringBuilder();
            Iterator<String> paramIterator = data.keySet().iterator();
            while (paramIterator.hasNext()) {
                String ueStringName = paramIterator.next();
                Iterator<String> valueIterator = data.get(ueStringName).iterator();
                while( valueIterator.hasNext() ){
                    String paramValue = valueIterator.next();
                    builder.append(URLEncoder.encode(ueStringName, "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode(paramValue, "UTF-8"));
                    if (valueIterator.hasNext()) {
                        builder.append("&");
                    }
                }
                if( paramIterator.hasNext() )
                    builder.append("&");
            }
            return builder.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }//end toQueryString()

    /**
     * Given a URL, this method will make sure that the given parameter exists, and has the given value.  If the
     * parameter is not given, it will be added - if it does exist and does not match the given value it will be removed
     * and added back with the given value.
     * <br/><br/>
     * @param url the {@link URL} to modify
     * @param paramName the name of the parameter
     * @param paramValue the value of the parameter
     * @return a {@link URL} guaranteed to have the given value.
     * @throws URISyntaxException for errors converting to URI
     * @throws MalformedURLException for errors converting to URL
     */
    public static URL ensureParameter(URL url, String paramName, String paramValue) throws URISyntaxException, MalformedURLException {
        Map<String, List<String>> queryParams = splitQuery(url);
        ArrayList<String> array = new ArrayList<>();
        array.add(paramValue);
        queryParams.put(paramName, array);

        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), toQueryString(queryParams), null);
        return new URL(uri.toString());
    }//end ensureFormatParameter


    public static URI ensureParameter(URI uri, String paramName, String paramValue) throws URISyntaxException, MalformedURLException {
        return ensureParameter(new URL(uri.toString()), paramName, paramValue).toURI();
    }

    public static URI ensureFormatParameter(URI uri, String val) throws URISyntaxException, MalformedURLException {
        return ensureFormatParameter(new URL(uri.toString()), val).toURI();
    }



    /**
     * Given a URL, this method will make sure that the format parameter exists, and has the given value.  If the format
     * parameter is not given, it will be added - if it does exist and does not match the given format it will be removed
     * and added back with the given value.
     * <br/><br/>
     * @param url the {@link URL} to modify
     * @param format the format value, like "json", "xml" or "html"
     * @return a {@link URL} guaranteed to have the given value.
     * @throws URISyntaxException for errors converting to URI
     * @throws MalformedURLException for errors converting to URL
     */
    public static URL ensureFormatParameter(URL url, String format) throws URISyntaxException, MalformedURLException {
        return ensureParameter(url, "format", format);
    }//end ensureFormatParameter

    /**
     * Given a URL, this method will make sure that the format parameter exists, and has the given value.  If the format
     * parameter is not given, it will be added - if it does exist and does not match the given format it will be removed
     * and added back with the given value.
     * <br/><br/>
     * @param url the {@link URL} to modify
     * @param format the format value, like "json", "xml" or "html"
     * @return a {@link URL} guaranteed to have the given value.
     * @throws URISyntaxException for errors converting to URI
     * @throws MalformedURLException for errors converting to URL
     */
    public static URL ensureFormatParameter(String url, String format) throws URISyntaxException, MalformedURLException {
        return ensureFormatParameter(new URL(url), format);
    }


    /**
     * Given a URL string, this method will make sure that the given parameter exists, and has the given value.  If the
     * parameter is not given, it will be added - if it does exist and does not match the given value it will be removed
     * and added back with the given value.
     * <br/><br/>
     * @param url the url as a String
     * @param paramName the name of the parameter
     * @param paramValue the value of the parameter
     * @return a {@link URL} guaranteed to have the given value.
     * @throws URISyntaxException for errors converting to URI
     * @throws MalformedURLException for errors converting to URL
     */
    public static URL ensureParameter(String url, String paramName, String paramValue) throws URISyntaxException, MalformedURLException {
        return ensureParameter(new URL(url), paramName, paramValue);
    }





}/* end UrlUtils */
