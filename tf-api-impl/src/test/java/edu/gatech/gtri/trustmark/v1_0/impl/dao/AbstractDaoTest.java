package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManager;
import edu.gatech.gtri.trustmark.v1_0.dao.DaoManagerFactory;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.RemoteServiceNetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;
import org.junit.*;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/13/16
 */
public abstract class AbstractDaoTest extends AbstractTest  {

    protected static final String EXAMPLE_URL = "https://trustmark.example.org/test";

    private static NetworkDownloader oldNetworkDownloader = null;
    protected static NetworkDownloader testNetworkDownloader = new RemoteServiceNetworkDownloader();

    protected static boolean doTestWithOperationalPilot() {
        return System.getProperty("use.operational.pilot") != null &&
                System.getProperty("use.operational.pilot").equalsIgnoreCase("true");
    }

    @BeforeClass
    public static void setTestNetworkDownloader() {
        synchronized (EXAMPLE_URL){
            if( !doTestWithOperationalPilot() ){
                if (oldNetworkDownloader == null) {
                    oldNetworkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);
                    FactoryLoader.register(NetworkDownloader.class, testNetworkDownloader);
                }
            }
        }
    }

    @AfterClass
    public static void restoreNetworkDownloader() {
        synchronized (EXAMPLE_URL){
            if( !doTestWithOperationalPilot() ) {
                if (oldNetworkDownloader != null) {
                    FactoryLoader.register(NetworkDownloader.class, oldNetworkDownloader);
                    oldNetworkDownloader = null;
                }
            }
        }
    }



    private static DataSource dataSource;
    public static DataSource getDataSource(){
        return dataSource;
    }

    private static DatabaseHelper databaseHelper;

    static {
        if( System.getProperty("mysql.test") != null && System.getProperty("mysql.test").equalsIgnoreCase("true") ){
            logger.warn("The user has chosen to test with MYSQL...");
            databaseHelper = new MysqlDatabaseHelper();
        }else{
            databaseHelper = new H2DatabaseHelper();
        }
    }

    @BeforeClass
    public static void initializeTestDatabase() {
        synchronized (EXAMPLE_URL) {
            if (dataSource == null) {
                dataSource = databaseHelper.createDataSource();
            }
        }
    }

    @Before
    public void resetDatabase() {
        logger.debug("Resetting the database...");
        initializeTestDatabase();
        databaseHelper.resetDatabase();
    }

    protected void loadSql(String fileName) throws Exception {
        databaseHelper.loadSql(fileName);
    }

    protected void saveToFile() {
        databaseHelper.saveToFile();
    }


    protected DaoManager getInitializedDaoManager() throws Exception {
        DaoManagerFactory factory = FactoryLoader.getInstance(DaoManagerFactory.class);
        assertThat(factory, notNullValue());
        DaoManager daoManager = factory.getInstance(getDataSource());
        assertThat(daoManager, notNullValue());

        loadSql("td_tip_cache_sample.2016-09-15");

        return daoManager;
    }


    protected void initialize(DaoManager daoManager) throws Exception {
        // TODO Should we do anything here?
    }


}/* end AbstractDaoTest */