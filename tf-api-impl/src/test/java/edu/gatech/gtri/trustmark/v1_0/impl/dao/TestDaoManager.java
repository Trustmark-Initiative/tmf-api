package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustInteroperabilityProfileDao;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustmarkDefinitionDao;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.DataObjectContent;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustInteroperabilityProfileCache;
import edu.gatech.gtri.trustmark.v1_0.impl.dao.objects.TrustmarkDefinitionCache;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Does some testing on {@link DaoManagerFactory}
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class TestDaoManager extends AbstractDaoTest {
    public static final Logger logger = Logger.getLogger(TestDaoManager.class);

    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testGetTIPDao() throws Exception {
        logger.info("Testing that we can create a TIP Dao...");
        DaoManagerFactory factory = FactoryLoader.getInstance(DaoManagerFactory.class);
        assertThat(factory, notNullValue());

        DaoManager daoManager = factory.getInstance(getDataSource());
        assertThat(daoManager, notNullValue());

        TrustInteroperabilityProfileDao dao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(dao, notNullValue());

        logger.info("Successfully tested creation of TIP Dao.");
    }


    @Test
    public void testGetTDDao() throws Exception {
        logger.info("Testing that we can create a TD Dao...");
        DaoManagerFactory factory = FactoryLoader.getInstance(DaoManagerFactory.class);
        assertThat(factory, notNullValue());

        DaoManager daoManager = factory.getInstance(getDataSource());
        assertThat(daoManager, notNullValue());

        TrustmarkDefinitionDao dao = daoManager.getTrustmarkDefinitionDao();
        assertThat(dao, notNullValue());

        logger.info("Successfully tested creation of TD Dao.");
    }
    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================

}/* end TestDaoManagerFactory */