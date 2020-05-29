package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.dao.DaoException;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;

import javax.sql.DataSource;

/**
 * Default implementation of a {@link DaoManagerFactory}
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class DaoManagerFactoryImpl implements DaoManagerFactory {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static Boolean SYNC_VAR = Boolean.FALSE;
    private static DaoManager staticInstance = null;
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    protected static DaoManager getStaticInstance() {
        if( staticInstance == null){
            throw new RuntimeException("The DaoManager has not yet been initialized.");
        }
        return staticInstance;
    }
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public DaoManagerFactoryImpl(){}
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  INTERFACE METHODS
    //==================================================================================================================
    @Override
    public DaoManager getInstance(DataSource dataSource) throws DaoException {
        DaoManager manager = new DaoManagerImpl(dataSource);
        synchronized (SYNC_VAR) {
            if (staticInstance == null) {
                staticInstance = manager;
            }
        }
        return manager;
    }

    @Override
    public DaoManager getInstance() {
        synchronized (SYNC_VAR) {
            return getStaticInstance();
        }
    }

}/* end DaoManagerFactoryImpl */