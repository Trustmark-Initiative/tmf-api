package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/8/15.
 */
public class TestTrustmarkDefinitionResolverImpl extends AbstractTest {

    @Test
    public void testFactoryLoaderRead() throws Exception {
        logger.info("Tests we can get a TrustmarkDefinitionResolver from the FactoryLoader...");
        TrustmarkDefinitionResolver resolver = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class);
        assertThat(resolver, notNullValue());
    }//end testGet()



}//end testGetSimpleHTMLResource()
