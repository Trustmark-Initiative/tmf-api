package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

/**
 * Implementations resolve a Trustmark Status Report from a source.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkStatusReportResolver extends ArtifactResolver<TrustmarkStatusReport> {

    /**
     * Parses the trustmark status report from the given trustmark.
     *
     * @param trustmark the trustmark
     * @return the trustmark status report
     * @throws ResolveException if the system cannot resolve the URL to an
     *                          artifact
     */
    TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException;
}
