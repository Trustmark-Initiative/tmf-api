package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/8/15.
 */
public class TestTrustmarkResolverImpl extends AbstractTest {

    @Test
    public void testFactoryLoaderRead() throws Exception {
        logger.info("Tests we can get a TrustmarkResolver from the FactoryLoader...");
        TrustmarkResolver resolver = FactoryLoader.getInstance(TrustmarkResolver.class);
        assertThat(resolver, notNullValue());
    }//end testGet()

    @Test
    public void test() throws ResolveException, URISyntaxException, IOException {
        final Trustmark trustmark = FactoryLoader.getInstance(TrustmarkResolver.class).resolve(new URI("https://tat.trustmarkinitiative.org/test-tat/public/trustmarks/9DEA1972-C587-476E-B854-17A449512079"), true);
        new SerializerXml().serialize(trustmark, System.out);
    }
}
