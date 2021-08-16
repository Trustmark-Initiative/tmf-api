package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.IOUtils;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.service.*;
import edu.gatech.gtri.trustmark.v1_0.util.UrlUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

/**
 * An implementation of {@link TrustmarkFrameworkService}.
 * Created by brad on 2/4/16.
 */
public class TrustmarkFrameworkServiceImpl implements TrustmarkFrameworkService {
    //==================================================================================================================
    // Static Variables
    //==================================================================================================================
    public static final Logger log = LogManager.getLogger(TrustmarkFrameworkServiceImpl.class);
    public static final Long DEFAULT_TIMEOUT = 1000l * 60l * 5l; // Five minute timeout.
    //==================================================================================================================
    // Instance Variables
    //==================================================================================================================
    public URL baseUrl = null;
    //==================================================================================================================
    // Constructors
    //==================================================================================================================
    public TrustmarkFrameworkServiceImpl(URL baseUrl){
        this.baseUrl = baseUrl;
    }
    //==================================================================================================================
    //  Service Methods
    //==================================================================================================================
    @Override
    public URL getBaseUrl() {
        return baseUrl;
    }

    @Override
    public RemoteStatusImpl getStatus()  throws RemoteException {
        log.debug(String.format("Getting remote server status for BaseURL[%s]...", this.baseUrl.toString()));
        String statusUrlString = this.baseUrl.toString() + "/status?format=json";
        JSONObject statusJson = IOUtils.fetchJSON(statusUrlString);
        RemoteStatusImpl status = new RemoteStatusImpl(statusJson);
        return status;
    }//end getStatus()

    @Override
    public Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfiles() throws RemoteException  {
        RemoteStatusImpl remoteStatus = getStatus();
        if( remoteStatus.getSupportsTrustInteroperabilityProfiles() ) {
            URL tipListLocationsJsonUrl = remoteStatus.getTrustInteroperabilityProfileLocations().get("json");
            if (tipListLocationsJsonUrl == null)
                throw new RemoteException("The remote server didn't return any link to a list of TrustInteroperabilityProfiles JSON.  Either this server has none, or this is an error on the server.");
            return new TIPPageImpl(IOUtils.fetchJSON(tipListLocationsJsonUrl));
        }else{
            throw new RemoteException("The remote server does not support TrustInteroperabilityProfiles");
        }
    }

    @Override
    public Page<RemoteTrustmarkDefinition> listTrustmarkDefinitions() throws RemoteException  {
        RemoteStatusImpl remoteStatus = getStatus();
        if( remoteStatus.getSupportsTrustmarkDefinitions() ) {
            URL tdListStart = remoteStatus.getTrustmarkDefinitionLocations().get("json");
            if (tdListStart == null)
                throw new RemoteException("The remote server didn't return any link to a list of TrustmarkDefinitions JSON.  Either this server has none, or this is an error on the server.");
            return new TDPageImpl(IOUtils.fetchJSON(tdListStart));
        }else{
            throw new RemoteException("The remote server does not support TrustmarkDefinitions");
        }
    }//end listTrustmarkDefinitions()

    @Override
    public List<RemoteKeyword> getKeywords() throws RemoteException  {
        RemoteStatusImpl remoteStatus = getStatus();
        List<RemoteKeyword> keywords = new ArrayList<>();
        if( remoteStatus.getKeywordCount() > 0 ){
            URL keywordJsonLocation = remoteStatus.getKeywordLocations().get("json");
            if( keywordJsonLocation == null )
                throw new RemoteException("The remote server didn't return any link to a list of Keywords as JSON.  Either this server has none, of this is an error on the server.");
            JSONObject keywordsJson = IOUtils.fetchJSON(keywordJsonLocation);
            if( keywordsJson.optJSONArray("keywords") != null ){
                JSONArray keywordJsonArray = keywordsJson.optJSONArray("keywords");
                for( int i = 0; i < keywordJsonArray.length(); i++ ){
                    JSONObject keywordObjJson = keywordJsonArray.optJSONObject(i);
                    if( keywordObjJson != null ){
                        RemoteKeywordImpl impl = new RemoteKeywordImpl(keywordObjJson);
                        keywords.add(impl);
                    }
                }
            }
        }
        return keywords;
    }

    @Override
    public Page<RemoteTrustmarkDefinition> listTrustmarkDefinitionsByKeyword(String keyword) throws RemoteException  {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @Override
    public Page<RemoteTrustmarkDefinition> listTrustmarkDefinitionByNameAndVersionRegex(String nameRegex, String versionRegex)
        throws RemoteException
    {
        RemoteStatus remoteStatus = this.getStatus();
        if( remoteStatus.getTrustmarkDefinitionByNameLocations() == null || !remoteStatus.getTrustmarkDefinitionByNameLocations().containsKey("json") ){
            log.error("The remote server["+this.getBaseUrl().toString()+"] did not return any by name TD locations, or did not return a JSON endpoint!");
            throw new RemoteException("The remote server["+this.getBaseUrl().toString()+"] did not return any by name TD locations, or did not return a JSON endpoint!");
        }

        URL remoteUrl = this.getStatus().getTrustmarkDefinitionByNameLocations().get("json");
        return new TDPageImpl(executeByNameFromURL(remoteUrl, nameRegex, versionRegex));
    }
    
    @Override
    public RemoteTrustmarkDefinition getTrustmarkDefinitionByName(String name) throws RemoteException {
        String quotedName = Pattern.quote(name); // TODO: this may need to change
        Page<RemoteTrustmarkDefinition> results = this.listTrustmarkDefinitionByNameAndVersionRegex(quotedName, ".*");
        long totalCount = results.getTotalCount();
        if (totalCount == 0) {
            return null;
        }
        else if (totalCount == 1) {
            log.debug(String.format("******  getTrustmarkDefinitionByName %s %s\n",
                    results.getObjects().get(0).getName(),
                    results.getObjects().get(0).getDescription()));
            return results.getObjects().get(0);
        }
        else {
            throw new RemoteException(String.format("Too many matches (%s) for TD name: %s", totalCount, name));
        }
    }

    @Override
    public RemoteTrustmarkDefinition getTrustmarkDefinitionByUrl(String url)  throws RemoteException {
        String statusUrlString = url + "?format=json";
        JSONObject json = IOUtils.fetchJSON(statusUrlString);
        RemoteTrustmarkDefinition rtdi = new RemoteTrustmarkDefinitionImpl(json.getJSONObject("Metadata"));
        return rtdi;
    }

    @Override
    public RemoteTrustInteroperabilityProfile getTrustInteroperabilityProfileByUrl(String url)  throws RemoteException {
        String statusUrlString = url + "?format=json";
        JSONObject json = IOUtils.fetchJSON(statusUrlString);
        RemoteTrustInteroperabilityProfile rtip = new RemoteTrustInteroperabilityProfileImpl(json);
        return rtip;
    }

    @Override
    public RemoteTrustInteroperabilityProfile getTrustInteroperabilityProfileByName(String name) throws RemoteException {
        String quotedName = Pattern.quote(name); // TODO: this may need to change
        Page<RemoteTrustInteroperabilityProfile> results = this.listTrustInteroperabilityProfilesByNameAndVersionRegex(quotedName, ".*");
        long totalCount = results.getTotalCount();
        if (totalCount == 0) {
            return null;
        }
        else if (totalCount == 1) {
            return results.getObjects().get(0);
        }
        else {
            throw new RemoteException(String.format("Too many matches (%s) for TIP name: %s", totalCount, name));
        }
    }

    //==================================================================================================================
    //  Helper Methods
    //==================================================================================================================
    @Override
    public RemoteOrganization getOrganizationInformation(String orgIdOrName) throws RemoteException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    @Override
    public RemoteTaxonomy getTaxonomy() throws RemoteException {
        log.debug(String.format("Getting remote server taxonomy for BaseURL[%s]...", this.baseUrl.toString()));
        String statusUrlString = this.baseUrl.toString() + "/taxonomy-terms?format=json";
        JSONObject responseJson = IOUtils.fetchJSON(statusUrlString);
        RemoteTaxonomyImpl taxonomy = new RemoteTaxonomyImpl(responseJson);
        return taxonomy;
    }

    @Override
    public Thread downloadAll(DownloadAllListener... listeners) {
        try {
            log.debug("Getting status to obtain the latest version set...");
            RemoteStatus status = this.getStatus();
            log.debug("Subbing to downloadAll(@|green "+status.getCurrentVersionSet().getName()+"|@, <listeners>)...");
            return this.downloadAll(status.getCurrentVersionSet().getName(), listeners);
        }catch(RemoteException re){
            log.error("Unable to get the current version set!", re);
            if( listeners != null ){
                for( DownloadAllListener listener : listeners ){
                    listener.downloadAllError(re);
                }
            }
            return null;
        }
    }//end downloadAll()

    @Override
    public Thread downloadAll(String versionSetName, DownloadAllListener... listeners) {
        log.debug("Downloading all for version set: @|green "+versionSetName+"|@");

        final String fVersionSetName = versionSetName;
        final DownloadAllListener[] fListeners = listeners;
        RemoteStatus remoteStatus = null;
        try{
            remoteStatus = this.getStatus();
        }catch(RemoteException re){
            log.error("Error getting current remote status!", re);
            fireDownloadAllError(re, listeners);
            return null;
        }

        URL vsDownloadAllUrl = null;
        for( RemoteVersionSet rvs : remoteStatus.listVersionSets() ){
            if(rvs.getName().equalsIgnoreCase(versionSetName) ){
                vsDownloadAllUrl = rvs.getDownloadAllUrl();
            }
        }

        if( vsDownloadAllUrl == null ){
            fireDownloadAllError("Unable to find any download all URL for version set: "+versionSetName, listeners);
            return null;
        }

        final URL fDownloadAllUrl = vsDownloadAllUrl;
        Thread downloadAllThread = new Thread(new Runnable() {
            @Override
            public void run() {

                fireDownloadAllStarted(fListeners);

                JSONObject initJson = null;
                try {
                    initJson = IOUtils.fetchJSON(fDownloadAllUrl.toString());
                }catch(RemoteException re){
                    log.error("Error getting starting download!", re);
                    fireDownloadAllError(re, fListeners);
                    return;
                }

                if( !"SUCCESS".equalsIgnoreCase(initJson.optString("status")) ){
                    String reason = "The server was unable to start the download: "+initJson.optString("message");
                    log.error(reason);
                    fireDownloadAllError(reason, fListeners);
                    return;
                }

                String monitorUrl = initJson.optString("monitorUrl");
                if( monitorUrl == null || monitorUrl.trim().length() == 0){
                    String reason = "The server has returned an empty monitor URL, the download cannot occur.";
                    log.error(reason);
                    fireDownloadAllError(reason, fListeners);
                    return;
                }

                long startTime = System.currentTimeMillis();
                boolean monitoring = true;
                while( monitoring ){
                    try {
                        JSONObject currentStatusJson = IOUtils.fetchJSON(monitorUrl);
                        // TODO Validate response?
                        boolean executing = currentStatusJson.optBoolean("executing", false);
                        if( !executing ){
                            String latestDownloadInfoURL = currentStatusJson.optString("latestDownloadInfoURL");
                            log.debug("Executing has finished while building remote zip file!  Finished info URL: "+latestDownloadInfoURL);
                            if( latestDownloadInfoURL != null && latestDownloadInfoURL.trim().length() > 0 ){
                                JSONObject latestDownloadInfoJson = IOUtils.fetchJSON(latestDownloadInfoURL);
                                String size = latestDownloadInfoJson.optString("humanSize");
                                String downloadUrl = latestDownloadInfoJson.optString("url");

                                log.info("The server has successfully built a zip that is size("+size+"), located at: "+downloadUrl);
                                byte[] data = FactoryLoader.getInstance(NetworkDownloader.class).download(new URL(downloadUrl)).getBinaryContent();

                                File downloadZip = File.createTempFile("downloadAll-", ".zip");
                                FileOutputStream fout = new FileOutputStream(downloadZip);
                                fout.write(data);
                                fout.flush();
                                fout.close();

                                log.info("Successfully wrote "+size+" to temp file "+downloadZip.getPath()+", notifying all listeners...");
                                fireDownloadAllComplete(latestDownloadInfoJson, downloadZip, fListeners);

                                monitoring = false; // Stop monitoring, we are finished.
                            }else{
                                fireDownloadAllError("The server did not return any latest download URL!", fListeners);
                                return;
                            }
                        }else{
                            String msg = currentStatusJson.optString("message");
                            Integer percentage = currentStatusJson.optInt("percentage", -2);
                            log.debug("Download All Status Update: "+msg+", "+percentage);
                            fireDownloadAllStatusUpdate(msg, percentage, fListeners);
                        }

                        long now = System.currentTimeMillis();
                        if( (now-startTime) > DEFAULT_TIMEOUT ){
                            log.error("Error - time has exceeded the timeout waiting for a download all!");
                            throw new TimeoutException("System timed out waiting for a Download All zip file to be obtained.");
                        }
                    }catch(Throwable t){
                        log.error("Error while monitoring remote download all build!", t);
                        fireDownloadAllError(new RemoteException("Error while monitoring remote download All build!", t), fListeners);
                        return;
                    }
                    try{Thread.sleep(333);}catch(Throwable t){log.error("Error while sleeping.", t);}
                }

                // Call start on server
                // Monitor until complete
                // download zip
            }
        });
        downloadAllThread.setName("DownloadAllThread_"+System.currentTimeMillis());
        downloadAllThread.start();

        return downloadAllThread;
    }

    private void fireDownloadAllStarted(DownloadAllListener[] listeners){
        if( listeners != null && listeners.length > 0 ) {
            for (DownloadAllListener listener : listeners) {
                try {
                    listener.downloadAllStarted();
                }catch(Throwable t){
                    log.error("Error executing "+listener.getClass().getName()+".downloadAllStarted( <void> )", t);
                }
            }
        }
    }


    private void fireDownloadAllComplete(JSONObject json, File downloadZip, DownloadAllListener[] listeners){
        if( listeners != null && listeners.length > 0 ) {
            DownloadAllResponseImpl downloadAllResponse = new DownloadAllResponseImpl(json, downloadZip);
            for (DownloadAllListener listener : listeners) {
                try {
                    listener.downloadAllComplete(downloadAllResponse);
                }catch(Throwable t){
                    log.error("Error executing "+listener.getClass().getName()+".downloadAllComplete("+downloadAllResponse+")", t);
                }
            }
        }
    }

    private void fireDownloadAllStatusUpdate(String update, Integer percentage, DownloadAllListener[] listeners){
        if( listeners != null && listeners.length > 0 ) {
            for (DownloadAllListener listener : listeners) {
                try {
                    listener.downloadAllStatusUpdate(update, percentage);
                }catch(Throwable t){
                    log.error("Error executing "+listener.getClass().getName()+".downloadAllStatusUpdate("+update+", "+percentage+")", t);
                }
            }
        }
    }

    private void fireDownloadAllError(String msg, DownloadAllListener[] listeners) {
        this.fireDownloadAllError(new RemoteException(msg), listeners);
    }
    private void fireDownloadAllError(RemoteException re, DownloadAllListener[] listeners){
        if( listeners != null && listeners.length > 0 ){
            for( DownloadAllListener listener : listeners ){
                try{
                    listener.downloadAllError(re);
                }catch(Throwable t){
                    log.error("Error executing "+listener.getClass().getName()+".downloadAllError("+re+")", t);
                }
            }
        }
    }

    @Override
    public Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfilesByKeyword(String keyword) throws RemoteException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }
    
    @Override
    public Page<RemoteTrustInteroperabilityProfile> listTrustInteroperabilityProfilesByNameAndVersionRegex(String nameRegex, String versionRegex)
        throws RemoteException
    {
        RemoteStatus remoteStatus = this.getStatus();
        if( remoteStatus.getTrustInteroperabilityProfileByNameLocations() == null || !remoteStatus.getTrustInteroperabilityProfileByNameLocations().containsKey("json") ){
            log.error("The remote server["+this.getBaseUrl().toString()+"] did not return any by name TIP locations, or did not return a JSON endpoint!");
            throw new RemoteException("The remote server["+this.getBaseUrl().toString()+"] did not return any by name TIP locations, or did not return a JSON endpoint!");
        }

        URL remoteUrl = this.getStatus().getTrustInteroperabilityProfileByNameLocations().get("json");
        return new TIPPageImpl(executeByNameFromURL(remoteUrl, nameRegex, versionRegex));
    }

    private JSONObject executeByNameFromURL(URL remoteUrl, String nameRegex, String versionRegex) throws RemoteException {
        URL byNameUrl = null;
        try {
            byNameUrl = UrlUtils.ensureParameter(remoteUrl, "nameRegex", nameRegex);
            byNameUrl = UrlUtils.ensureParameter(byNameUrl, "versionRegex", versionRegex);
            byNameUrl = UrlUtils.ensureFormatParameter(byNameUrl, "json");
        }catch(Throwable t){
            log.debug(String.format("*** BUILT URL for searching -> %s | %s | %s ", byNameUrl, nameRegex, versionRegex));
            log.error("Error while adding nameRegex and versionRegex patterns to URL!", t);
            throw new RemoteException("Unable to build remote By-Name URL!", t);
        }

        log.debug("Sending request to ByName URL["+byNameUrl+"]...");
        JSONObject listJson = IOUtils.fetchJSON(byNameUrl);
        return listJson;
    }
    
    @Override
    public RemoteSearchResult search(String text) throws RemoteException {
        log.info("Request to search on @|green "+text+"|@");

        String searchUrlString = getBaseUrl().toString();
        if( searchUrlString.endsWith("/") ){
            searchUrlString += "search";
        }else{
            searchUrlString += "/search";
        }

        try {
            searchUrlString += "?format=json&q=" + URLEncoder.encode(text, "UTF-8");
        }catch(Throwable t){
            throw new RemoteException("Cannot encode search text into URL!", t);
        }

        log.debug("Searching URL: [@|cyan "+searchUrlString+"|@]");
        JSONObject jsonObject = IOUtils.fetchJSON(searchUrlString);
        log.debug("Received a response, parsing...");

        RemoteSearchResultImpl searchResult = new RemoteSearchResultImpl(jsonObject);
        return searchResult;
    }

    @Override
    public void setVersionSet(String name) throws RemoteException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }



}//end class TrustmarkFrameworkServiceImpl