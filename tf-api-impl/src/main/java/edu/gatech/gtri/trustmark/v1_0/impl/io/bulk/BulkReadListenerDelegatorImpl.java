package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadListener;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 5/3/17
 */
public class BulkReadListenerDelegatorImpl implements BulkReadListener {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(BulkReadListenerDelegatorImpl.class);
    //==================================================================================================================
    //  Instance Variables
    //==================================================================================================================
    private List<BulkReadListener> listeners = new ArrayList<>();
    //==================================================================================================================
    //  GETTERS/SETTERS/ADDERS
    //==================================================================================================================
    public List<BulkReadListener> getListeners() {
        if( listeners == null )
            listeners = new ArrayList<>();
        return listeners;
    }
    public void setListeners(List<BulkReadListener> listeners) {
        this.listeners = listeners;
    }
    public BulkReadListenerDelegatorImpl addListener(BulkReadListener listener){
        if( !this.getListeners().contains(listener) )
            this.getListeners().add(listener);
        return this;
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

    @Override
    public void start() {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.start();
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".start()!", t);
            }
        }
    }

    @Override
    public void finished() {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.finished();
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".finished()!", t);
            }
        }
    }

    @Override
    public void checkingFiles(List<File> files) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.checkingFiles(files);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".checkingFiles(files)!", t);
            }
        }
    }

    @Override
    public void startReadingFile(File file) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.startReadingFile(file);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".startReadingFile(file)!", t);
            }
        }
    }

    @Override
    public void setMessage(String message) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.setMessage(message);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".setMessage(message)!", t);
            }
        }
    }

    @Override
    public void setPercentage(Integer percentComplete) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.setPercentage(percentComplete);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".setPercentage(percentComplete)!", t);
            }
        }
    }

    @Override
    public void finishedReadingFile(File file) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.finishedReadingFile(file);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".finishedReadingFile(file)!", t);
            }
        }
    }

    @Override
    public void startProcessingRawTDs() {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.startProcessingRawTDs();
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".startProcessingRawTDs()!", t);
            }
        }
    }

    @Override
    public void finishedProcessingRawTDs(List<TrustmarkDefinition> tds) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.finishedProcessingRawTDs(tds);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".finishedProcessingRawTDs(tds)!", t);
            }
        }
    }

    @Override
    public void startProcessingRawTIPs() {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.startProcessingRawTIPs();
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".startProcessingRawTIPs()!", t);
            }
        }
    }

    @Override
    public void finishedProcessingRawTIPs(List<TrustInteroperabilityProfile> tips) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.finishedProcessingRawTIPs(tips);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".finishedProcessingRawTIPs(tips)!", t);
            }
        }
    }

    @Override
    public void errorDuringBulkRead(Throwable error) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.errorDuringBulkRead(error);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".errorDuringBulkRead(error)!", t);
            }
        }
    }

    @Override
    public void fileNotSupported(File file, Throwable error) {
        for( BulkReadListener listener : this.getListeners() ){
            try{
                listener.fileNotSupported(file, error);
            }catch(Throwable t){
                log.error("Error executing BulkReadListener: "+listener.getClass().getName()+".fileNotSupported(file, error)!", t);
            }
        }
    }
}/* end BulkReadListenerDelegatorImpl */