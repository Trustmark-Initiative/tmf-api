package edu.gatech.gtri.trustmark.v1_0.impl.conversion;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.converter.Converter_1_0_to_1_1;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Performs conversion testing.
 * <br/><br/>
 * @author brad
 * @date 10/25/16
 */
public class TestConverter_1_0_to_1_1 extends AbstractTest {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(TestConverter_1_0_to_1_1.class);
    //==================================================================================================================
    //  TEST METHODS
    //==================================================================================================================
    @Test
    public void testSupportsWorks() throws Exception {
        log.debug("Testing the supports method on converter...");

        Converter_1_0_to_1_1 c = new Converter_1_0_to_1_1();
        assertThat(c.supports(new File("./src/test/resources/conversion/v1_0/acceptance-criteria_1.0.xml")), equalTo(true));
        assertThat(c.supports(new File("./src/test/resources/conversion/v1_0/invalid-supported-root-element.xml")), equalTo(false));
        assertThat(c.supports(new File("./src/test/resources/conversion/v1_0/invalid-supported-bad-ns.xml")), equalTo(false));

        log.debug("Successfully tested supports method.");
    }

    @Test
    public void testConversionTd() throws Exception {
        log.debug("Testing conversion of a TD...");
        File td = new File("./src/test/resources/conversion/v1_0/acceptance-criteria_1.0.xml");
        File td_out = new File("./target/td-conversion1.xml");
        if( td_out.exists() )
            td_out.delete();

        Converter_1_0_to_1_1 c = new Converter_1_0_to_1_1();
        assertThat(c.supports(td), equalTo(true));

        c.convert(td, td_out);

        assertThat(td_out.exists(), equalTo(true));
        String content = FileUtils.readFileToString(td_out);
        assertThat(content, notNullValue());
        assertThat(content.length(), greaterThan(10));

        log.debug("Content: \n"+content);

        // TODO Many more assertions...

    }

    @Test
    public void testConversionTip() throws Exception {
        log.debug("Testing conversion of a TIP...");
        File tipFile = new File("./src/test/resources/conversion/v1_0/ficam-loa-2-profile_1.0.xml");
        File tipFileOut = new File("./target/tip-conversion1.xml");
        if( tipFileOut.exists() )
            tipFileOut.delete();

        Converter_1_0_to_1_1 c = new Converter_1_0_to_1_1();
        assertThat(c.supports(tipFile), equalTo(true));

        c.convert(tipFile, tipFileOut);

        assertThat(tipFileOut.exists(), equalTo(true));
        String content = FileUtils.readFileToString(tipFileOut);
        assertThat(content, notNullValue());
        assertThat(content.length(), greaterThan(10));

        log.debug("Content: \n"+content);

        // TODO Many more assertions...

    }


    @Test
    public void testSimpleReplacement() {
        doTestNsReplacement(
                "xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_10+"\"",
                Converter_1_0_to_1_1.NS_URI_10, Converter_1_0_to_1_1.NS_URI_11,
                "xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_11+"\""
        );
    }

    @Test
    public void testSimpleReplacementWithOtherData() {
        doTestNsReplacement(
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_10+"\" />",
                Converter_1_0_to_1_1.NS_URI_10, Converter_1_0_to_1_1.NS_URI_11,
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_11+"\" />"
        );
    }

    @Test
    public void testSimpleReplacementWithOtherData2() {
        doTestNsReplacement(
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_10+"\" xmlns:test=\"urn:test:1\" />",
                Converter_1_0_to_1_1.NS_URI_10, Converter_1_0_to_1_1.NS_URI_11,
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_11+"\" xmlns:test=\"urn:test:1\" />"
        );
    }
    @Test
    public void testReplacementMultiple() {
        doTestNsReplacement(
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_10+"\" /> <blah2 xmlns:temp=\""+Converter_1_0_to_1_1.NS_URI_10+"\" />",
                Converter_1_0_to_1_1.NS_URI_10, Converter_1_0_to_1_1.NS_URI_11,
                "<blah xmlns:tf=\""+Converter_1_0_to_1_1.NS_URI_11+"\" /> <blah2 xmlns:temp=\""+Converter_1_0_to_1_1.NS_URI_11+"\" />"
        );
    }

    private void doTestNsReplacement(String orig, String ns1, String ns2, String expected) {
        log.debug("Converting NS in string["+orig+"], ns1="+ns1+" ns2="+ns2);
        Converter_1_0_to_1_1 c = new Converter_1_0_to_1_1();
        String replaced = c.replaceNsURI(orig, ns1, ns2);
        log.debug("   Conversion Result: "+replaced);
        assertThat(replaced, equalTo(expected));
    }

}/* end TestConverter_1_0_to_1_1 */
