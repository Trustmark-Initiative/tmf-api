package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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




}//end testGetSimpleHTMLResource()