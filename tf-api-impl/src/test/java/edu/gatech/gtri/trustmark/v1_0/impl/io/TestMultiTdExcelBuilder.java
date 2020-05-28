package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.MultiTrustmarkDefinitionBuilder;
import edu.gatech.gtri.trustmark.v1_0.io.MultiTrustmarkDefinitionBuilderFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 3/18/16.
 */
public class TestMultiTdExcelBuilder extends AbstractTest {

    @BeforeClass
    public static void createExcelOutputDir() throws IOException {
        File excelDir = new File("./target/excel");
        excelDir.mkdirs();
    }

    @Test
    public void testExcelTdBuilderResolution() throws Exception {
        logger.info("Testing that we can get an instance of Excel output...");
        MultiTrustmarkDefinitionBuilderFactory factory = FactoryLoader.getInstance(MultiTrustmarkDefinitionBuilderFactory.class);
        assertThat(factory, notNullValue());
        File tempFile = File.createTempFile("test", ".deleteme");
        Object excelOutputBuilder = factory.createExcelOutput(tempFile);
        assertThat(excelOutputBuilder, notNullValue());
        assertThat(excelOutputBuilder, instanceOf(MultiTrustmarkDefinitionBuilder.class));
        assertThat(excelOutputBuilder, instanceOf(ExcelMultiTdBuilder.class));

        logger.info("Successfully received an instance of ExcelMultiTdBuilder!");
    }


    @Test
    public void testOutputExcelSimple() throws Exception {
        logger.info("Testing Excel file output...");

        logger.debug("Reading TrustmarkDefinitions...");
        TrustmarkDefinition td2 = readTdFromFile("./src/test/resources/TDs/td-full.json");

        logger.debug("Creating excel output...");
        File excelFile = new File("./target/excel/testOutputExcelSimple.xls");
        MultiTrustmarkDefinitionBuilder excelBuilder = FactoryLoader.getInstance(MultiTrustmarkDefinitionBuilderFactory.class).createExcelOutput(excelFile);

        logger.debug("Writing excel output...");
        excelBuilder.init();
        excelBuilder.add(td2);
        excelBuilder.finish();

        logger.info("Successfully wrote excel file out!");
    }



}//end TestMultiTdExcelBuilder