package edu.gatech.gtri.trustmark.v1_0.impl;

import edu.gatech.gtri.trustmark.v1_0.impl.dao.HttpResponseFromFile;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the {@link NetworkDownloader] which serves from a local directory.  Very useful for testing
 * services that need a RemoteService.  You simply register this with FactoryLoader before you begin testing with
 * the RemoteService to make things work locally.
 * <br/><br/>
 * @user brad
 * @date 9/13/16
 */
public class RemoteServiceNetworkDownloader implements NetworkDownloader {

    private static final Logger logger = Logger.getLogger(RemoteServiceNetworkDownloader.class);

    public static final String FILE_BASE = "./src/test/resources/spoof_remote_service";

    private long downloadAllStart = 0l;

    @Override
    public HttpResponse download(URL url) throws IOException {
        logger.debug("Downloading URL: "+url+" [host="+url.getHost()+"] [path="+url.getPath()+"] [query="+url.getQuery()+"]");
        if( url.getHost() != null && url.getHost().contains("example.org") ){
            if( (url.getQuery() != null && url.getQuery().contains("format=json")) ||  url.getPath().contains(".json") ) {

                String path = getPath(url);
                logger.info("Finding data for: " + url);

                if (path.startsWith("/status")) {
                    logger.debug("Returning status1.json...");
                    return new HttpResponseFromFile(getFile("/status1.json"), "application/json", false);
                }

                if (path.equals("/trustmark-definitions")) {
                    logger.debug("Returning tds1.json...");
                    return new HttpResponseFromFile(getFile("/tds1.json"), "application/json", false);
                }

                if (path.equals("/taxonomy-terms")) {
                    logger.debug("Returning taxonomy-terms.json...");
                    return new HttpResponseFromFile(getFile("/taxonomy-terms.json"), "application/json", false);
                }


                if (path.equals("/trust-interoperability-profiles")) {
                    return new HttpResponseFromFile(getFile("/tips1.json"), "application/json", false);
                }

                if (path.startsWith("/keywords")) {
                    return new HttpResponseFromFile(getFile("/keywords.json"), "application/json", false);
                }

                if (path.startsWith("/downloadAll")) {
                    logger.debug("Found download all request...");
                    if (path.contains("/downloadAll/build/VS_20170411_1.json")) {
                        logger.debug("Starting download all...");
                        downloadAllStart = System.currentTimeMillis();
                        return new HttpResponseFromFile(getFile("/downloadAll/1.json"), "application/json", false);
                    }

                    if (path.contains("/downloadAll/monitor/VS_20170411_1")) {
                        long now = System.currentTimeMillis();
                        logger.debug("Checking status...");
                        return new HttpResponseFromFile(resolveDownloadAllFile(now, downloadAllStart), "application/json", false);
                    }

                    if (path.contains("/downloadAll/getLatestDownload/VS_20170411_1.json")) {
                        logger.debug("Getting latest download...");
                        return new HttpResponseFromFile(getFile("/downloadAll/5.json"), "application/json", false);
                    }
                }

                if (path.startsWith("/trustmark-definitions/")) {
                    Pattern pattern = Pattern.compile("\\/trustmark-definitions\\/(.*?)\\/(.*?)\\/");
                    Matcher m = pattern.matcher(path);
                    if (m.matches()) {
                        String name = m.group(1);
                        String version = m.group(2);
                        logger.info("Resolved TD file: " + name + "_" + version + ".json");
                        return new HttpResponseFromFile(getFile("/tds_1/" + name + "_" + version + ".json"), "application/json", false);

                    } else {
                        logger.error("UNABLE TO MATCH!");
                    }

                }

                if (path.startsWith("/trust-interoperability-profiles/")) {
                    Pattern pattern = Pattern.compile("\\/trust-interoperability-profiles\\/(.*?)\\/(.*?)\\/");
                    Matcher m = pattern.matcher(path);
                    if (m.matches()) {
                        String name = m.group(1);
                        String version = m.group(2);
                        logger.info("Resolved TIP file: " + name + "_" + version + ".json");
                        return new HttpResponseFromFile(getFile("/tips_1/" + name + "_" + version + ".json"), "application/json", false);

                    } else {
                        logger.error("UNABLE TO MATCH!");
                    }

                }

                if( path.contains("/search") ){
                    return new HttpResponseFromFile(getFile("/search.json"), "application/json", false);
                }

                logger.error("Cannot find: " + path);
                // FIXME Return data here.
                throw new UnsupportedOperationException("Not yet implemented");


            }else if( url.getPath().contains("/binary/view/831") ){

                return new HttpResponseFromFile(getFile("/downloadAll/test.zip"), "application/zip", true);

            }else{
                throw new UnsupportedOperationException("Only JSON formats are supported.  This query is either empty (ie, no format=json) or specifies something else (like XML).");
            }
        }else{
            throw new UnsupportedOperationException("URL Host '"+url.getHost()+"' is NOT a valid URL for the test RemoteServiceNetworkDownloader.");
        }
    }

    private File resolveDownloadAllFile(Long now, Long start){
        long diff = now - start;
        logger.info("Resolve DownloadAllFile, diff="+diff);
        if( diff >= 0l ){
            if( diff < 1000l ){
                return getFile("/downloadAll/2-a.json");
            }else if( diff < 2000l ){
                return getFile("/downloadAll/2-b.json");
            }else if( diff < 3000l ) {
                return getFile("/downloadAll/2-c.json");
            }else if( diff < 4000l ) {
                return getFile("/downloadAll/2-d.json");
            }else if( diff < 5000l ) {
                return getFile("/downloadAll/2-e.json");
            }else if( diff < 6000l ) {
                return getFile("/downloadAll/3.json");
            }else { // Anything over 6s.
                return getFile("/downloadAll/4.json");
            }
        }else{
            return getFile("/downloadAll/error.json");
        }
    }

    private File getFile(String subpath){
        return new File(FILE_BASE, subpath);
    }

    private String getPath(URL url){
        return url.getPath().substring("/test".length());
    }




}/* end RemoteServiceNetworkDownloader */