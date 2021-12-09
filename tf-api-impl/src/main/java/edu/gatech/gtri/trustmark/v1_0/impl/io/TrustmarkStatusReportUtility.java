package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportCache;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrustmarkStatusReportUtility {

    private static final Logger log = LogManager.getLogger(TrustmarkStatusReportUtility.class);

    private TrustmarkStatusReportUtility() {
    }

    public static TrustmarkStatusReport resolve(
            final Trustmark trustmark,
            final TrustmarkStatusReportResolver trustmarkStatusReportResolver) throws ResolveException {

        log.debug("Resolving status report for Trustmark (" + trustmark.getIdentifier() + ") ...");

        final TrustmarkStatusReportCache cache = FactoryLoader.getInstance(TrustmarkStatusReportCache.class);

        if (cache != null && cache.hasValidCacheEntry(trustmark)) {
            log.debug("Returning valid cache entry ...");
            return cache.getReportFromCache(trustmark);
        }

        return trustmarkStatusReportResolver.resolve(trustmark.getStatusURL());
    }
}
