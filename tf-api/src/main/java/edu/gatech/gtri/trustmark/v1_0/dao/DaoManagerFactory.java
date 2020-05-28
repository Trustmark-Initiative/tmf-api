package edu.gatech.gtri.trustmark.v1_0.dao;

import javax.sql.DataSource;

/**
 * Responsible for creating instances of {@link DaoManager}.
 * <br/><br/>
 * @author brad
 * @date 2016-09-12
 */
public interface DaoManagerFactory {

    /**
     * Provides access to a DaoManager initialized previously.  If you call this method without first calling
     * {@link DaoManagerFactory#getInstance(DataSource)} then expect an error message.
     */
    public DaoManager getInstance() throws DaoManagerNotInitializedException;

    /**
     * Provides access to the DaoManager.
     */
    public DaoManager getInstance(DataSource dataSource) throws DaoException;

}// end DaoManager