package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.model.ContactImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.io.*;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReaderFactory;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.ExcelBulkReader;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.*;
//import org.apache.log4j.Level;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Level.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Brad
 * @date 2016-09-08
 */
public class TestExcelBulkReader extends AbstractTest {

    final String TPAT_PROPERTIES = "./src/test/resources/tpat_config.properties";
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void testMinimalExample() throws Exception {
        logger.info("Testing excel with minimal information...");

        File excelFile = getFile("minimal.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("minimal.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult readResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(readResult, notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(readResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(readResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Validating single TD...");
        TrustmarkDefinition actualTd = readResult.getResultingTrustmarkDefinitions().get(0);
        assertThat(actualTd, notNullValue());

        TrustmarkDefinition expectedTd = expectedResult.getResultingTrustmarkDefinitions().get(0);

        doDiff(expectedTd, actualTd);

        logger.info("Successfully tested that ExcelBulkReader handles the minimal case with one TD.");
    }

    @Test
    public void testMaximumExample() throws Exception {
        logger.info("Testing excel with maximum information...");

        File excelFile = getFile("maximum.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("maximum.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult readResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(readResult, notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(readResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(readResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Validating single TD...");
        TrustmarkDefinition actualTd = readResult.getResultingTrustmarkDefinitions().get(0);
        assertThat(actualTd, notNullValue());

        TrustmarkDefinition expectedTd = expectedResult.getResultingTrustmarkDefinitions().get(0);

        doDiff(expectedTd, actualTd);

        logger.info("Successfully tested that ExcelBulkReader handles the maximum case with one TD.");
    }

    @Test
    public void testMultipleAssessmentSteps() throws Exception {
        logger.info("Importing an excel file containing a TD with multiple Steps...");

        File excelFile = getFile("multiple-steps.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("multiple-steps.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));


        logger.debug("Finding diff of Test 1 TDs...");
        TrustmarkDefinition td1Expected = getTDByName(expectedResult, "Test 1");
        assertThat(td1Expected, notNullValue());
        TrustmarkDefinition td1Actual = getTDByName(actualResult, "Test 1");
        assertThat(td1Actual, notNullValue());

        doDiff(td1Expected, td1Actual);


        logger.debug("Finding diff of Test 2 TDs...");
        TrustmarkDefinition td2Expected = getTDByName(expectedResult, "Test 2");
        assertThat(td2Expected, notNullValue());
        TrustmarkDefinition td2Actual = getTDByName(actualResult, "Test 2");
        assertThat(td2Actual, notNullValue());

        doDiff(td2Expected, td2Actual);

        logger.info("Successfully tested that ExcelBulkReader handles a multi-step case.");
    }

    @Test
    public void testMultipleCriteria() throws Exception {
        logger.info("Importing an excel file containing a TD with multiple criteria...");

        File excelFile = getFile("multiple-criteria.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("multiple-criteria.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));


        logger.debug("Finding diff of Test 1 TDs...");
        TrustmarkDefinition td1Expected = getTDByName(expectedResult, "Test 1");
        assertThat(td1Expected, notNullValue());
        TrustmarkDefinition td1Actual = getTDByName(actualResult, "Test 1");
        assertThat(td1Actual, notNullValue());

        doDiff(td1Expected, td1Actual);


        logger.debug("Finding diff of Test 2 TDs...");
        TrustmarkDefinition td2Expected = getTDByName(expectedResult, "Test 2");
        assertThat(td2Expected, notNullValue());
        TrustmarkDefinition td2Actual = getTDByName(actualResult, "Test 2");
        assertThat(td2Actual, notNullValue());

        doDiff(td2Expected, td2Actual);

        logger.info("Successfully tested that ExcelBulkReader handles a multi-criteria case.");
    }

    @Test
    public void testParameters() throws Exception {
        logger.info("Importing an excel file containing a TD with parameters...");

        File excelFile = getFile("parameters.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("parameters.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(1));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));


        logger.debug("Finding diff of Test 1 TDs...");
        TrustmarkDefinition td1Expected = getTDByName(expectedResult, "Test 001");
        assertThat(td1Expected, notNullValue());
        TrustmarkDefinition td1Actual = getTDByName(actualResult, "Test 001");
        assertThat(td1Actual, notNullValue());

        doDiff(td1Expected, td1Actual);


        logger.info("Successfully tested that ExcelBulkReader handles a parameters case.");
    }
    
    @Test
    public void testStepIdsInIssuanceCriteriaExample() throws Exception {
        logger.info("Testing excel import for step IDs in TD issuance criteria...");
        
        File excelFile = getFile("step-ids-in-issuance-criteria.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("step-ids-in-issuance-criteria.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(5));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));
        
        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult readResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(readResult, notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(readResult.getResultingTrustmarkDefinitions().size(), equalTo(5));
        assertThat(readResult.getResultingTrustInteroperabilityProfiles(), notNullValue()); // Even though it is empty
        assertThat(readResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(0));
        
        logger.debug("Validating all TDs...");
        assertAllArtifactsMatch(expectedResult, readResult);
        
        logger.info("Successfully tested excel import for step IDs in TD issuance criteria.");
    }

    @Test
    public void testMinimalTips() throws Exception {
        logger.info("Testing excel file with minimal TIPs example...");

        logger.debug("Parsing expected results...");
        File excelFile = getFile("minimal-tips.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("minimal-tips.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(2));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(2));


        logger.debug("Comparing actual results to expected results...");
        TrustInteroperabilityProfile tip1Expected = getTIPByName(expectedResult, "TIP 1");
        assertThat(tip1Expected, notNullValue());
        TrustInteroperabilityProfile tip1Actual = getTIPByName(actualResult, "TIP 1");
        assertThat(tip1Actual, notNullValue());

        doDiff(tip1Expected, tip1Actual);

        logger.debug("Comparing actual results to expected results...");
        TrustInteroperabilityProfile tip2Expected = getTIPByName(expectedResult, "TIP 2");
        assertThat(tip2Expected, notNullValue());
        TrustInteroperabilityProfile tip2Actual = getTIPByName(actualResult, "TIP 2");
        assertThat(tip2Actual, notNullValue());

        doDiff(tip2Expected, tip2Actual);

        logger.info("Successfully tested minimal TIPs import.");
    }//end testMinimalTips()

    @Test
    public void testExistingReferenceTips() throws Exception {
        logger.info("Testing excel file with existing reference TIPs example...");

        logger.debug("Parsing expected results...");
        File excelFile = getFile("existing-reference-tips.xlsx");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("existing-reference-tips.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(4));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContextFromFactory();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(4));
        actualResult.getResultingTrustInteroperabilityProfiles()
                .forEach(tip -> tip.getReferences()
                        .forEach(r -> logger.debug("refNumber -> "+r.getNumber())));


        logger.debug("Comparing actual results to expected results...");
        assertAllArtifactsMatch(expectedResult, actualResult);

        logger.info("Successfully tested existing reference TIPs import.");
    }

    @Test
    public void testLocalByNameReferenceTips() throws Exception {
        logger.info("Testing excel file with local by name reference TIPs example...");

        logger.debug("Parsing expected results...");
        File excelFile = getFile("local-by-name.xlsx");
        ExcelBulkReader reader = getExcelBulkReader();
/*
        BulkReadResult expectedResult = readJsonToResult("existing-reference-tips.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(4));
*/

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
/*
        BulkReadContext readContext = this.getBulkReadContextFromFactory();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(4));
        actualResult.getResultingTrustInteroperabilityProfiles()
                .forEach(tip -> tip.getReferences()
                        .forEach(r -> logger.debug("refNumber -> "+r.getNumber())));


        logger.debug("Comparing actual results to expected results...");
        assertAllArtifactsMatch(expectedResult, actualResult);
*/

        logger.info("Successfully tested existing reference TIPs import.");
    }

    @Test
    public void testLongNameReferenceTips() throws Exception {
        logger.info("Testing excel file with long-name-reference TIPs example...");
        
        logger.debug("Parsing expected results...");
        File excelFile = getFile("long-name-reference-tips.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("long-name-reference-tips.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(2));
        
        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(2));
        
        logger.debug("Comparing actual results to expected results...");
        assertAllArtifactsMatch(expectedResult, actualResult);
        
        logger.info("Successfully tested long-name-reference TIPs import.");
    }

    @Test
    public void testAndORNameReferenceTips() throws Exception {
        logger.info("Testing excel file with AND-OR-in-name-reference TIPs example...");

        logger.debug("Parsing expected results...");
        File excelFile = getFile("long-with-and-ors.xlsx");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("long-with-and-ors.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(2));

        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(4));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(5));

        actualResult.getResultingTrustInteroperabilityProfiles().forEach(tip -> tip.getReferences().forEach(r -> logger.debug("OrderBy -> "+r.getName() +" : "+r.getNumber())));
        logger.debug("Comparing actual results to expected results...");
//        assertAllArtifactsMatch(expectedResult, actualResult);

        logger.info("Successfully tested long-name-reference TIPs import.");
    }

    @Test
    public void testSourcesTermsTips() throws Exception {
        logger.info("Testing excel file with sources-and-terms TIPs example...");
        
        logger.debug("Parsing expected results...");
        File excelFile = getFile("sources-terms-tips.xls");
        ExcelBulkReader reader = getExcelBulkReader();
        BulkReadResult expectedResult = readJsonToResult("sources-terms-tips.json");
        assertThat(expectedResult, notNullValue());
        assertThat(expectedResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(expectedResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(5));
        
        logger.debug("Performing ExcelBulkReader.readBulkFrom() on file["+excelFile+"]...");
        BulkReadContext readContext = this.getBulkReadContext();
        BulkReadResult actualResult = reader.readBulkFrom(readContext, excelFile);
        assertThat(actualResult, notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions(), notNullValue());
        assertThat(actualResult.getResultingTrustmarkDefinitions().size(), equalTo(2));
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles(), notNullValue());
        assertThat(actualResult.getResultingTrustInteroperabilityProfiles().size(), equalTo(5));
        
        logger.debug("Comparing actual results to expected results...");
        assertAllArtifactsMatch(expectedResult, actualResult);
        
        logger.info("Successfully tested sources-and-terms TIPs import.");
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================

    /**
     * Performs a diff of the two given TDs.  If they are different, then a test failure is asserted, and the
     * differences printed.
     */
    private void doDiff(TrustmarkDefinition tdExpected, TrustmarkDefinition tdActual) {
        logger.debug("Performing JSON 'Diff' of the two different Trustmark Definitions( expected vs. actual )...");
        JsonDiffResultCollection resultCollection = this.doJsonDiff(TrustmarkDefinition.class, tdExpected, tdActual);
        assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, 0);
        assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, 0);
        
        // Diff Tool
        logger.debug("Performing 'Diff' of the two different Trustmark Definitions( expected vs. actual )...");
        Collection<TrustmarkDefinitionDiffResult> diffResults = FactoryLoader.getInstance(TrustmarkDefinitionUtils.class).diff(tdExpected, tdActual);
        assertThat(diffResults, notNullValue());
        if( diffResults.size() > 0 ){
            for( TrustmarkDefinitionDiffResult diffResult : diffResults ){
                logger.warn("Found unexpected diff Result: "+diffResult.getDescription());
            }
        }
        assertThat(diffResults.size(), equalTo(0));
    }
    
    private void doDiff(TrustInteroperabilityProfile tipExpected, TrustInteroperabilityProfile tipActual){
        JsonDiffResultCollection resultCollection = this.doJsonDiff(TrustInteroperabilityProfile.class, tipExpected, tipActual);
        assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, 0);
        assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, 0);
        //if not provided, "$id" field is being generated on the fly and is currently ignored in TrustInteroperabilityProfileJsonDiffImpl
//        assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, 1);
//        assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.NOT_COMPARED, 1);
    }

    private Serializer getJsonSerializer() {
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        Serializer serializer = factory.getJsonSerializer();
        return serializer;
    }

    private String toJsonString(Object obj){
        StringWriter stringWriter = new StringWriter();
        try {
            if (obj instanceof TrustmarkDefinition){
                getJsonSerializer().serialize(((TrustmarkDefinition) obj), stringWriter);
            }else if (obj instanceof TrustInteroperabilityProfile){
                getJsonSerializer().serialize(((TrustInteroperabilityProfile) obj), stringWriter);
            }
            stringWriter.flush();
        }catch(IOException ioe){
            logger.error("UNABLE TO SERIALIZE JSON!", ioe);
        }
        return stringWriter.toString();
    }

    /**
     * Gets the TrustmarkDefinition from the BulkReadResult based on its name only.  If the TD is not found, a unit
     * test failure is asserted.
     */
    private TrustmarkDefinition getTDByName(BulkReadResult container, String name){
        logger.debug("Getting TD by name: " + name);
        TrustmarkDefinition td = null;
        if( container.getResultingTrustmarkDefinitions() != null ){
            for( TrustmarkDefinition current : container.getResultingTrustmarkDefinitions() ){
                if( current.getMetadata().getName().equals(name) ){
                    td = current;
                    break;
                }
            }
        }
        assertThat("For TD Name: " + name, td, notNullValue());
        return td;
    }
    
    private TrustmarkDefinition getTDByIdentifier(BulkReadResult container, URI identifier){
        logger.debug("Getting TD by identifier: " + identifier);
        TrustmarkDefinition td = null;
        if( container.getResultingTrustmarkDefinitions() != null ){
            for( TrustmarkDefinition current : container.getResultingTrustmarkDefinitions() ){
                if( current.getMetadata().getIdentifier().equals(identifier) ){
                    td = current;
                    break;
                }
            }
        }
        assertThat("For TD Identifier: " + identifier, td, notNullValue());
        return td;
    }

    /**
     * Gets the TrustInteroperabilityProfile from the BulkReadResult based on its name only.  If the TIP is not found, a unit
     * test failure is asserted.
     */
    private TrustInteroperabilityProfile getTIPByName(BulkReadResult container, String name){
        logger.debug("Getting TIP by name: " + name);
        TrustInteroperabilityProfile tip = null;
        if( container.getResultingTrustInteroperabilityProfiles() != null ){
            for( TrustInteroperabilityProfile current : container.getResultingTrustInteroperabilityProfiles() ){
                if( current.getName().equals(name) ){
                    tip = current;
                    break;
                }
            }
        }
        assertThat("For TIP Name: " + name, tip, notNullValue());
        return tip;
    }
    
    private TrustInteroperabilityProfile getTIPByIdentifier(BulkReadResult container, URI identifier) {
        logger.debug("Getting TIP by identifier: " + identifier);
        TrustInteroperabilityProfile tip = null;
        if( container.getResultingTrustInteroperabilityProfiles() != null ){
            for( TrustInteroperabilityProfile current : container.getResultingTrustInteroperabilityProfiles() ){
                if( current.getIdentifier().equals(identifier) ){
                    tip = current;
                    break;
                }
            }
        }
        assertThat("For TIP identifier: " + identifier, tip, notNullValue());
        return tip;
    }

    private void assertAllArtifactsMatch(BulkReadResult expectedResult, BulkReadResult actualResult) {
        Set<URI> tipIdentifiers = new HashSet<>();
        expectedResult.getResultingTrustInteroperabilityProfiles().forEach(tip -> tipIdentifiers.add(tip.getIdentifier()));
        actualResult.getResultingTrustInteroperabilityProfiles().forEach(tip -> tipIdentifiers.add(tip.getIdentifier()));
        for (URI identifier : tipIdentifiers) {
            TrustInteroperabilityProfile tipExpected = getTIPByIdentifier(expectedResult, identifier);
            TrustInteroperabilityProfile tipActual = getTIPByIdentifier(actualResult, identifier);

            doDiff(tipExpected, tipActual);
        }

        Set<URI> tdIdentifiers = new HashSet<>();
        expectedResult.getResultingTrustmarkDefinitions().forEach(td -> tdIdentifiers.add(td.getMetadata().getIdentifier()));
        actualResult.getResultingTrustmarkDefinitions().forEach(td -> tdIdentifiers.add(td.getMetadata().getIdentifier()));
        for (URI identifier : tdIdentifiers) {
            TrustmarkDefinition tdExpected = getTDByIdentifier(expectedResult, identifier);
            TrustmarkDefinition tdActual = getTDByIdentifier(actualResult, identifier);

            doDiff(tdExpected, tdActual);
        }
    }



    /**
     * Finds the file with the specified name, starting in directory ./src/test/resources/excel/.  If the file is not
     * found, an error is asserted and the unit test should stop.
     */
    private File getFile(String name){
        File excelFile = new File("./src/test/resources/excel/"+name);
        assertThat(excelFile.exists(), equalTo(Boolean.TRUE));
        return excelFile;
    }

    /**
     * Given a JSON file which should mirror the contents of a BulkReadResult, this method will read it into one.
     */
    private SimpleBulkReadResult readJsonToResult(String filename) throws IOException, ResolveException {
        SimpleBulkReadResult result = new SimpleBulkReadResult();
        File jsonFile = getFile(filename);
        String text = new String(Files.readAllBytes(jsonFile.toPath()));
        JSONObject json = new JSONObject(text);

//        Logger jsonLogger = LogManager.getLogger("edu.gatech.gtri.trustmark.v1_0.impl.io.json");
//        Level originalLogLevelForJSON = jsonLogger.getLevel();
//        jsonLogger.setLevel(Level.WARN);
        JSONArray tds = json.optJSONArray("trustmarkDefinitions");
        if( tds != null ){
            for( int i = 0; i < tds.length(); i++ ){
                JSONObject tdJsonObj = tds.optJSONObject(i);
                if( tdJsonObj != null ){
                    TrustmarkDefinition td = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(tdJsonObj.toString(), false);
                    result.getResultingTrustmarkDefinitions().add(td);
                }
            }
        }

        JSONArray tips = json.optJSONArray("trustInteroperabilityProfiles");
        if( tips != null ){
            for( int i = 0; i < tips.length(); i++ ){
                JSONObject tipJsonObj = tips.optJSONObject(i);
                if( tipJsonObj != null ){
                    TrustInteroperabilityProfile tip = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class).resolve(tipJsonObj.toString(), false);
                    result.getResultingTrustInteroperabilityProfiles().add(tip);
                }
            }
        }

//        jsonLogger.setLevel(originalLogLevelForJSON);
        return result;
    }

    private ExcelBulkReader getExcelBulkReader() throws Exception {
        BulkReaderFactory bulkReaderFactory = FactoryLoader.getInstance(BulkReaderFactory.class);
        ExcelBulkReader reader = bulkReaderFactory.createExcelBulkReader();
        return reader;
    }

    private BulkReadContext getBulkReadContextFromFactory() {
        BulkReaderFactoryImpl factoryImpl = new BulkReaderFactoryImpl();
        return factoryImpl.createBulkReadContextFromFile(new File(TPAT_PROPERTIES));
    }

    private BulkReadContext getBulkReadContext() throws Exception {
        BulkReadContextImpl contextImpl = new BulkReadContextImpl();
        
        // Trustmark Defining Organization
        EntityImpl tdoEntity = new EntityImpl();
        tdoEntity.setIdentifier(new URI("http:///tdo.example/"));
        tdoEntity.setName("Trustmark Defining Organization");
        ContactImpl tdoContact = new ContactImpl();
        tdoContact.setKind(ContactKindCode.PRIMARY);
        tdoContact.addEmail("TrustmarkFeedback@gtri.gatech.edu");
        tdoContact.addTelephone("404-407-8956");
        tdoContact.addMailingAddress("75 5th Street NW, Suite 900, Atlanta, GA 30308");
        tdoEntity.addContact(tdoContact);
        contextImpl.setTrustmarkDefiningOrganization(tdoEntity);
        
        // TIP Issuer
        EntityImpl tipIssuerEntity = new EntityImpl();
        tipIssuerEntity.setIdentifier(tdoEntity.getIdentifier());
        tipIssuerEntity.setName("GTRI NSTIC Trustmark Pilot");
        tipIssuerEntity.setContacts(tdoEntity.getContacts());
        contextImpl.setTrustInteroperabilityProfileIssuer(tipIssuerEntity);
        
        // TD Provider References
        String[] tdProviderReferenceIds = {
            "https://trustmark.gtri.gatech.edu/",
            "https://nief.gfipm.net/",
            "https://nief.org/"
        };
        for (String tdProviderReferenceId : tdProviderReferenceIds) {
            EntityImpl tdProviderReference = new EntityImpl();
            tdProviderReference.setIdentifier(new URI(tdProviderReferenceId));
            contextImpl.addTrustmarkProviderReference(tdProviderReference);
            contextImpl.addTfamUriForExternalResolution(new URI(tdProviderReferenceId));
        }
        
        // TD Identifier URI Base
        contextImpl.setTdIdentifierUriBase("https://trustmark.gtri.gatech.edu/operational-pilot/trustmark-definitions");
        
        // TIP Identifier URI Base
        contextImpl.setTipIdentifierUriBase("https://trustmark.gtri.gatech.edu/operational-pilot/trust-interoperability-profiles");
        
        // Default Wording
        contextImpl.setDefaultVersion("1.0");
        contextImpl.setDefaulTiptLegalNotice("This document and the information contained herein is provided on an \"AS IS\" basis, and the Georgia Tech Research Institute disclaims all warranties, express or implied, including but not limited to any warranty that the use of the information herein will not infringe any rights or any implied warranties or merchantability or fitness for a particular purpose. In addition, the Georgia Tech Research Institute disclaims legal liability for any loss incurred as a result of the use or reliance on the document or the information contained herein.");
        contextImpl.setDefaultTipNotes("The Georgia Tech Research Institute (GTRI) has published this document with the support of the National Strategy for Trusted Identities in Cyberspace (NSTIC) via the National Institute of Standards and Technology (NIST). The views expressed herein do not necessarily reflect the official policies of GTRI, NIST or NSTIC; nor does mention of trade names, commercial practices, or organizations imply endorsement by the U.S. Government.");
        contextImpl.setDefaultIssuanceCriteria("yes(ALL)");
        contextImpl.setDefaultRevocationCriteria("For any trustmark issued under this Trustmark Definition, the Trustmark Provider must revoke the trustmark upon any condition whereby one or more Conformance Criteria cease to be satisfied.​");
        contextImpl.setDefaultStakeholderDescription("StakeholderDescription from NIST Special Publication 800-53.");
        contextImpl.setDefaultRecipientDescription("RecipientDescription from NIST Special Publication 800-53.");

        contextImpl.setDefaultTdLegalNotice("Default TD Legal Notice");
        contextImpl.setDefaultTdNotes("Default TD Notes");

        return contextImpl;
    }

}
