package edu.gatech.gtri.trustmark.v1_0.service;

import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * When authenticated with a TFAM, you can retrieve version set information.
 * <br/><br/>
 * @author brad
 * @date 3/21/17
 */
public interface RemoteVersionSet extends RemoteObject {

    public String getName();

    public Boolean isProduction();

    public Boolean isEditable();

    public Date getReleasedDate();

    public String getPredecessorName();

    public String getSuccessorName();

    /**
     * Returns the URL to support downloading all of this RemoteVersionSet at once.
     * @return
     */
    public URL getDownloadAllUrl();

}/* end RemoteVersionSet */
