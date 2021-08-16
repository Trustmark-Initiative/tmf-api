package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.dao.SearchResult;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustmarkDefinitionDao;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.DataObjectContent;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustmarkDefinitionCache;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.util.*;

/**
 * Implementation of the {@link TrustmarkDefinitionDao} which uses the ORMLite library to interact with the database.
 * <br/><br/>
 * @user brad
 * @date 9/15/16
 */
public class TrustmarkDefinitionDaoImpl extends AbstractDaoImpl implements TrustmarkDefinitionDao {
    //====================================================================================================================
    //  STATIC VARIABLES
    //====================================================================================================================
    private static final Logger log = LogManager.getLogger(TrustmarkDefinitionDaoImpl.class);
    //====================================================================================================================
    //  CONSTRUCTORS
    //====================================================================================================================
    public TrustmarkDefinitionDaoImpl(DataSourceConnectionSource _dataSourceConnectionSource){
        this.dataSourceConnectionSource = _dataSourceConnectionSource;
        try {
            this.trustmarkDefinitionCacheDao = DaoManager.createDao(this.dataSourceConnectionSource, TrustmarkDefinitionCache.class);
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
    private Dao<TrustmarkDefinitionCache, Integer> trustmarkDefinitionCacheDao = null;
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
     * Converts a {@link TrustmarkDefinitionCache} object into a {@link TrustmarkDefinition} object, by finding the
     * {@link DataObjectContent} for the shared identifier URL.  Note that this method touches the database every time.
     *
     * FIXME: We could cache things in memory to speed things up.  We sure could hold alot of them...
     */
    private TrustmarkDefinition toTrustmarkDefinition(TrustmarkDefinitionCache cacheTd) throws SQLException, ResolveException {
        log.debug("Finding cached JSON for TD: "+cacheTd.getIdentifierURL());
        DataObjectContent doc = (DataObjectContent) doSingle(this.dataObjectContentDao, "identifier_url", cacheTd.getIdentifierURL());
        if( doc == null ){
            log.error("Cannot find content for cache: "+cacheTd.getIdentifierURL());
            throw new UnsupportedOperationException("Cannot find content for cache: "+cacheTd.getIdentifierURL());
        }

        log.debug("Parsing cached JSON for TD: "+cacheTd.getIdentifierURL());
        return FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(doc.getContent(), false);
    }

    //====================================================================================================================
    //  INTERFACE METHODS
    //====================================================================================================================
    @Override
    public List<TrustmarkDefinition> listCacheErrors() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Integer count() {
        try {
            return (int) this.trustmarkDefinitionCacheDao.countOf();
        }catch(SQLException sqle){
            log.error("Error counting trustmark definitions!", sqle);
            throw new RuntimeException("Unable to count trustmark definitions!", sqle);
        }
    }

    @Override
    public TrustmarkDefinition get(String identifier) {
        try {
            TrustmarkDefinitionCache cachedTd = (TrustmarkDefinitionCache) doSingle(this.trustmarkDefinitionCacheDao, TrustmarkDefinitionCache.COL_IDENTIFIER_URL, identifier);
            if( cachedTd == null ){
                log.warn("TD '"+identifier+"' is not cached!");
                return null;
            }

            log.debug("Found cache object for: "+identifier+" -> ["+cachedTd.getName()+", v"+cachedTd.getVersion()+"]");
            return toTrustmarkDefinition(cachedTd);
        }catch(SQLException sqle){
            log.error("SQL Error while finding TrustmarkDefinitionCache.identifier_url = "+identifier, sqle);
            throw new RuntimeException("SQL Error while finding TrustmarkDefinitionCache.identifier_url = "+identifier, sqle);
        }catch(ResolveException re){
            log.error("error while parsing cached json for Td = "+identifier, re);
            throw new RuntimeException("error while parsing cached json for Td = "+identifier, re);
        }
    }

    @Override
    public TrustmarkDefinition get(String name, String version) {
        try {
            List<TrustmarkDefinitionCache> matchingCacheObjects = new ArrayList<>();
            List cachedTdsWithName = doList(this.trustmarkDefinitionCacheDao, TrustmarkDefinitionCache.COL_NAME, name);
            if( cachedTdsWithName != null && cachedTdsWithName.size() > 0 ){
                for( Object obj : cachedTdsWithName ){
                    TrustmarkDefinitionCache tdc = (TrustmarkDefinitionCache) obj;
                    if( tdc.getVersion().equals(version) ){
                        matchingCacheObjects.add(tdc);
                    }
                }

            }

            if( matchingCacheObjects.size() == 0 ){
                log.debug("No TD with name="+name+" and version="+version+" was found.");
                return null;
            }else if( matchingCacheObjects.size() > 1 ){
                log.error("There are multiple TDs with name="+name+" and version="+version+" in the database.");
                throw new UnsupportedOperationException("There are multiple TDs with name="+name+" and version="+version+" in the database.");
            }

            TrustmarkDefinitionCache cachedTd = matchingCacheObjects.get(0);

            log.debug("Found cache object for: "+name+", v"+version+" -> ["+cachedTd.getIdentifierURL()+"]");
            return toTrustmarkDefinition(cachedTd);

        }catch(SQLException sqle){
            log.error("SQL Error while finding TrustmarkDefinitionCache.name = "+name+" and version="+version, sqle);
            throw new RuntimeException("SQL Error while finding TrustmarkDefinitionCache.name = "+name+" and version="+version, sqle);
        }catch(ResolveException re){
            log.error("error while parsing cached json for Td.name = "+name+" and version="+version, re);
            throw new RuntimeException("error while parsing cached json for Td.name = "+name+" and version="+version, re);
        }
    }

    @Override
    public List<TrustmarkDefinition> list(Integer offset, Integer count, SortField sortField, Boolean ascending) {
        try {
            QueryBuilder<TrustmarkDefinitionCache, Integer> queryBuilder = this.trustmarkDefinitionCacheDao.queryBuilder();
            queryBuilder.offset((long) offset);
            queryBuilder.limit((long) count);
            queryBuilder.orderBy(TrustmarkDefinitionCache.toColumn(sortField), ascending);

            List<TrustmarkDefinitionCache> results = queryBuilder.query();
            if( results == null )
                results = new ArrayList<>();


            List<TrustmarkDefinition> actualResults = new ArrayList<>();
            for( TrustmarkDefinitionCache tdc : results ){
                actualResults.add(toTrustmarkDefinition(tdc));
            }

            return actualResults;
        }catch(Exception e){
            log.error("", e);
            throw new UnsupportedOperationException("", e);
        }
    }

    @Override
    public SearchResult<TrustmarkDefinition> doSearch(String searchText, Integer count) {
        log.debug("Performing search on string: "+searchText);
        Map<String, List<String>> searchTerms = parseTerms(searchText);
        Map<String, List<TrustmarkDefinitionCache>> matchingDefinitions = new HashMap<>();
        for( String term : searchTerms.get("exact") ) {
            try {
                matchingDefinitions.put(term, searchCacheExact(term, count));
            }catch(Exception e){
                log.error("Error searching for exact term: "+term, e);
            }
        }
        for( String term : searchTerms.get("regular") ) {
            try{
                matchingDefinitions.put(term, searchCacheRegular(term, count));
            }catch(Exception e){
                log.error("Error searching for regular term: "+term, e);
            }
        }

        log.debug("Merging results (ie, creating map of TD -> number)...");
        // Now we merge based on results.  First, we build a map of all TrustmarkDefinitionCache objects, with a count of occurrences.
        Map<TrustmarkDefinitionCache, Integer> countMap = new HashMap<>();
        for( Map.Entry<String, List<TrustmarkDefinitionCache>> entry : matchingDefinitions.entrySet() ){
            for( TrustmarkDefinitionCache cacheTd : entry.getValue() ){
                Integer cacheTdCount = 1;
                if( countMap.containsKey(cacheTd) ){
                    cacheTdCount = countMap.get(cacheTd);
                    cacheTdCount++;
                }
                countMap.put(cacheTd, cacheTdCount);
            }
        }

        List<Map.Entry> sortedEntries = getSortedByValueEntriesList(countMap);

        List<TrustmarkDefinition> matchingTds = new ArrayList<>();
        for( int i = 0; i < sortedEntries.size() && i < count; i++ ){
            TrustmarkDefinitionCache tdCache = (TrustmarkDefinitionCache) sortedEntries.get(i).getKey();
            try {
                matchingTds.add(toTrustmarkDefinition(tdCache));
            }catch(Exception e){
                log.error("Unable to convert TD cache to TD: "+tdCache.getIdentifierURL(), e);
            }
        }

        return new SinglePageSearchResultTD(matchingTds);
    }

    // @see http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
    public static List getSortedByValueEntriesList( Map map ) {
        List<Map.Entry> list = new ArrayList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry>() {
                @Override
                public int compare( Map.Entry o1, Map.Entry o2 ) {
                    return ( (Comparable) o1.getValue() ).compareTo( o2.getValue() );
                }
            });
        return list;
    }//end getSortedByValueEntriesList()


    private List<TrustmarkDefinitionCache> searchCacheExact(String exactPhrase, Integer limit) throws SQLException {
        QueryBuilder<TrustmarkDefinitionCache, Integer> queryBuilder = this.trustmarkDefinitionCacheDao.queryBuilder();
        queryBuilder.limit((long) limit);
        Where<TrustmarkDefinitionCache, Integer> where = queryBuilder.where();
        // Note that like is case insensitive for many databases (mysql included).  If we need to do exact only matching,
        //   then we will have to dump to native SQL instead.
        where.or(
                where.like(TrustmarkDefinitionCache.COL_NAME,           "%"+exactPhrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_DESCRIPTION,    "%"+exactPhrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_IDENTIFIER_URL, "%"+exactPhrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_ORG_NAME,       "%"+exactPhrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_ORG_ID,         "%"+exactPhrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_VERSION,        "%"+exactPhrase+"%")
        );
        return queryBuilder.query();
    }

    private List<TrustmarkDefinitionCache> searchCacheRegular(String phrase, Integer limit) throws SQLException {
        QueryBuilder<TrustmarkDefinitionCache, Integer> queryBuilder = this.trustmarkDefinitionCacheDao.queryBuilder();
        queryBuilder.limit((long) limit);
        Where<TrustmarkDefinitionCache, Integer> where = queryBuilder.where();
        // Note that like is case insensitive for many databases (mysql included).  If we need to do exact only matching,
        //   then we will have to dump to native SQL instead.
        where.or(
                where.like(TrustmarkDefinitionCache.COL_NAME,           "%"+phrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_DESCRIPTION,    "%"+phrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_IDENTIFIER_URL, "%"+phrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_ORG_NAME,       "%"+phrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_ORG_ID,         "%"+phrase+"%"),
                where.like(TrustmarkDefinitionCache.COL_VERSION,        "%"+phrase+"%")
        );
        return queryBuilder.query();
    }


    @Override
    public Class<TrustmarkDefinition> getType() {
        return TrustmarkDefinition.class;
    }
    @Override
    public List<TrustmarkDefinition> list(Integer offset, Integer count) {
        return list(offset, count, SortField.IDENTIFIER, true);
    }
    @Override
    public SearchResult<TrustmarkDefinition> doSearch(String searchText) {
        return doSearch(searchText, 25);
    }


}/* end TrustmarkDefinitionDaoImpl */