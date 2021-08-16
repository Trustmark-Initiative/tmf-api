package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.util.UrlUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Tests the UrlUtils works as expected.
 * <br/><br/>
 * @author brad
 * @date 5/9/17
 */
public class TestUrlUtils extends AbstractTest {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(TestUrlUtils.class);
    //==================================================================================================================
    //  TESTS
    //==================================================================================================================
    @Test
    public void testEnsureParameter() throws Exception {
        log.info("Testing that ensure parameter works as expected...");

        URL url = UrlUtils.ensureParameter("http://www.google.com", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?test=123"));

        url = UrlUtils.ensureParameter("http://www.google.com?param=value", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?param=value&test=123"));

        url = UrlUtils.ensureParameter("http://www.google.com?test=value", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?test=123"));

        url = UrlUtils.ensureParameter("http://www.google.com?p1=p1val&test=value&p2=p2val", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?p1=p1val&test=123&p2=p2val"));

        url = UrlUtils.ensureParameter("http://www.google.com?test=value&p2=p2val", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?test=123&p2=p2val"));

        url = UrlUtils.ensureParameter("http://www.google.com?p1=p1val&test=value", "test", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?p1=p1val&test=123"));


        url = UrlUtils.ensureParameter("http://www.google.com?p1=p1val&test param=value", "test param", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?p1=p1val&test+param=123"));

        url = UrlUtils.ensureParameter("http://www.google.com?p1=p1val&test+param=value", "test param", "123");
        assertThat(url.toString(), equalTo("http://www.google.com?p1=p1val&test+param=123"));

        url = UrlUtils.ensureParameter("http://www.google.com?format=xml&format=json", "format", "json");
        assertThat(url.toString(), equalTo("http://www.google.com?format=json"));

        log.info("UrlUtils.ensureParameter() works!");
    }//end testEnsureParmaeter()



}/* end TestUrlUtils */