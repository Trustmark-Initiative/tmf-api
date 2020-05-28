package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;

/**
 * This class will analyze a file and return the type of artifact that is in it.
 * <br/><br/>
 * Created by brad on 7/29/16.
 */
public interface ArtifactIdentificationHelper {

    /**
     * Opens and analyzes the given file to determine what is represented in it.  If the system cannot determine what
     * file contents are, a ResolveException is raised.
     */
    public ArtifactIdentification getArtifactIdentification(File file) throws ResolveException;


}
