package edu.gatech.gtri.trustmark.v1_0.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.io.File;
import java.util.List;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 11/29/16
 */
public class BulkReadListenerAdapter implements BulkReadListener {

    @Override
    public void start() {

    }

    @Override
    public void finished() {

    }

    @Override
    public void checkingFiles(List<File> files) {

    }

    @Override
    public void startReadingFile(File file) {

    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void setPercentage(Integer percentComplete) {

    }

    @Override
    public void finishedReadingFile(File file) {

    }

    @Override
    public void errorDuringBulkRead(Throwable error) {

    }

    @Override
    public void fileNotSupported(File file, Throwable error) {

    }

    @Override
    public void startProcessingRawTDs() {

    }

    @Override
    public void finishedProcessingRawTDs(List<TrustmarkDefinition> tds) {

    }

    @Override
    public void startProcessingRawTIPs() {

    }

    @Override
    public void finishedProcessingRawTIPs(List<TrustInteroperabilityProfile> tips) {

    }
}/* end BulkReadListenerAdapter */