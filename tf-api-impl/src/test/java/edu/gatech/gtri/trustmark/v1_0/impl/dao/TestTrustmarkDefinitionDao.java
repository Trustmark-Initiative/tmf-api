package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustmarkDefinitionDao;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Does some testing on {@link DaoManagerFactory}
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class TestTrustmarkDefinitionDao extends AbstractDaoTest {
    public static final Logger logger = Logger.getLogger(TestTrustmarkDefinitionDao.class);

    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testTrustmarkDefinitionGetByIdentifier() throws Exception {
        logger.info("Testing the TD Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        TrustmarkDefinition td = tdDao.get("https://trustmark.gtri.gatech.edu/operational-pilot/trustmark-definitions/acceptance-criteria/1.0/");
        assertThat(td, notNullValue());
        assertThat(td.getMetadata().getName(), equalTo("Acceptance Criteria"));
        assertThat(td.getMetadata().getVersion(), equalTo("1.0"));

        logger.info("Successfully tested that we can get a TD by identifier URL");
    }

    @Test
    public void testTrustmarkDefinitionGetByIdentifierDoesntError() throws Exception {
        logger.info("Testing the TD Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        TrustmarkDefinition td = tdDao.get("___SHOULD_NEVER_EXIST___");
        assertThat(td, nullValue());

        logger.info("Successfully tested the not existing TD case.");
    }


    @Test
    public void testTrustmarkDefinitionGetByNameAndVersion() throws Exception {
        logger.info("Testing the TD Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        TrustmarkDefinition td = tdDao.get("Acceptance Criteria", "1.0");
        assertThat(td, notNullValue());
        assertThat(td.getMetadata().getIdentifier().toString(), equalTo("https://trustmark.gtri.gatech.edu/operational-pilot/trustmark-definitions/acceptance-criteria/1.0/"));

        logger.info("Successfully tested we can get a TD by name and version");
    }

    @Test
    public void testTrustmarkDefinitionCounts() throws Exception {
        logger.info("Testing the TD Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        assertThat(tdDao.count(), equalTo(10));

        logger.info("Successfully tested a TD Count operations");
    }


    @Test
    public void testTrustmarkDefinitionList() throws Exception {
        logger.info("Testing the TD Cache list capability...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        List<TrustmarkDefinition> tdList = tdDao.list(0, 5);
        assertThat(tdList.size(), equalTo(5));
        tdList = tdDao.list(5, 5);
        assertThat(tdList.size(), equalTo(5));
        tdList = tdDao.list(8, 5);
        assertThat(tdList.size(), equalTo(2)); // Because there is only 10 of them.

        logger.info("Successfully tested a that we can list TDs!");
    }

    @Test
    public void testTrustmarkDefinitionSearch() throws Exception {
        logger.info("Testing the TD Cache search capability...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TrustmarkDefinitionDao...");
        TrustmarkDefinitionDao tdDao = daoManager.getTrustmarkDefinitionDao();
        assertThat(tdDao, notNullValue());

        List<TrustmarkDefinition> tds = tdDao.doSearch("acceptance").getResults();
        assertThat(tds, notNullValue());
        assertThat(tds.size(), equalTo(3));

        logger.info("Successfully tested a that we can search TDs!");
    }



}/* end TestDaoManagerFactory */