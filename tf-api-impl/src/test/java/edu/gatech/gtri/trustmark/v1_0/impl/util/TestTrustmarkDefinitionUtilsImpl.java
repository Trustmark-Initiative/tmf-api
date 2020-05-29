package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffType;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 4/12/16.
 */
public class TestTrustmarkDefinitionUtilsImpl extends AbstractTest {

    @Test
    public void testServiceLoading() {
        logger.debug("Asserting that we can load TrustmarkDefinitionUtilsImpl.class...");
        Object thing = FactoryLoader.getInstance(TrustmarkDefinitionUtils.class);
        assertThat(thing, notNullValue());
        assertThat(thing, instanceOf(TrustmarkDefinitionUtilsImpl.class));
        logger.info("Successfully loaded TrustmarkDefinitionUtilsImpl.class!");
    }//end testServiceLoading()

    private TrustmarkDefinitionUtils getUtils() {
        return FactoryLoader.getInstance(TrustmarkDefinitionUtils.class);
    }

    private TrustmarkDefinition load(String filename) throws ResolveException {
        File file = new File("./src/test/resources/TDs/diff_tests/" + filename + ".xml");
        return FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(file);
    }

    private boolean resultMatches(TrustmarkDefinitionDiffResult result, TrustmarkDefinitionDiffType type, String locationRegex, String descriptionRegex){
        boolean matches = false;
        if( result.getDiffType() == type ){
            boolean locationMatches = true;
            if( locationRegex != null ){
                locationMatches = false;
                if( result.getLocation().matches(locationRegex) || result.getLocation().equalsIgnoreCase(locationRegex) )
                    locationMatches = true;
            }
            boolean descMatches = true;
            if( descriptionRegex != null ){
                descMatches = false;
                if( result.getDescription().matches(descriptionRegex) || result.getDescription().equalsIgnoreCase(descriptionRegex) )
                    descMatches = true;
            }
            matches = locationMatches && descMatches;
        }
        return matches;
    }

    private void assertContains(Collection<TrustmarkDefinitionDiffResult> results, TrustmarkDefinitionDiffType type, String locationRegex, String descriptionRegex)  {
        try{
            if( results == null || results.size() == 0 )
                Assert.fail("Expecting TD Diff results to contain Type["+type+"], Location["+locationRegex+"], Description["+descriptionRegex+"] - but the results are empty!");

            boolean found = false;
            for( TrustmarkDefinitionDiffResult result : results ){
                if( resultMatches(result, type, locationRegex, descriptionRegex) ){
                    found = true;
                    break;
                }
            }
            if( !found )
                Assert.fail("Expecting TD Diff results to contain Type["+type+"], Location["+locationRegex+"], Description["+descriptionRegex+"] - but the result was not found!");

        }catch(Exception t){
            logger.error("Error evaluating assertContains()!", t);
            Assert.fail("Error evaluating assertContains(): "+t.toString());
        }
    }

    private void testExpectedDifference(String firstFile, String secondFile, TrustmarkDefinitionDiffType typeExpected) throws ResolveException {
        Collection<TrustmarkDefinitionDiffResult> results = getUtils().diff(load(firstFile), load(secondFile));
        assertThat(results, notNullValue());
        assertThat(results.size(), greaterThan(0)); // There is at least 1 result
        assertContains(results, typeExpected, null, null);
    }
    
    @Test
    public void testDifferentIds() throws ResolveException {
        logger.info("Testing a different ids...");
    
        testExpectedDifference("td1", "td1_diff_ids", TrustmarkDefinitionDiffType.MAJOR_METADATA_ID_FIELD_DOES_NOT_MATCH);

        logger.info("Found metadata.identifier change as expected.");
    }

    @Test
    public void testMinorTextDiff() throws ResolveException {
        logger.info("Testing a minor text difference change...");
    
        testExpectedDifference("td1", "td1_desc_different", TrustmarkDefinitionDiffType.TEXT_SLIGHTLY_DIFFERENT);

        logger.info("Found minor text difference change as expected.");
    }

    @Test
    public void testMajorTextDiff() throws ResolveException {
        logger.info("Testing a major text difference change...");
    
        testExpectedDifference("td1", "td1_description_change", TrustmarkDefinitionDiffType.TEXT_COMPLETELY_DIFFERENT);

        logger.info("Found major text difference change as expected.");
    }

    @Test
    public void testDifferentCritCount() throws ResolveException {
        logger.info("Testing different crit count...");
    
        testExpectedDifference("td1", "td1_critcount", TrustmarkDefinitionDiffType.CRITERIA_COUNT_MISMATCH);

        logger.info("Found different crit count as expected.");
    }
    
    @Test
    public void testDifferentStepCount() throws ResolveException {
        logger.info("Testing different step count...");
    
        testExpectedDifference("td1", "td1_stepcount", TrustmarkDefinitionDiffType.STEP_COUNT_MISMATCH);
        
        logger.info("Found different step count as expected.");
    }

    @Test
    public void testDifferentStepParameterKind() throws ResolveException {
        logger.info("Testing different step parameter kind...");
    
        testExpectedDifference("td1", "td1_step_param_kind", TrustmarkDefinitionDiffType.STEP_PARAMETERS_KIND_MISMATCH);

        logger.info("Found different step parameter kind as expected.");
    }

    @Test
    public void testDifferentStepParameterEnum() throws ResolveException {
        logger.info("Testing different step parameter enum...");
    
        testExpectedDifference("td1", "td1_step_param_enum", TrustmarkDefinitionDiffType.STEP_PARAMETERS_ENUM_VALUES_MISMATCH);

        logger.info("Found different step parameter enum as expected.");
    }

    @Test
    public void testDifferentStepParameterRequirement() throws ResolveException {
        logger.info("Testing different step parameter requirement...");
    
        testExpectedDifference("td1", "td1_step_param_required", TrustmarkDefinitionDiffType.STEP_PARAMETERS_REQUIREMENT_MISMATCH);

        logger.info("Found different step parameter requirement as expected.");
    }
    
    @Test
    public void testDifferentStepParameterCount() throws ResolveException {
        logger.info("Testing different step parameter count...");
        
        testExpectedDifference("td1", "td1_step_param_count", TrustmarkDefinitionDiffType.STEP_PARAMETER_COUNT_MISMATCH);
        
        logger.info("Found different step parameter count as expected.");
    }
    
    @Test
    public void testDifferentStepParameterNotFound() throws ResolveException {
        logger.info("Testing different step parameters found...");
        
        testExpectedDifference("td1", "td1_step_param_notfound", TrustmarkDefinitionDiffType.STEP_PARAMETER_NOT_FOUND);
        
        logger.info("Found different step parameters as expected.");
    }
    


}//end TestTrustmarkDefinitionUtilsImpl