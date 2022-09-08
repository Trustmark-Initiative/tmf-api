package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Does some testing on {@link edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory}
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class TestDaoManagerFactory extends AbstractDaoTest {
    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testInstantiation() {
        logger.info("Testing that we can create a DaoManagerFactory...");
        DaoManagerFactory factory = FactoryLoader.getInstance(DaoManagerFactory.class);
        assertThat(factory, notNullValue());
        assertThat(factory, instanceOf(DaoManagerFactoryImpl.class));
        logger.info("Successfully created a DaoManagerFactory!");
    }

    @Test
    public void testCreateDaoManager() throws Exception {
        logger.info("Testing that we can create a DaoManagerFactory...");
        DaoManagerFactory factory = FactoryLoader.getInstance(DaoManagerFactory.class);
        assertThat(factory, notNullValue());

        DaoManager daoManager = factory.getInstance(getDataSource());
        assertThat(daoManager, notNullValue());

        logger.info("Successfully created a DaoManagerFactory!");
    }


    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================



}/* end TestDaoManagerFactory */
