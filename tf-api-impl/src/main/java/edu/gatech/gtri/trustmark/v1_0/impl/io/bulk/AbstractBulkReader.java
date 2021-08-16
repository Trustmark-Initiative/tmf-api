package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadContext;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadListener;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadResult;
import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReader;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.*;

/**
 * Created by Nicholas on 9/7/2016.
 */
public abstract class AbstractBulkReader implements BulkReader {

    // Constants
    private static final Logger log = LogManager.getLogger(AbstractBulkReader.class);
    
    // Instance Fields
    protected final BulkReadListenerCollection listenerCollection = new BulkReadListenerCollection();
    
    // Instance Properties
    private boolean isTransientDataCollectionEnabled = false;
    public boolean getIsTransientDataCollectionEnabled() { return this.isTransientDataCollectionEnabled; }
    public void setIsTransientDataCollectionEnabled(boolean value) { this.isTransientDataCollectionEnabled = value; }

    // Constructor
    protected AbstractBulkReader() {
        // nothing here right now
    }

    // Instance Methods - Abstract
    protected abstract boolean fileIsSupported(File inputFile);
    protected abstract void addRawDataFromFile(
        BulkReadRawData allRawData,
        File inputFile,
        int inputFileIndex,
        Collection<File> allFiles
    ) throws Exception;
    
    // Instance Methods - Concrete
    public void addListener(BulkReadListener listener) {
        this.listenerCollection.addListener(listener);
    }
    
    
    /**
     * A simple implementation leveraging the fileIsSupported method which already exists.  Subclasses may choose to
     * do something more complex, such as allowing a combination of input file types.
     */
    @Override
    public Boolean supports(File... inputFiles) {
        return supports(Arrays.asList(inputFiles));
    }
    @Override
    public Boolean supports(List<File> inputFiles) {
        Boolean supported = true;
        if (inputFiles != null && inputFiles.size() > 0) {
            for (File f : inputFiles) {
                if (!this.fileIsSupported(f)) {
                    supported = false;
                    break;
                }
            }
        }
        return supported;
    }

    @Override
    public BulkReadResult readBulkFrom(BulkReadContext context, File... inputFiles) throws Exception {
        return readBulkFrom(context, Arrays.asList(inputFiles));
    }
    @Override
    public BulkReadResult readBulkFrom(BulkReadContext context, List<File> files) throws Exception {
        this.listenerCollection.fireStart();
    
        this.listenerCollection.fireCheckingFiles(files);
        // Ensure all files are supported
        for (File file : files) {
            try {
                this.ensureFileIsSupported(file);
            } catch (Throwable t) {
                this.listenerCollection.fireFileNotSupported(file, t);
                
                // Rethrow the exception:
                // The caller of the readBulkFrom() method should handle the exception,
                // and that logic shouldn't be altered by anything that is just a listener.
                throw t;
            }
        }

        BulkReadResult result = null;
        try {
            // Create working data
            BulkReadRawData allRawData = new BulkReadRawData(context);
            allRawData.setListenerCollection(this.listenerCollection);
            allRawData.setIsTransientDataCollectionEnabled(this.isTransientDataCollectionEnabled);

            // Read files into memory
            for (int i = 0; i < files.size(); ++i) {
                File file = files.get(i);
                this.listenerCollection.fireStartReadingFile(file);
                this.addRawDataFromFile(allRawData, file, i, files);
                this.listenerCollection.fireFinishedReadingFile(file);
            }

            // Return results
            this.listenerCollection.fireStartProcessingRawTDs();
            List<TrustmarkDefinition> resultingTDs = allRawData.getParsedTds();
            this.listenerCollection.fireFinishedProcessingRawTDs(resultingTDs);
    
            this.listenerCollection.fireStartProcessingRawTIPs();
            List<TrustInteroperabilityProfile> resultingTips = allRawData.getParsedTips();
            this.listenerCollection.fireFinishedProcessingRawTIPs(resultingTips);

            result = new BulkReadResultImpl(resultingTDs, resultingTips, allRawData.getInvalidParameters());
        } catch(Throwable t) {
            log.error("Error performing bulk read!", t);
            this.listenerCollection.fireErrorDuringBulkRead(t);
            
            // Rethrow the exception:
            // The caller of the readBulkFrom() method should handle the exception,
            // and that logic shouldn't be altered by anything that is just a listener.
            throw t;
        }
        this.listenerCollection.fireFinished();
        return result;
    }
    
    protected final void ensureFileIsSupported(File inputFile) throws UnsupportedOperationException {
        if (!this.fileIsSupported(inputFile)) {
            throw new UnsupportedOperationException("File type not supported " + inputFile.getName());
        }
    }
}
