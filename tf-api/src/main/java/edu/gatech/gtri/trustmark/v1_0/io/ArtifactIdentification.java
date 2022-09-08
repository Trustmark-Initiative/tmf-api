package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;

/**
 * Implementations describe an artifact type.
 *
 * @author GTRI Trustmark Team
 */
public interface ArtifactIdentification {

    enum ArtifactType {
        TRUSTMARK,
        TRUSTMARK_STATUS_REPORT,
        TRUSTMARK_DEFINITION,
        TRUST_INTEROPERABILITY_PROFILE
    }

    /**
     * Returns the file for the artifact.
     *
     * @return the file for the artifact
     */
    File getFile();

    /**
     * Returns the artifact type for the artifact.
     *
     * @return the artifact type for the artifact
     */
    ArtifactType getArtifactType();

    /**
     * Returns the media type for the artifact.
     *
     * @return the media type for the artifact
     */
    String getMimeType();
}
