package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * Responsible for creating BulkReader based on incoming files.
 * <br/><br/>
 * @author brad
 * @date 2016-08-09
 */
public interface BulkReaderFactory {


    /**
     * Creates a BulkReader from the given files.  Expected that it will look at them and decide, but shouldn't be a
     * time consuming operation to figure it out.
     * <br/><br/>
     * @param inputFiles the collection of input {@link File}s that we are going to decide from
     * @return a BulkReader, or UnsupportedOperationException if none found to support it.
     */
    BulkReader createBulkReader(File... inputFiles);

    /**
     * Examines the input files and determines if any {@link BulkReader} is configured support them.
     * <br/><br/>
     * @param inputFiles the collection of input {@link File}s that we are going to decide from
     * @return a BulkReader, or UnsupportedOperationException if none found to support it.
     */
    BulkReader createBulkReader(List<File> inputFiles);

    /**
     * Returns an {@link ExcelBulkReader} quickly and easily.
     * <br/><br/>
     * @return an implementation of {@link ExcelBulkReader}
     */
    ExcelBulkReader createExcelBulkReader();

    /**
     * Generates a bulk reader which can handle a collection of either XML or JSON files.
     * <br/><br/>
     * @return an implementation of {@link BulkReader}
     */
    BulkReader createXmlJsonBulkReader();

    /**
     * Provides a convenient way to get a {@link BulkReadContext} from a particular properties file format.  Note
     * that this is the "default" way, so while being easy it lacks flexibility you could have by implementing the
     * interface yourself.
     */
    BulkReadContext createBulkReadContextFromProperties(Properties props);

    /**
     * another method to build a BulkReadContext by passing in a properties file vs passing in a properties object
     * @param propsFile
     * @return
     */
    BulkReadContext createBulkReadContextFromFile(File propsFile);

}/* end BulkReaderFactory */