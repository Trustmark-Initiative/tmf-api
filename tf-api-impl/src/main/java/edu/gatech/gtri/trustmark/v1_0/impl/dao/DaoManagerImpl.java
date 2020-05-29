package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.gatech.gtri.trustmark.v1_0.dao.*;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.DataObjectContent;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustInteroperabilityProfileCache;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustmarkDefinitionCache;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * TODO: Insert Comment Here
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class DaoManagerImpl implements DaoManager {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(DaoManagerImpl.class);
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public DaoManagerImpl(DataSource _dataSource) {
        this.dataSource = _dataSource;
        _init();
    }
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private DataSource dataSource = null;
    private DataSourceConnectionSource dataSourceConnectionSource = null;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
    public DataSourceConnectionSource getDataSourceConnectionSource() {
        return dataSourceConnectionSource;
    }
    //==================================================================================================================
    //  SETTERS
    //==================================================================================================================

    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    /**
     * Initializes the system.  For example, checks the data source, makes sure the schema is good, etc.
     */
    private void _init(){
        log.info("Initializing new DaoManagerImpl...");

        log.debug("Connecting using ORMLite...");
        try{
            this.dataSourceConnectionSource = new DataSourceConnectionSource(this.getDataSource(), DatabaseUtils.getDatabaseType(this.getDataSource()));
        }catch(SQLException sqle){
            throw new RuntimeException("Cannot initialize connection from data source!", sqle);
        }

        log.info("Creating caching tables...");
        this.createTablesIfNecessary();

    }//end _init();

    /**
     * Creates database tables if they don't exist.
     */
    private void createTablesIfNecessary() {
        try {
            log.debug("Creating TrustmarkDefinitionCache table...");
            TableUtils.createTableIfNotExists(this.getDataSourceConnectionSource(), TrustmarkDefinitionCache.class);
            log.debug("Creating TrustInteroperabilityProfile table...");
            TableUtils.createTableIfNotExists(this.getDataSourceConnectionSource(), TrustInteroperabilityProfileCache.class);
            log.debug("Creating DataObjectContent table...");
            TableUtils.createTableIfNotExists(this.getDataSourceConnectionSource(), DataObjectContent.class);

        }catch(SQLException sqle){
            log.error("Error creating TD/TIP caching tables!", sqle);
            throw new RuntimeException("Error creating TD/TIP Caching tables!", sqle);
        }
    }//end createTablesIfNecessary()

    //==================================================================================================================
    //  INTERFACE METHODS
    //==================================================================================================================
    @Override
    public TrustInteroperabilityProfileDao getTrustInteroperabilityProfileDao() {
        return new TrustInteroperabilityProfileDaoImpl(this.dataSourceConnectionSource);
    }

    @Override
    public TrustmarkDefinitionDao getTrustmarkDefinitionDao() {
        return new TrustmarkDefinitionDaoImpl(this.dataSourceConnectionSource);
    }



}/* end DaoManagerImpl */