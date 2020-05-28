package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

/**
 * An error that occurred while deserializing an abstract document.
 */
public class AbstractDocumentDeserializationException extends ParseException {
    public AbstractDocumentDeserializationException() { }
    public AbstractDocumentDeserializationException(String message) { super(message); }
    public AbstractDocumentDeserializationException(String message, Throwable cause) { super(message, cause); }
    public AbstractDocumentDeserializationException(Throwable cause) { super (cause); }
    public AbstractDocumentDeserializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}