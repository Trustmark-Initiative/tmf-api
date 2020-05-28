package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.DownloadAllListener;
import edu.gatech.gtri.trustmark.v1_0.service.DownloadAllResponse;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 4/14/17
 */
public class DownloadAllListenerStateImpl implements DownloadAllListener {
    //==================================================================================================================
    //  INSTANCE VARS
    //==================================================================================================================
    private Boolean downloadAllStartedCalled = false;
    private List<Map> downloadAllStatusUpdates = new ArrayList<>();
    private DownloadAllResponse response = null;
    private Boolean completedSuccessfully = false;
    private RemoteException error = null;
    private Boolean hasError = false;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public Boolean getDownloadAllStartedCalled() {
        return downloadAllStartedCalled;
    }
    public List<Map> getDownloadAllStatusUpdates() {
        return downloadAllStatusUpdates;
    }
    public DownloadAllResponse getResponse() {
        return response;
    }
    public Boolean getCompletedSuccessfully() {
        return completedSuccessfully;
    }
    public RemoteException getError() {
        return error;
    }
    public Boolean getHasError() {
        return hasError;
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================
    @Override
    public void downloadAllStarted() {
        downloadAllStartedCalled = true;
    }

    @Override
    public void downloadAllStatusUpdate(String status, Integer completionPercentage) {
        Map data = new HashMap();
        data.put("status", status);
        data.put("completionPercentage", completionPercentage);
        this.downloadAllStatusUpdates.add(data);
    }

    @Override
    public void downloadAllComplete(DownloadAllResponse response) {
        this.response = response;
        completedSuccessfully = true;
    }

    @Override
    public void downloadAllError(RemoteException re) {
        error = re;
        hasError = true;
    }


}/* end DownloadAllListenerStateImpl */