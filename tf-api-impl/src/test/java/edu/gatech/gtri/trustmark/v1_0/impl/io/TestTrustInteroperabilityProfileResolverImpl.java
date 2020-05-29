package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/8/15.
 */
public class TestTrustInteroperabilityProfileResolverImpl extends AbstractTest {

    @Test
    public void testFactoryLoaderRead() throws Exception {
        logger.info("Tests we can get a TrustInteroperabilityProfileResolver from the FactoryLoader...");
        TrustInteroperabilityProfileResolver resolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        assertThat(resolver, notNullValue());
    }//end testGet()




}//end testGetSimpleHTMLResource()