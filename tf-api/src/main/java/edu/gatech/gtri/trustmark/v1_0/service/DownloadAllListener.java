package edu.gatech.gtri.trustmark.v1_0.service;

/**
 * Called by the API after a download all request is made, to update status of the request (which is potentially long-running).
 * Your code is expected to implement this interface to perform the necessary actions to handle a download-at-once Zip file.
 * <br/><br/>
 * @author brad
 * @date 3/21/17
 */
public interface DownloadAllListener {

    /**
     * Indicates that the local system has successfully contacted the remote system and told it to start a download.
     */
    public void downloadAllStarted();

    /**
     * Periodically called during the time between starting a download all request and when it is completely downloaded.
     * Note that the completion percentage may wrap (ie, go 0-100 then back to 0-100, etc.)
     */
    public void downloadAllStatusUpdate(String status, Integer completionPercentage);

    /**
     * Called to indicate that the download all has succeeded, and all remote information was retrieved.
     */
    public void downloadAllComplete(DownloadAllResponse response);

    /**
     * Called when the attempt to download all data has failed.
     */
    public void downloadAllError(RemoteException re);

}/* end DownloadAllListener */
