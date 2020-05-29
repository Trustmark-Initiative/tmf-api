package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

/**
 * A specialty Cache class used to allow users to implement their own status Report caching logic easily.
 * Caching other artifacts can be done, but requires implementing a custom URIResolver implementation.
 * <br/><br/>
 * Created by brad on 12/10/15.
 */
public interface TrustmarkStatusReportCache {

    /**
     * Returns true if there exists a cached status report which is still valid for this trustmark.  Returns false if
     * there is no cache entry, or the existing one is invalid.
     */
    public Boolean hasValidCacheEntry(Trustmark trustmark);

    /**
     * Returns the TrustmarkStatusReport from the cache, assumes that the TSR is still valid (ie, hasValidCacheEntry returns true)
     */
    public TrustmarkStatusReport getReportFromCache( Trustmark trustmark ) throws ParseException;

    /**
     * Called when the TrustmarkStatusReportResolver resolves a report for a given trustmark.
     */
    public void updateCache(Trustmark trustmark, TrustmarkStatusReport tsr);

}//end TrustmarkStatusReportCache