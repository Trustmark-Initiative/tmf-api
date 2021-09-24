package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

/**
 * Retrieves trustmark status reports (TSRs) for trustmarks.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkStatusReportResolver extends ArtifactResolver<TrustmarkStatusReport> {

    /**
     * Resolves the TrustmarkStatusReport for the given Trustmark.
     */
    TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException;
}
