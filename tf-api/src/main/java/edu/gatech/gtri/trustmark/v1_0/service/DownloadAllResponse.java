package edu.gatech.gtri.trustmark.v1_0.service;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

/**
 * Interface representing all the data you can download from a remote TFAM.
 * <br/><br/>
 * @author brad
 * @date 3/21/17
 */
public interface DownloadAllResponse {

    /**
     * The system will download a ZIP file from the server, and you can get access to it here.
     */
    public File getZipFile();

    /**
     * The location on the internet this zip file is located (ie, where the zip file came from).
     */
    public URL getDownloadUrl();

    /**
     * Returns the name of the version set represented by this download all response.
     */
    public String getVersionSetName();

    /**
     * Size in bytes of the downloaded zip file.
     */
    public Long getSize();

    /**
     * A 'pretty' way to represent the size to the user.
     */
    public String getHumanReadableSize();

    /**
     * When the zip file was constructed on the server.
     */
    public Date getCreateDate();

}/* end DownloadAllResponse */