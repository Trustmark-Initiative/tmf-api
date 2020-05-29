package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustInteroperabilityProfileDao;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustmarkDefinitionDao;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.net.URI;
import java.util.*;

/**
 * Created by Nicholas on 12/12/2016.
 */
public class TipStatistics {
    
    // Constants - Field Comparators
    public static final TipStatisticsField<URI> TIP_IDENTIFIER = ts -> ts.trustInteroperabilityProfile.getIdentifier();
    public static final TipStatisticsField<String> TIP_NAME = ts -> ts.trustInteroperabilityProfile.getName();
    public static final TipStatisticsField<Integer> KNOWN_TD_REFERENCE_COUNT = ts -> ts.knownTdReferenceCount;
    public static final TipStatisticsField<Integer> KNOWN_TIP_REFERENCE_COUNT = ts -> ts.knownTipReferenceCount;
    public static final TipStatisticsField<Integer> KNOWN_REFERENCE_COUNT = TipStatistics::getKnownReferenceCount;
    public static final TipStatisticsField<Integer> UNKNOWN_TD_REFERENCE_COUNT = ts -> ts.unknownTdReferences.size();
    public static final TipStatisticsField<Integer> UNKNOWN_TIP_REFERENCE_COUNT = ts -> ts.unknownTipReferences.size();
    public static final TipStatisticsField<Integer> UNKNOWN_REFERENCE_COUNT = TipStatistics::getUnknownReferenceCount;
    public static final TipStatisticsField<Integer> HEIGHT = ts -> ts.height;
    
    
    // Fields
    public final TrustInteroperabilityProfile trustInteroperabilityProfile;
    public final int knownTdReferenceCount;
    public final int knownTipReferenceCount;
    public final List<URI> unknownTdReferences;
    public final List<URI> unknownTipReferences;
    public final int height;
    
    
    // Constructor
    protected TipStatistics(
        TrustInteroperabilityProfile _trustInteroperabilityProfile,
        int _knownTdReferenceCount,
        int _knownTipReferenceCount,
        List<URI> _unknownTdReferences,
        List<URI> _unknownTipReferences,
        int _height
    ) {
        this.trustInteroperabilityProfile = _trustInteroperabilityProfile;
        this.knownTdReferenceCount = _knownTdReferenceCount;
        this.knownTipReferenceCount = _knownTipReferenceCount;
        this.unknownTdReferences = _unknownTdReferences;
        this.unknownTipReferences = _unknownTipReferences;
        this.height = _height;
    }
    
    
    // Instance Methods
    @Override
    public String toString() {
        return String.format(
            "{ \"tipIdentifier\": \"%s\", \"knownTdRefCount\": %s, \"knownTipRefCount\": %s, \"unknownTdRefCount\": %s, \"unknownTipRefCount\": %s, \"height\": %s }",
            this.trustInteroperabilityProfile.getIdentifier(),
            this.knownTdReferenceCount,
            this.knownTipReferenceCount,
            this.unknownTdReferences.size(),
            this.unknownTipReferences.size(),
            this.height
        );
    }
    
    public final int getKnownReferenceCount() { return this.knownTdReferenceCount + this.knownTipReferenceCount; }
    public final int getUnknownReferenceCount() { return this.unknownTdReferences.size() + this.unknownTipReferences.size(); }
    
    
    // Static Methods
    public static Map<URI, TipStatistics> gatherStatistics(
        Collection<? extends TrustInteroperabilityProfile> tips,
        Collection<? extends TrustmarkDefinition> tds
    ) throws Exception {
        TipStatisticsCache cache = new ProvidedTipStatisticsCache(tips, tds);
        return gatherStatistics(tips, cache);
    }
    
    public static Map<URI, TipStatistics> gatherStatistics(
        Collection<? extends TrustInteroperabilityProfile> tips,
        DaoManager daoManager
    ) throws Exception {
        TipStatisticsCache cache = new DaoTipStatisticsCache(daoManager);
        return gatherStatistics(tips, cache);
    }
    
    public static Map<URI, TipStatistics> gatherStatistics(
        Collection<? extends TrustInteroperabilityProfile> tips,
        TipStatisticsCache cache
    ) throws Exception {
        HashMap<URI, TipStatistics> result = new HashMap<>();
        gatherStatisticsRecursively(result, cache, tips);
        return result;
    }
    
    protected static void gatherStatisticsRecursively(
        Map<URI, TipStatistics> result,
        TipStatisticsCache cache,
        Collection<? extends TrustInteroperabilityProfile> tips
    ) throws Exception {
        for (TrustInteroperabilityProfile tip : tips) {
            URI identifier = tip.getIdentifier();
            if (result.containsKey(identifier)) {
                continue;
            }
            
            int knownTdReferenceCount = 0;
            int knownTipReferenceCount = 0;
            List<URI> unknownTdReferences = new ArrayList<>();
            List<URI> unknownTipReferences = new ArrayList<>();
            ArrayList<TrustInteroperabilityProfile> referencedTips = new ArrayList<>();
            for (AbstractTIPReference ref : tip.getReferences()) {
                URI refIdentifier = ref.getIdentifier();
                if (ref.isTrustInteroperabilityProfileReference()) {
                    TrustInteroperabilityProfile referencedTip = cache.getTip(refIdentifier);
                    if (referencedTip != null) {
                        ++knownTipReferenceCount;
                        referencedTips.add(referencedTip);
                    }
                    else {
                        unknownTipReferences.add(refIdentifier);
                    }
                }
                else if (ref.isTrustmarkDefinitionRequirement()) {
                    if (cache.hasTd(refIdentifier)) {
                        ++knownTdReferenceCount;
                    }
                    else {
                        unknownTdReferences.add(refIdentifier);
                    }
                }
            }
            
            gatherStatisticsRecursively(result, cache, referencedTips);
            
            ArrayList<TipStatistics> referencedTipsStatistics = new ArrayList<>();
            for (TrustInteroperabilityProfile referencedTip : referencedTips) {
                URI refIdentifier = referencedTip.getIdentifier();
                TipStatistics tipStatistics = result.get(refIdentifier);
                referencedTipsStatistics.add(tipStatistics);
            }
            
            for (TipStatistics tipStatistics : referencedTipsStatistics) {
                knownTdReferenceCount += tipStatistics.knownTdReferenceCount;
                knownTipReferenceCount += tipStatistics.knownTipReferenceCount;
                unknownTdReferences.addAll(tipStatistics.unknownTdReferences);
                unknownTipReferences.addAll(tipStatistics.unknownTipReferences);
            }
            
            int referencedTipMaxHeight = referencedTipsStatistics.size() == 0
                ? 0
                : Collections.max(referencedTipsStatistics, HEIGHT.ASC()).height
            ;
            
            TipStatistics tipStatistics = new TipStatistics(
                tip,
                knownTdReferenceCount,
                knownTipReferenceCount,
                unknownTdReferences,
                unknownTipReferences,
                referencedTipMaxHeight + 1
            );
            result.put(identifier, tipStatistics);
        }
    }
    
    
    // Static Nested Classes
    @FunctionalInterface
    public static interface TipStatisticsField<T extends Comparable<? super T>> extends Comparator<TipStatistics> {
        // Instance Methods - Abstract
        T getFieldValueFor(TipStatistics ts);
        
        // Instance Methods - Default
        @Override
        default int compare(TipStatistics x, TipStatistics y) {
            return this.getFieldValueFor(x).compareTo(this.getFieldValueFor(y));
        }
        default Comparator<TipStatistics> ASC() { return this; }
        default Comparator<TipStatistics> DESC() { return (x, y) -> -this.compare(x, y); }
    }
    
    public static interface TipStatisticsCache {
        public boolean hasTd(URI tdIdentifier) throws Exception;
        public TrustInteroperabilityProfile getTip(URI tipIdentifier) throws Exception;
    }
    
    public static class ProvidedTipStatisticsCache implements TipStatisticsCache {
        // Fields
        protected final HashSet<URI> knownTdIdentifiers;
        protected final HashMap<URI,TrustInteroperabilityProfile> knownTips;
        
        // Constructor
        public ProvidedTipStatisticsCache(
            Collection<? extends TrustInteroperabilityProfile> tips,
            Collection<? extends TrustmarkDefinition> tds
        ) throws Exception {
            this.knownTdIdentifiers = new HashSet<>();
            for (TrustmarkDefinition td : tds) {
                this.knownTdIdentifiers.add(td.getMetadata().getIdentifier());
            }
            this.knownTips = new HashMap<>();
            for (TrustInteroperabilityProfile tip : tips) {
                this.knownTips.put(tip.getIdentifier(), tip);
            }
        }
        
        // Instance Methods
        @Override
        public boolean hasTd(URI tdIdentifier) throws Exception {
            return this.knownTdIdentifiers.contains(tdIdentifier);
        }
        
        @Override
        public TrustInteroperabilityProfile getTip(URI tipIdentifier) throws Exception {
            return this.knownTips.get(tipIdentifier);
        }
    }
    
    public static class DaoTipStatisticsCache implements TipStatisticsCache {
        // Fields
        protected final TrustmarkDefinitionDao tdDao;
        protected final TrustInteroperabilityProfileDao tipDao;
        
        // Constructor
        public DaoTipStatisticsCache(
            DaoManager daoManager
        ) throws Exception {
            this.tdDao = daoManager.getTrustmarkDefinitionDao();
            this.tipDao = daoManager.getTrustInteroperabilityProfileDao();
        }
        
        public DaoTipStatisticsCache(
            TrustmarkDefinitionDao _tdDao,
            TrustInteroperabilityProfileDao _tipDao
        ) throws Exception {
            this.tdDao = _tdDao;
            this.tipDao = _tipDao;
        }
        
        // Instance Methods
        @Override
        public boolean hasTd(URI tdIdentifier) throws Exception {
            TrustmarkDefinition td = this.tdDao.get(tdIdentifier == null ? null : tdIdentifier.toString());
            return td != null;
        }
        
        @Override
        public TrustInteroperabilityProfile getTip(URI tipIdentifier) throws Exception {
            TrustInteroperabilityProfile tip = this.tipDao.get(tipIdentifier == null ? null : tipIdentifier.toString());
            return tip;
        }
    }
}
