package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.dao.SearchResult;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustInteroperabilityProfileDao;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.DataObjectContent;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustInteroperabilityProfileCache;
import edu.gatech.gtri.trustmark.v1_0.impl.util.TipStatistics;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.log4j.Logger;

import java.net.URI;
import java.sql.SQLException;
import java.util.*;

/**
 * Implementation of the {@link TrustInteroperabilityProfileDao} which uses the ORMLite library to interact with the database.
 * <br/><br/>
 * @user brad
 * @date 9/15/16
 */
public class TrustInteroperabilityProfileDaoImpl extends AbstractDaoImpl implements TrustInteroperabilityProfileDao {
    //====================================================================================================================
    //  STATIC VARIABLES
    //====================================================================================================================
    private static final Logger log = Logger.getLogger(TrustInteroperabilityProfileDaoImpl.class);
    //====================================================================================================================
    //  CONSTRUCTORS
    //====================================================================================================================
    public TrustInteroperabilityProfileDaoImpl(DataSourceConnectionSource _dataSourceConnectionSource){
        this.dataSourceConnectionSource = _dataSourceConnectionSource;
        try {
            this.tipCacheDao = DaoManager.createDao(this.dataSourceConnectionSource, TrustInteroperabilityProfileCache.class);
            this.dataObjectContentDao = DaoManager.createDao(this.dataSourceConnectionSource, DataObjectContent.class);
        }catch(Throwable t){
            log.error("Cannot create DAO objects!", t);
            throw new RuntimeException("Cannot create DAO!", t);
        }
    }
    //====================================================================================================================
    //  INSTANCE VARIABLES
    //====================================================================================================================
    private DataSourceConnectionSource dataSourceConnectionSource;
    private Dao<TrustInteroperabilityProfileCache, Integer> tipCacheDao = null;
    private Dao<DataObjectContent, Integer> dataObjectContentDao = null;
    //====================================================================================================================
    //  GETTERS
    //====================================================================================================================

    //====================================================================================================================
    //  SETTERS
    //====================================================================================================================

    //====================================================================================================================
    //  HELPER METHODS
    //====================================================================================================================
    protected Object doSingle(Dao dao, String fieldName, Object value) throws SQLException {
        List cacheObjs = dao.queryForEq(fieldName, value);
        if( cacheObjs != null && cacheObjs.size() > 0 ){
            return cacheObjs.get(0);
        }else{
            log.debug("Could not find anything with "+fieldName+" = "+value);
            return null;
        }
    }
    protected List<Object> doList(Dao dao, String fieldName, Object value) throws SQLException {
        List cacheObjs = dao.queryForEq(fieldName, value);
        if( cacheObjs == null ) {
            cacheObjs = new ArrayList();
        }
        return cacheObjs;
    }

    /**
     * Converts a {@link TrustInteroperabilityProfileCache} object into a {@link TrustInteroperabilityProfile} object, by finding the
     * {@link DataObjectContent} for the shared identifier URL.  Note that this method touches the database every time.
     *
     * FIXME: We could cache things in memory to speed things up.  We sure could hold plenty of them...
     */
    private TrustInteroperabilityProfile toTrustInteroperabilityProfile(TrustInteroperabilityProfileCache cacheTip) throws SQLException, ResolveException {
        log.debug("Finding cached JSON for TIP: "+cacheTip.getIdentifierURL());
        DataObjectContent doc = (DataObjectContent) doSingle(this.dataObjectContentDao, "identifier_url", cacheTip.getIdentifierURL());
        if( doc == null ){
            log.error("Cannot find content for cache: "+cacheTip.getIdentifierURL());
            throw new UnsupportedOperationException("Cannot find content for cache: "+cacheTip.getIdentifierURL());
        }

        log.debug("Parsing cached JSON for TIP: "+cacheTip.getIdentifierURL());
        return FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(doc.getContent(), false);
    }

    //====================================================================================================================
    //  INTERFACE METHODS
    //====================================================================================================================
    @Override
    public List<TrustInteroperabilityProfile> listCacheErrors() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Integer count() {
        try {
            return (int) this.tipCacheDao.countOf();
        }catch(SQLException sqle){
            log.error("Error getting object counts for TIPs!", sqle);
            throw new UnsupportedOperationException("Error getting object counts for TIPs!", sqle);
        }
    }

    @Override
    public TrustInteroperabilityProfile get(String identifier) {
        try {
            TrustInteroperabilityProfileCache cachedTip = (TrustInteroperabilityProfileCache) doSingle(this.tipCacheDao, TrustInteroperabilityProfileCache.COL_IDENTIFIER_URL, identifier);
            if( cachedTip == null ){
                log.warn("TIP '"+identifier+"' is not cached!");
                return null;
            }

            log.debug("Found cache object for: "+identifier+" -> ["+cachedTip.getName()+", v"+cachedTip.getVersion()+"]");
            return toTrustInteroperabilityProfile(cachedTip);
        }catch(SQLException sqle){
            log.error("SQL Error while finding TrustInteroperabilityProfileCache.identifier_url = "+identifier, sqle);
            throw new RuntimeException("SQL Error while finding TrustInteroperabilityProfileCache.identifier_url = "+identifier, sqle);
        }catch(ResolveException re){
            log.error("error while parsing cached json for TIP = "+identifier, re);
            throw new RuntimeException("error while parsing cached json for TIP = "+identifier, re);
        }
    }

    @Override
    public TrustInteroperabilityProfile get(String name, String version) {
        try {
            List<TrustInteroperabilityProfileCache> matchingCacheObjects = new ArrayList<>();
            List cachedTipsWithName = doList(this.tipCacheDao, TrustInteroperabilityProfileCache.COL_NAME, name);
            if( cachedTipsWithName != null && cachedTipsWithName.size() > 0 ){
                for( Object obj : cachedTipsWithName ){
                    TrustInteroperabilityProfileCache tipc = (TrustInteroperabilityProfileCache) obj;
                    if( tipc.getVersion().equals(version) ){
                        matchingCacheObjects.add(tipc);
                    }
                }

            }

            if( matchingCacheObjects.size() == 0 ){
                log.debug("No TIP with name="+name+" and version="+version+" was found.");
                return null;
            }else if( matchingCacheObjects.size() > 1 ){
                log.error("There are multiple TIPs with name="+name+" and version="+version+" in the database.");
                throw new UnsupportedOperationException("There are multiple TIPs with name="+name+" and version="+version+" in the database.");
            }

            TrustInteroperabilityProfileCache cachedTip = matchingCacheObjects.get(0);

            log.debug("Found cache object for: "+name+", v"+version+" -> ["+cachedTip.getIdentifierURL()+"]");
            return toTrustInteroperabilityProfile(cachedTip);

        }catch(SQLException sqle){
            log.error("SQL Error while finding TrustInteroperabilityProfileCache.name = "+name+" and version="+version, sqle);
            throw new RuntimeException("SQL Error while finding TrustInteroperabilityProfileCache.name = "+name+" and version="+version, sqle);
        }catch(ResolveException re){
            log.error("error while parsing cached json for TIP.name = "+name+" and version="+version, re);
            throw new RuntimeException("error while parsing cached json for TIP.name = "+name+" and version="+version, re);
        }
    }

    @Override
    public List<TrustInteroperabilityProfile> list(Integer offset, Integer count, SortField sortField, Boolean ascending) {
        try {
            QueryBuilder<TrustInteroperabilityProfileCache, Integer> queryBuilder = this.tipCacheDao.queryBuilder();
            queryBuilder.offset((long) offset);
            queryBuilder.limit((long) count);
            queryBuilder.orderBy(TrustInteroperabilityProfileCache.toColumn(sortField), ascending);

            List<TrustInteroperabilityProfileCache> results = queryBuilder.query();
            if( results == null )
                results = new ArrayList<>();


            List<TrustInteroperabilityProfile> actualResults = new ArrayList<>();
            for( TrustInteroperabilityProfileCache tipc : results ){
                actualResults.add(toTrustInteroperabilityProfile(tipc));
            }

            return actualResults;
        }catch(Exception e){
            log.error("", e);
            throw new UnsupportedOperationException("", e);
        }
    }

    @Override
    public SearchResult<TrustInteroperabilityProfile> doSearch(String searchText, Integer count) {
        log.debug("Performing search on string: "+searchText);
        Map<String, List<String>> searchTerms = parseTerms(searchText);
        Map<String, List<TrustInteroperabilityProfileCache>> matchingProfiles = new HashMap<>();
        for( String term : searchTerms.get("exact") ) {
            try {
                matchingProfiles.put(term, searchCacheExact(term));
            }catch(Exception e){
                log.error("Error searching for exact term: "+term, e);
            }
        }
        for( String term : searchTerms.get("regular") ) {
            try{
                matchingProfiles.put(term, searchCacheRegular(term));
            }catch(Exception e){
                log.error("Error searching for regular term: "+term, e);
            }
        }

        log.debug("Merging results (ie, creating map of TIP -> number)...");
        // Now we merge based on results.  First, we build a map of all TrustInteroperabilityProfileCache objects, with a count of occurrences.
        Map<String, TrustInteroperabilityProfileCache> tipCachesByIdentifierUrl = new HashMap<>();
        // Using the identifier URL as the key because TIP Cache objects themselves
        // weren't being recognized as equal in order to merge and count properly.
        Map<String, Integer> countMap = new HashMap<>();
        for (List<TrustInteroperabilityProfileCache> tipCacheList : matchingProfiles.values()) {
            for (TrustInteroperabilityProfileCache tipCache : tipCacheList) {
                tipCachesByIdentifierUrl.put(tipCache.getIdentifierURL(), tipCache);
                Integer tipCacheCount = countMap.get(tipCache.getIdentifierURL());
                if (tipCacheCount == null) { tipCacheCount = 0; }
                countMap.put(tipCache.getIdentifierURL(), tipCacheCount + 1);
            }
        }
        
        // convert all TIP Caches to TIPs
        List<TrustInteroperabilityProfile> unsortedTips = new ArrayList<>();
        for (TrustInteroperabilityProfileCache tipCache : tipCachesByIdentifierUrl.values()) {
            try {
                unsortedTips.add(toTrustInteroperabilityProfile(tipCache));
            }catch(Exception e){
                log.error("Unable to convert TIP cache to TIP: "+tipCache.getIdentifierURL(), e);
            }
        }
        
        // sort
        List<TrustInteroperabilityProfile> sortedTips = getSortedTipSearchResults(unsortedTips, countMap);
        
        // limit and return
        int resultCount = Math.min(count, sortedTips.size());
        List<TrustInteroperabilityProfile> matchingTips = new ArrayList<>(sortedTips.subList(0, resultCount));
        return new SinglePageSearchResultTIP(matchingTips);
    }
    
    public List<TrustInteroperabilityProfile> getSortedTipSearchResults(
        List<TrustInteroperabilityProfile> tips,
        final Map<String, Integer> countMap
    ) {
        ArrayList<TrustInteroperabilityProfile> result = new ArrayList<>(tips);
        
        final Map<URI, TipStatistics> tipStatisticsMap;
        try {
            TipStatistics.TipStatisticsCache tipStatisticsCache = new TipStatistics.DaoTipStatisticsCache(
                new TrustmarkDefinitionDaoImpl(this.dataSourceConnectionSource),
                this
            );
            tipStatisticsMap = TipStatistics.gatherStatistics(tips, tipStatisticsCache);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        Collections.sort(result, new Comparator<TrustInteroperabilityProfile>() {
            @Override
            public int compare(TrustInteroperabilityProfile x, TrustInteroperabilityProfile y) {
                // Nulls come last
                final int X_NULL = 1;
                final int Y_NULL = -1;
                
                if (x == y) { return 0; }
                if (x == null) { return X_NULL; }
                if (y == null) { return Y_NULL; }
                
                // Compare first by keyword match count (descending)
                int result;
                Integer countX = countMap.get(x.getIdentifier().toString());
                Integer countY = countMap.get(y.getIdentifier().toString());
                if (countX == null) { return X_NULL; }
                if (countY == null) { return Y_NULL; }
                result = Integer.compare(countX, countY) * -1;
                if (result != 0) { return result; }
                
                // Compare next by total known reference count (descending)
                TipStatistics statsX = tipStatisticsMap.get(x.getIdentifier());
                TipStatistics statsY = tipStatisticsMap.get(y.getIdentifier());
                if (statsX == null) { return X_NULL; }
                if (statsY == null) { return Y_NULL; }
                result = TipStatistics.KNOWN_REFERENCE_COUNT.DESC().compare(statsX, statsY);
                if (result != 0) { return result; }
                
                // Compare finally by name (ascending)
                return String.CASE_INSENSITIVE_ORDER.compare(x.getName(), y.getName());
            }
        });
        
        return result;
    }


    private List<TrustInteroperabilityProfileCache> searchCacheExact(String exactPhrase) throws SQLException {
        QueryBuilder<TrustInteroperabilityProfileCache, Integer> queryBuilder = this.tipCacheDao.queryBuilder();
        Where<TrustInteroperabilityProfileCache, Integer> where = queryBuilder.where();
        // Note that like is case insensitive for many databases (mysql included).  If we need to do exact only matching,
        //   then we will have to dump to native SQL instead.
        where.or(
                where.like(TrustInteroperabilityProfileCache.COL_NAME,           "%"+exactPhrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_DESCRIPTION,    "%"+exactPhrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_IDENTIFIER_URL, "%"+exactPhrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_ORG_NAME,       "%"+exactPhrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_ORG_ID,         "%"+exactPhrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_VERSION,        "%"+exactPhrase+"%")
        );
        return queryBuilder.query();
    }

    private List<TrustInteroperabilityProfileCache> searchCacheRegular(String phrase) throws SQLException {
        QueryBuilder<TrustInteroperabilityProfileCache, Integer> queryBuilder = this.tipCacheDao.queryBuilder();
        Where<TrustInteroperabilityProfileCache, Integer> where = queryBuilder.where();
        // Note that like is case insensitive for many databases (mysql included).  If we need to do exact only matching,
        //   then we will have to dump to native SQL instead.
        where.or(
                where.like(TrustInteroperabilityProfileCache.COL_NAME,           "%"+phrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_DESCRIPTION,    "%"+phrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_IDENTIFIER_URL, "%"+phrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_ORG_NAME,       "%"+phrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_ORG_ID,         "%"+phrase+"%"),
                where.like(TrustInteroperabilityProfileCache.COL_VERSION,        "%"+phrase+"%")
        );
        return queryBuilder.query();
    }


    @Override
    public Class<TrustInteroperabilityProfile> getType() {
        return TrustInteroperabilityProfile.class;
    }
    @Override
    public List<TrustInteroperabilityProfile> list(Integer offset, Integer count) {
        return list(offset, count, SortField.IDENTIFIER, true);
    }
    @Override
    public SearchResult<TrustInteroperabilityProfile> doSearch(String searchText) {
        return doSearch(searchText, 25);
    }


}/* end TrustInteroperabilityProfileDaoImpl */