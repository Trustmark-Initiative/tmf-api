package edu.gatech.gtri.trustmark.v1_0.service;

import java.net.URL;
import java.util.Map;

/**
 * Created by brad on 2/4/16.
 */
public interface RemoteObject {

    /**
     * Provides the collection of formats this remote object has, as well as the URL to them.
     * @return
     */
    public Map<String, URL> getFormats();

}
