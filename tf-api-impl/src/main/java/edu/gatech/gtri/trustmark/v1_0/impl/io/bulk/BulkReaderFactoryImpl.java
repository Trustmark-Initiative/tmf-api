package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReader;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReaderFactory;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.ExcelBulkReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Default implementation of the {@link BulkReaderFactory}
 * <br/><br/>
 * @author brad
 * @date 2016-09-08
 */
public class BulkReaderFactoryImpl implements BulkReaderFactory {

    private static final Logger logger = Logger.getLogger(BulkReaderFactoryImpl.class);

    @Override
    public BulkReader createBulkReader(File... inputFiles) {
        BulkReader reader = null;
        logger.info("Creating BulkReader from list of files...");
        if( logger.isDebugEnabled() ){
            if( inputFiles != null && inputFiles.length > 0 ){
                for( File f : inputFiles ){
                    logger.debug("  InputFile: "+f.getPath());
                }
            }
        }
        logger.debug("Instantiating ServiceLoader<BulkReader>...");
        ServiceLoader<BulkReader> loader = ServiceLoader.load(BulkReader.class);
        Iterator<BulkReader> iter = loader.iterator();
        while( iter.hasNext() ){
            BulkReader nextReader = iter.next();
            logger.debug("Successfully instantiated a BulkReader("+nextReader.getClass().getName()+") from ServiceLoader!");
            if( nextReader.supports(inputFiles) ){
                if( reader == null ) {
                    reader = nextReader;
                }else{
                    logger.warn("Found a second BulkReader("+nextReader.getClass().getName()+") which supports given files!  This reader is ignored, using ("+reader.getClass().getName()+") instead.");
                }
            }else{
                logger.debug("BulkReader ("+nextReader.getClass().getName()+") does not support given files");
            }
        }
        if( reader == null ) {
            logger.warn("No BulkReader found to support given files.");
        }

        logger.debug("Returning reader...");
        return reader;
    }


    @Override
    public BulkReader createBulkReader(List<File> inputFiles) {
        File[] files = new File[inputFiles.size()];
        for( int i = 0; i < inputFiles.size(); i++ )
            files[i] = inputFiles.get(i);
        return createBulkReader(files);
    }

    @Override
    public BulkReader createXmlJsonBulkReader() {
        return new XmlJsonBulKReader();
    }

    @Override
    public ExcelBulkReader createExcelBulkReader() {
        return new ExcelBulkReaderImpl();
    }

    @Override
    public BulkReadContext createBulkReadContextFromProperties(Properties props) {
        try {
            return new ExcelImportBulkReadContext(props);
        }
        catch (Throwable t) {
            throw new RuntimeException("Error initializing BulkReadContext!", t);
        }
    }

    @Override
    public BulkReadContext createBulkReadContextFromFile(File propsFile) {
        try {
            return new ExcelImportBulkReadContext(propsFile);
        }
        catch (Throwable t) {
            throw new RuntimeException("Error initializing BulkReadContext!", t);
        }
    }

}
