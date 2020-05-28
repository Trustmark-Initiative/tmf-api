package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionComparator;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiff;

/**
 * Created by brad on 4/15/16.
 */
public abstract class AbstractTDDiff extends AbstractDiff implements TrustmarkDefinitionDiff {

    private static Boolean COMPARATOR_LOCK = Boolean.FALSE;
    protected static TrustmarkDefinitionComparator CACHED_COMPARATOR = null;
    protected static TrustmarkDefinitionComparator loadComparator() {
        synchronized (COMPARATOR_LOCK){
            if( CACHED_COMPARATOR == null )
                CACHED_COMPARATOR = FactoryLoader.getInstance(TrustmarkDefinitionComparator.class);
        }
        return CACHED_COMPARATOR;
    }




}
