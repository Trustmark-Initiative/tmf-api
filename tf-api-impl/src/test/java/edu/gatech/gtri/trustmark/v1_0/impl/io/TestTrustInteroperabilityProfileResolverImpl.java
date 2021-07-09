package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.junit.Test;

import java.net.URI;

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

    @Test
    public void testResolve() throws ResolveException {
        final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustInteroperabilityProfileResolver.resolve(URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
        assertThat(trustInteroperabilityProfile, notNullValue());
    }
}//end testGetSimpleHTMLResource()
