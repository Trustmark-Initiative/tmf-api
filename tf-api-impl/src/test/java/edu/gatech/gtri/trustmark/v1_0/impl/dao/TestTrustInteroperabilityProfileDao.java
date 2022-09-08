package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustInteroperabilityProfileDao;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Does some testing on {@link DaoManagerFactory}
 * <br/><br/>
 * @author brad
 * @date 9/12/16
 */
public class TestTrustInteroperabilityProfileDao extends AbstractDaoTest {
    public static final Logger logger = LoggerFactory.getLogger(TestTrustInteroperabilityProfileDao.class);

    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testTIPDaoGetByIdentifier() throws Exception {
        logger.info("Testing the TIP Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        TrustInteroperabilityProfile tip = tipDao.get("https://trustmark.gtri.gatech.edu/operational-pilot/trust-interoperability-profiles/ficam-loa-2-profile/1.0/");
        assertThat(tip, notNullValue());
        assertThat(tip.getName(), equalTo("FICAM LOA 2 Profile"));
        assertThat(tip.getVersion(), equalTo("1.0"));

        logger.info("Successfully tested that we can get a TIP by identifier URL");
    }

    @Test
    public void testTIPDaoGetByIdentifierDoesntError() throws Exception {
        logger.info("Testing the TIP Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        TrustInteroperabilityProfile tip = tipDao.get("___SHOULD_NEVER_EXIST___");
        assertThat(tip, nullValue());

        logger.info("Successfully tested the not existing tip case.");
    }

    @Test
    public void testTIPDaoGetByNameAndVersion() throws Exception {
        logger.info("Testing the TIP Cache...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        TrustInteroperabilityProfile tip = tipDao.get("FICAM LOA 2 Profile", "1.0");
        assertThat(tip, notNullValue());
        assertThat(tip.getIdentifier().toString(), equalTo("https://trustmark.gtri.gatech.edu/operational-pilot/trust-interoperability-profiles/ficam-loa-2-profile/1.0/"));

        logger.info("Successfully tested we can get a TIP by name and version");
    }

    @Test
    public void testTIPDaoCounts() throws Exception {
        logger.info("Testing the TIPDao Counts...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        assertThat(tipDao.count(), equalTo(10));

        logger.info("Successfully tested TIPDao Count operations");
    }

    @Test
    public void testTIPDaoList() throws Exception {
        logger.info("Testing the TIP Cache list capability...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        List<TrustInteroperabilityProfile> tipList = tipDao.list(0, 5);
        assertThat(tipList.size(), equalTo(5));
        tipList = tipDao.list(5, 5);
        assertThat(tipList.size(), equalTo(5));
        tipList = tipDao.list(8, 5);
        assertThat(tipList.size(), equalTo(2)); // Because there is only 10 of them.

        logger.info("Successfully tested a that we can list TIPs!");
    }

    @Test
    public void testTrustmarkDefinitionSearch() throws Exception {
        logger.info("Testing the TD Cache search capability...");
        DaoManager daoManager = getInitializedDaoManager();

        logger.debug("Getting TIPDao...");
        TrustInteroperabilityProfileDao tipDao = daoManager.getTrustInteroperabilityProfileDao();
        assertThat(tipDao, notNullValue());

        List<TrustInteroperabilityProfile> tips = tipDao.doSearch("ficam").getResults();
        assertThat(tips, notNullValue());
        assertThat(tips.size(), equalTo(8));

        logger.info("Successfully tested a that we can search TIPs!");
    }


}/* end TestDaoManagerFactory */
