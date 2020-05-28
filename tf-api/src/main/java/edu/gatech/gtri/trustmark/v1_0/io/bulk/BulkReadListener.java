package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.io.File;
import java.util.List;

/**
 * An interface for listening and tracking status on the bulk reader.
 * <br/><br/>
 * @user brad
 * @date 11/29/16
 */
public interface BulkReadListener {

    /**
     * Called when the reader begins.
     */
    public void start();

    /**
     * Called when the reader begins.
     */
    public void finished();

    /**
     * Called before the files are checked to make sure they are valid.
     */
    public void checkingFiles(List<File> files);

    /**
     * Indicates that we are reading from one of the files given.
     */
    public void startReadingFile(File file);

    /**
     * Allows the bulk reader to set a generic message of what it's doing, such as "Reading Trustmark Definitions...".
     * Note that this is meant to be called in conjunction with the "setPercentage()" method to inform a process of what's
     * going on and how long it will take.  The method is intentionally vague.
     */
    public void setMessage(String message);

    /**
     * Notify the caller of a percentage complete, of whatever is going on.  Meant to be used in conjunction with the setMessage() method.
     */
    public void setPercentage(Integer percentComplete);

    /**
     * Indicates that we are finished reading from one of the files given.
     */
    public void finishedReadingFile(File file);

    /**
     * called when the system starts processing the given TDs.
     */
    public void startProcessingRawTDs();

    /**
     * called when the system finishes processing the given TDs.
     */
    public void finishedProcessingRawTDs(List<TrustmarkDefinition> tds);

    /**
     * called when the system starts processing the given TIPs.
     */
    public void startProcessingRawTIPs();

    /**
     * called when the system is finsihed processing the given TIPs.
     */
    public void finishedProcessingRawTIPs(List<TrustInteroperabilityProfile> tips);


    /**
     * Called in the event of a processing error.
     */
    public void errorDuringBulkRead(Throwable error);

    /**
     * Called if a file is not supported.
     */
    public void fileNotSupported(File file, Throwable error);

}/* end BulkReadListener */