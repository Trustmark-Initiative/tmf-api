package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * Can parse a trustmark from various sources.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkResolver extends ArtifactResolver<Trustmark> {
}
