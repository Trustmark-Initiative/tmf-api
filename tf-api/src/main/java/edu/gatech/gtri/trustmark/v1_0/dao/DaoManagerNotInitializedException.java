package edu.gatech.gtri.trustmark.v1_0.dao;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/27/17
 */
public class DaoManagerNotInitializedException extends DaoException {

    public DaoManagerNotInitializedException(){
        super("The DaoManager has not yet been initialized.  You must first call DaoManagerFactory#getInstance(DataSource)");
    }



}/* end DaoManagerNotInitializedException */