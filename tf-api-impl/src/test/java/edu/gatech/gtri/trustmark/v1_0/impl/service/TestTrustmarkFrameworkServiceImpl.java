package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.RemoteServiceNetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.service.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 2/4/16.
 */
public class TestTrustmarkFrameworkServiceImpl extends AbstractTest {

    protected static final String EXAMPLE_URL = "https://trustmark.example.org/test";

    private static NetworkDownloader oldNetworkDownloader = null;
    protected static NetworkDownloader testNetworkDownloader = new RemoteServiceNetworkDownloader();

    @BeforeClass
    public static void setTestNetworkDownloader() {
        synchronized (EXAMPLE_URL){
            if(oldNetworkDownloader == null && getBaseUrl().equalsIgnoreCase(EXAMPLE_URL) ) {
                oldNetworkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);
                FactoryLoader.register(NetworkDownloader.class, testNetworkDownloader);
            }
        }
    }

    @AfterClass
    public static void restoreNetworkDownloader() {
        synchronized (EXAMPLE_URL){
            if (oldNetworkDownloader != null && getBaseUrl().equalsIgnoreCase(EXAMPLE_URL) ) {
                FactoryLoader.register(NetworkDownloader.class, oldNetworkDownloader);
                oldNetworkDownloader = null;
            }
        }
    }


    protected static String getBaseUrl() {
        return System.getProperty("tmf.remote.ws", EXAMPLE_URL);
    }
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testTrustmarkFramworkServiceResolution() {
        logger.info("Resolving a TrustmarkFrameworkService...");
        Object factoryObj = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class);
        assertThat(factoryObj, notNullValue());
        assertThat(factoryObj, instanceOf(TrustmarkFrameworkServiceFactoryImpl.class));
        TrustmarkFrameworkServiceFactory factory = (TrustmarkFrameworkServiceFactory) factoryObj;

        Object serviceObj = factory.createService("http://www.google.com");
        assertThat(serviceObj, notNullValue());
        assertThat(serviceObj, instanceOf(TrustmarkFrameworkServiceImpl.class));
    }//end testTrustmarkFramworkServiceResolution()

    @Test
    public void testGetStatus() throws Exception {
        logger.info("Checking status on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());
        assertThat(localhost.getBaseUrl().toString(), equalTo(getBaseUrl()));

        logger.debug("Querying status...");
        RemoteStatus status = localhost.getStatus();
        assertThat(status, notNullValue());

        RemoteVersionSet rvs = status.getCurrentVersionSet();
        assertThat(rvs, notNullValue());
        assertThat(rvs.getName(), equalTo("VS_20170411_1"));
        assertThat(rvs.getPredecessorName(), equalTo("VS_20170411"));
//        assertThat(rvs.getReleasedDate().getTime(), equalTo(1490672864000l));
        assertThat(rvs.isEditable(), equalTo(false));
        assertThat(rvs.isProduction(), equalTo(true));
        assertThat(rvs.getDownloadAllUrl(), notNullValue());
        assertThat(rvs.getDownloadAllUrl().toString(), equalTo("https://trustmark.example.org/test/downloadAll/build/VS_20170411_1.json"));

        RemoteOrganization remoteOrganization = status.getOrganization();
        assertThat(remoteOrganization, notNullValue());
        assertThat(remoteOrganization.getName(), equalTo("Trustmark Initiative Core"));
        assertThat(remoteOrganization.getIdentifier().toString(), equalTo("https://artifacts.trustmarkinitiative.org/core"));
        assertThat(remoteOrganization.getAbbreviation(), equalTo("TMI Core"));
        assertThat(remoteOrganization.getLogoImagePath(), equalTo("/logo.png"));

        assertThat(status.getSupportsTrustmarkDefinitions(), equalTo(true));
        assertThat(status.getTrustmarkDefinitionCountAll(), equalTo(10));
        assertThat(status.getTrustmarkDefinitionCountNotDeprecated(), equalTo(8));
        assertThat(status.getMostRecentTrustmarkDefinitionDate(), notNullValue());
        assertThat(status.getMostRecentTrustmarkDefinitionDate().getTime(), greaterThan(1000000000000l));
        assertThat(status.getTrustmarkDefinitionLocations(), notNullValue());
        assertThat(status.getTrustmarkDefinitionLocations().keySet().size(), equalTo(1));

        assertThat(status.getSupportsTrustInteroperabilityProfiles(), equalTo(true));
        assertThat(status.getTrustInteroperabilityProfileCountAll(), equalTo(10));
        assertThat(status.getMostRecentTrustInteroperabilityProfileDate(), notNullValue());
        assertThat(status.getMostRecentTrustInteroperabilityProfileDate().getTime(), greaterThan(1000000000000l));

        assertThat(status.getSupportsTrustmarks(), equalTo(false));

        assertThat(status.getKeywordCount(), equalTo(5));

        logger.info("Successfully checked status.");
    }

    @Test
    public void testGetTDs() throws Exception {
        logger.info("Getting TrustmarkDefinitions list on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        logger.debug("Querying status...");
        Page<RemoteTrustmarkDefinition> tdPage = localhost.listTrustmarkDefinitions();
        assertThat(tdPage, notNullValue());
        assertThat(tdPage.getCount(), equalTo(10l));
        assertThat(tdPage.getOffset(), equalTo(0l));
        assertThat(tdPage.hasNext(), equalTo(false));

        assertThat(tdPage.getObjects().get(0).getName(), equalTo("Acceptance Criteria"));
        assertThat(tdPage.getObjects().get(0).getVersion(), equalTo("1.0"));

        TrustmarkDefinitionResolver tdResolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);

        for( RemoteTrustmarkDefinition remoteTd : tdPage.getObjects() ){
            URL jsonUrl = remoteTd.getFormats().get("json");
            TrustmarkDefinition td = tdResolver.resolve(jsonUrl);
            assertThat(td, notNullValue());
        }

        logger.info("Successfully retrieved TDs.");
    }

    @Test
    public void testGetTIPs() throws Exception {
        logger.info("Getting TrustInteroperabilityProfiles list on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        logger.debug("Querying status...");
        Page<RemoteTrustInteroperabilityProfile> tipPage = localhost.listTrustInteroperabilityProfiles();
        assertThat(tipPage, notNullValue());
        assertThat(tipPage.getCount(), equalTo(10l));
        assertThat(tipPage.getOffset(), equalTo(0l));
        assertThat(tipPage.hasNext(), equalTo(false));

        assertThat(tipPage.getObjects().get(0).getName(), equalTo("FICAM LOA 2 Profile"));
        assertThat(tipPage.getObjects().get(0).getVersion(), equalTo("1.0"));


        TrustInteroperabilityProfileResolver tipResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);

        for( RemoteTrustInteroperabilityProfile remoteTip : tipPage.getObjects() ){
            URL jsonUrl = remoteTip.getFormats().get("json");
            TrustInteroperabilityProfile tip = tipResolver.resolve(jsonUrl);
            assertThat(tip, notNullValue());
        }

        logger.info("Successfully retrieved TIPs.");
    }

    @Test @Ignore
    public void testSetVersionSet() throws Exception {
        logger.info("Running test testSetVersionSet() on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        localhost.setVersionSet("VS_123");

        logger.info("Successfully tested testSetVersionSet()...");
    }

    @Test
    public void testGetKeywords() throws Exception {
        logger.info("Running test testGetKeywords() on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        List<RemoteKeyword> keywordList = localhost.getKeywords();
        assertThat(keywordList, notNullValue());
        assertThat(keywordList.size(), equalTo(68));

        RemoteKeyword keyword1 = findKeyword(keywordList, "800-53");
        assertThat(keyword1, notNullValue());
        assertThat(keyword1.getTrustmarkDefinitionCount(), equalTo(471));

        // TODO Other tests?

        logger.info("Successfully tested testGetKeywords()...");
    }

    @Test
    public void testDownloadAll() throws Exception {
        logger.info("Running test testDownloadAll() on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        DownloadAllListenerStateImpl listener = new DownloadAllListenerStateImpl();
        Thread t = localhost.downloadAll(listener);
        assertThat(t, notNullValue());
        t.join(); // Wait for download all thread to finish.

        assertThat(listener.getCompletedSuccessfully(), equalTo(true));
        assertThat(listener.getResponse(), notNullValue());
        assertThat(listener.getResponse().getSize(), equalTo(3382644l));



        // TODO Other tests?

        logger.info("Successfully tested testDownloadAll()...");
    }

    @Test
    public void testSearch() throws Exception {
        logger.info("Running test testSearch() on ["+ getBaseUrl() +"]...");

        logger.debug("Creating a new localhost service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        RemoteSearchResult searchResult = localhost.search("test");
        assertThat(searchResult, notNullValue());
        assertThat(searchResult.getQueryString(), equalTo("test"));
        assertThat(searchResult.getSearchTerms().get(0), equalTo("test"));

        assertThat(searchResult.getTrustmarkDefinitionTotalCount(), equalTo(599));
        assertThat(searchResult.getTrustInteroperabilityProfileTotalCount(), equalTo(229));

        assertThat(searchResult.areTrustmarkDefinitionsSuppressed(), equalTo(false));
        assertThat(searchResult.areTrustInteroperabilityProfilesSuppressed(), equalTo(false));

        assertThat(searchResult.getTrustmarkDefinitionMatchCount(), equalTo(4));
        assertThat(searchResult.getTrustInteroperabilityProfileMatchCount(), equalTo(1));

        assertThat(searchResult.getTrustInteroperabilityProfiles().get(0).getName(), equalTo("Corrective Actions Based on System Contingency Plan Test Results"));

        assertThat(searchResult.getTrustmarkDefinitions().get(0).getName(), equalTo("System Contingency Plan Testing"));

        logger.info("Successfully tested testSearch()...");
    }


    @Test
    public void testGetTaxonomy() throws Exception {
        logger.info("Running testGetTaxonomy() on ["+getBaseUrl()+"]...");

        logger.debug("Creating a new service...");
        TrustmarkFrameworkService localhost = FactoryLoader.getInstance(TrustmarkFrameworkServiceFactory.class).createService(getBaseUrl());
        assertThat(localhost, notNullValue());

        RemoteTaxonomy taxonomy = localhost.getTaxonomy();
        assertThat(taxonomy, notNullValue());
        assertThat(taxonomy.getTopLevelTermCount(), equalTo(19));
        assertThat(taxonomy.getTotalTermCount(), equalTo(347));
        assertThat(taxonomy.getTerms().size(), equalTo(19));

        RemoteTaxonomyTerm securityTerm = taxonomy.getTerms().get(18);
        assertThat(securityTerm, notNullValue());
        assertThat(securityTerm.getTerm(), equalTo("Security"));
        assertThat(securityTerm.getChildren().size(), equalTo(1));
        RemoteTaxonomyTerm accessControlTerm = securityTerm.getChildren().get(0);
        assertThat(accessControlTerm, notNullValue());
        assertThat(accessControlTerm.getTerm(), equalTo("Access Control"));

        logger.info("Successfully tested testGetTaxonomy()!");
    }

    //==================================================================================================================
    //  Private Helper Methods
    //==================================================================================================================
    private RemoteKeyword findKeyword(List<RemoteKeyword> keywords, String name){
        for( RemoteKeyword keyword : keywords ) {
            if (keyword.getText().equalsIgnoreCase(name)) {
                return keyword;
            }
        }
        return null;
    }


}//end class TestTrustmarkFrameworkServiceImpl
