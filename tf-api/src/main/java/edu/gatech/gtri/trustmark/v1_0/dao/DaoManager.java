package edu.gatech.gtri.trustmark.v1_0.dao;

import javax.sql.DataSource;

/**
 * Responsible for managing the Dao classes, as well as providing caching methods.
 * <br/><br/>
 * @author brad
 * @date 2016-09-12
 */
public interface DaoManager {

    /**
     * Provides a direct access to the underlying {@link DataSource} that the Dao objects are using.
     */
    public DataSource getDataSource();

    /**
     * Returns access to an instance of {@link TrustInteroperabilityProfileDao}
     */
    public TrustInteroperabilityProfileDao getTrustInteroperabilityProfileDao();

    /**
     * Returns access to an instance of {@link TrustmarkDefinitionDao}.
     */
    public TrustmarkDefinitionDao getTrustmarkDefinitionDao();

}// end DaoManager