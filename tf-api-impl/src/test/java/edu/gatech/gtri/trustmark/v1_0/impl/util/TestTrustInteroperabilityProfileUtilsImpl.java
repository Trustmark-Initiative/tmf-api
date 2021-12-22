package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.util.TipTreeNode;
import edu.gatech.gtri.trustmark.v1_0.util.TrustInteroperabilityProfileUtils;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 4/12/16.
 */
public class TestTrustInteroperabilityProfileUtilsImpl extends AbstractTest {

    @Test
    public void testServiceLoading() {
        logger.debug("Asserting that we can load TrustInteroperabilityProfileUtilsImpl.class...");
        Object thing = FactoryLoader.getInstance(TrustInteroperabilityProfileUtils.class);
        assertThat(thing, notNullValue());
        assertThat(thing, instanceOf(TrustInteroperabilityProfileUtilsImpl.class));
        logger.info("Successfully loaded TrustInteroperabilityProfileUtilsImpl.class!");
    }//end testServiceLoading()

    private TrustInteroperabilityProfileUtils getUtils() {
        return FactoryLoader.getInstance(TrustInteroperabilityProfileUtils.class);
    }

    @Test
    @Ignore
    public void testTreeDownload() throws Exception {
        TipTreeNode treeNode = getUtils().buildTipTree(new URI("https://cjis.trustmarkinitiative.org/lib/trust-interoperability-profiles/nist-800-63-loa-3-profile/1.0/"));
    }

    private TrustInteroperabilityProfile load(String filename) throws ResolveException {
        File file = new File("./src/test/resources/TIPs/diff_tests/" + filename + ".xml");
        return FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(file);
    }


    @Test
    public void testFileResolution() {
        logger.info("Testing that we can identify TIP files...");
        assertThat(getUtils().isTrustInteroperabilityProfile(new File("./src/test/resources/TIPs/tip-full.xml")), equalTo(true));
        assertThat(getUtils().isTrustInteroperabilityProfile(new File("./src/test/resources/TIPs/tip-full.json")), equalTo(true));
        assertThat(getUtils().isTrustInteroperabilityProfile(new File("./src/test/resources/TDs/td-full.xml")), equalTo(false));
        logger.info("Successfully tested identifying TIP files!");
    }


    @Test
    public void testSameFileHasNoDiff() throws Exception {
        TrustInteroperabilityProfile tip1 = load("tip1");
        TrustInteroperabilityProfile tip2 = load("tip1");

        Collection<TrustInteroperabilityProfileDiffResult> differences = getUtils().diff(tip1, tip2);
        assertThat(differences, notNullValue());
        assertThat(differences.isEmpty(), equalTo(true));
    }

    @Test
    public void testMajorDifferencesInImportantMetadata() throws Exception {
        logger.info("Testing Major Differences in Important Metadata for TIP Diff...");
        TrustInteroperabilityProfile tip1 = load("tip1");
        TrustInteroperabilityProfile tip2 = load("tip1_majorDiff");

        Collection<TrustInteroperabilityProfileDiffResult> differences = getUtils().diff(tip1, tip2);
        assertThat(differences, notNullValue());
        assertThat(differences.size(), equalTo(5));
    }

    @Test
    public void testChangeInReferenceIds() throws Exception {
        logger.info("Testing Change in Reference IDs for TIP Diff...");
        TrustInteroperabilityProfile tip1 = load("tip1");
        TrustInteroperabilityProfile tip2 = load("tip1_references");

        Collection<TrustInteroperabilityProfileDiffResult> differences = getUtils().diff(tip1, tip2);
        assertThat(differences, notNullValue());
        assertThat(differences.size(), equalTo(2));
    }

    private boolean resultMatches(TrustInteroperabilityProfileDiffResult result, TrustInteroperabilityProfileDiffType type, String locationRegex, String descriptionRegex) {
        boolean matches = false;
        if (result.getDiffType() == type) {
            boolean locationMatches = true;
            if (locationRegex != null) {
                locationMatches = false;
                if (result.getLocation().matches(locationRegex) || result.getLocation().equalsIgnoreCase(locationRegex))
                    locationMatches = true;
            }
            boolean descMatches = true;
            if (descriptionRegex != null) {
                descMatches = false;
                if (result.getDescription().matches(descriptionRegex) || result.getDescription().equalsIgnoreCase(descriptionRegex))
                    descMatches = true;
            }
            matches = locationMatches && descMatches;
        }
        return matches;
    }

    private void assertContains(Collection<TrustInteroperabilityProfileDiffResult> results, TrustInteroperabilityProfileDiffType type, String locationRegex, String descriptionRegex) {
        try {
            if (results == null || results.size() == 0)
                Assert.fail("Expecting TIP Diff results to contain Type[" + type + "], Location[" + locationRegex + "], Description[" + descriptionRegex + "] - but the results are empty!");

            boolean found = false;
            for (TrustInteroperabilityProfileDiffResult result : results) {
                if (resultMatches(result, type, locationRegex, descriptionRegex)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                Assert.fail("Expecting TIP Diff results to contain Type[" + type + "], Location[" + locationRegex + "], Description[" + descriptionRegex + "] - but the result was not found!");

        } catch (Exception t) {
            logger.error("Error evaluating assertContains()!", t);
            Assert.fail("Error evaluating assertContains(): " + t.toString());
        }
    }

    private void testExpectedDifference(String firstFile, String secondFile, TrustInteroperabilityProfileDiffType typeExpected) throws ResolveException {
        Collection<TrustInteroperabilityProfileDiffResult> results = getUtils().diff(load(firstFile), load(secondFile));
        assertThat(results, notNullValue());
        assertThat(results.size(), greaterThan(0)); // There is at least 1 result
        assertContains(results, typeExpected, null, null);
    }

}//end TestTrustmarkDefinitionUtilsImpl
