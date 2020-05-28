package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

/**
 * An error that occurred while serializing an abstract document.
 */
public class AbstractDocumentSerializationException extends Exception {
    public AbstractDocumentSerializationException() { }
    public AbstractDocumentSerializationException(String message) { super(message); }
    public AbstractDocumentSerializationException(String message, Throwable cause) { super(message, cause); }
    public AbstractDocumentSerializationException(Throwable cause) { super (cause); }
    public AbstractDocumentSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
