package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;

/**
 * Created by brad on 7/29/16.
 */
public interface ArtifactIdentification {

    public static enum ArtifactType {
        TRUSTMARK,
        TRUSTMARK_STATUS_REPORT,
        TRUSTMARK_DEFINITION,
        TRUST_INTEROPERABILITY_PROFILE
    }

    /**
     * Returns the file that this identification is for.
     */
    public File getFile();

    /**
     * Returns the type of artifact in the file.
     */
    public ArtifactType getArtifactType();

    /**
     * Returns the mime type of the file, such as application/json or text/xml.
     */
    public String getMimeType();

}
