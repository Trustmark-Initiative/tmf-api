package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import java.io.File;
import java.util.List;

/**
 * Can read bulk TD and TIP data from a collection of given files.
 * @author Nicholas Saney
 * @date 2016-09-07
 */
public interface BulkReader {

    /**
     * BulkReader implementations must allow for attaching a listener (or more than 1) to monitor the status.
     */
    public void addListener(BulkReadListener listener);
    
    /**
     * Reads the TDs and TIPs defined in the given files.
     * @param context    the context for the bulk read
     * @param inputFiles the files to read
     * @return a {@link BulkReadResult}, which contains 
     *         the {@link edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition} objects and 
     *         the {@link edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile} objects 
     *         that are found in the given file(s) 
     * @throws Exception if an error occurs while reading or parsing the given input files
     */
    public BulkReadResult readBulkFrom(BulkReadContext context, File... inputFiles) throws Exception;
    public BulkReadResult readBulkFrom(BulkReadContext context, List<File> inputFiles) throws Exception;


    /**
     * Provide a fairly quick yes/no on whether this BulkReader can support the given files.
     * <br/><br/>
     * @param inputFiles An array of files that will later be passed to readBulkFrom
     * @return true if they are supported, false otherwise.
     */
    public Boolean supports(File ... inputFiles );
    public Boolean supports(List<File> inputFiles );

}
