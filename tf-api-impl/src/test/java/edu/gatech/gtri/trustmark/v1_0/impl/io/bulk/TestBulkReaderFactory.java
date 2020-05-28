package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReaderFactory;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.ExcelBulkReader;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 9/8/16.
 */
public class TestBulkReaderFactory extends AbstractTest {

    @Test
    public void testFactoryInstatiation() {
        logger.info("Testing that we can create a new bulk reader factory...");
        BulkReaderFactory factory = FactoryLoader.getInstance(BulkReaderFactory.class);
        assertThat(factory, notNullValue());
        logger.info("Successfully retrieved an instance of BulkReaderFactory!");
    }


    @Test
    public void testCreateExcelBulkReader() {
        logger.info("Testing we can create an ExcelBulkReader...");
        BulkReaderFactory factory = FactoryLoader.getInstance(BulkReaderFactory.class);
        assertThat(factory, notNullValue());

        Object o = factory.createExcelBulkReader();
        assertThat(o, notNullValue());
        assertThat(o, instanceOf(ExcelBulkReader.class));

        logger.info("Successfully tested an ExcelBulkReader creation!");
    }

    @Test
    public void testCreateExcelBulkReaderFromFiles() {
        logger.info("Testing that we can create an ExcelBulkReader from a more abstract, File-first perspective...");

        File excelFile = new File("./src/test/resources/excel/minimal.xls");
        assertThat(excelFile.exists(), equalTo(Boolean.TRUE));

        BulkReaderFactory factory = FactoryLoader.getInstance(BulkReaderFactory.class);
        assertThat(factory, notNullValue());

        Object o = factory.createBulkReader(excelFile);
        assertThat(o, notNullValue());
        assertThat(o, instanceOf(ExcelBulkReader.class));

        logger.info("Successfully tested an ExcelBulkReader creation!");
    }//end testCreateExcelBulkReaderFromFiles()


}//end TestBulkReaderFactory