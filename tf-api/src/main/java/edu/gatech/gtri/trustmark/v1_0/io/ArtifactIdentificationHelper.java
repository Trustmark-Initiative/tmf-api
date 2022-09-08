package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;

/**
 * Implementations return an artifact identification for a file.
 *
 * @author GTRI Trustmark Team
 */
public interface ArtifactIdentificationHelper {

    /**
     * Returns an artifact identification for a file.
     *
     * @param file the file
     * @return the artifact identification
     * @throws ResolveException if the system cannot identify the file
     */
    ArtifactIdentification getArtifactIdentification(final File file) throws ResolveException;
}
