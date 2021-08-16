package edu.gatech.gtri.trustmark.v1_0.impl.io.bulk;

import edu.gatech.gtri.trustmark.v1_0.io.bulk.BulkReadListener;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 11/29/2016.
 * Reorganized by Nick on 12/05/2016.
 */
public class BulkReadListenerCollection {
    
    private static final Logger log = LogManager.getLogger(BulkReadListenerCollection.class);
    
    private List<BulkReadListener> listeners;
    
    public void addListener(BulkReadListener listener) {
        if (this.listeners == null)
            this.listeners = new ArrayList<>();
        this.listeners.add(listener);
    }
    
    public void fireStart() {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.start();
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].start", t);
                }
            }
        }
    }
    
    public void fireCheckingFiles(List<File> files) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.checkingFiles(files);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].checkingFiles", t);
                }
            }
        }
    }
    
    public void fireFileNotSupported(File file, Throwable t) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.fileNotSupported(file, t);
                } catch (Throwable t2) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].fileNotSupported", t2);
                }
            }
        }
    }
    
    public void fireFinished() {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.finished();
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].finished", t);
                }
            }
        }
    }
    
    public void fireStartReadingFile(File f) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.startReadingFile(f);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].startReadingFile", t);
                }
            }
        }
    }
    
    public void fireFinishedReadingFile(File f) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.finishedReadingFile(f);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].finishedReadingFile", t);
                }
            }
        }
    }
    
    public void fireErrorDuringBulkRead(Throwable error) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.errorDuringBulkRead(error);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].errorDuringBulkRead", t);
                }
            }
        }
    }
    
    public void fireSetMessage(String msg) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.setMessage(msg);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].setMessage", t);
                }
            }
        }
    }
    
    public void fireSetPercentage(Integer p) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.setPercentage(p);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].setPercentage", t);
                }
            }
        }
    }
    
    public void fireStartProcessingRawTDs() {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.startProcessingRawTDs();
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].startProcessingRawTDs", t);
                }
            }
        }
    }
    
    public void fireStartProcessingRawTIPs() {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.startProcessingRawTIPs();
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].startProcessingRawTIPs", t);
                }
            }
        }
    }
    
    
    public void fireFinishedProcessingRawTDs(List<TrustmarkDefinition> tds) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.finishedProcessingRawTDs(tds);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].fireFinishedProcessingRawTDs", t);
                }
            }
        }
    }
    
    public void fireFinishedProcessingRawTIPs(List<TrustInteroperabilityProfile> tips) {
        if (this.listeners != null) {
            for (BulkReadListener listener : this.listeners) {
                try {
                    listener.finishedProcessingRawTIPs(tips);
                } catch (Throwable t) {
                    log.error("Error calling listener[" + listener.getClass().getName() + "].finishedProcessingRawTIPs", t);
                }
            }
        }
    }
}
