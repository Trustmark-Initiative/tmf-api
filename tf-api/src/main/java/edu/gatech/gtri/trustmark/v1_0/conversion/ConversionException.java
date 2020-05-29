package edu.gatech.gtri.trustmark.v1_0.conversion;

/**
 * Represents an unexpected error which occurred during the conversion process.
 * <br/><br/>
 * @author brad
 * @date 10/25/16
 */
public class ConversionException extends Exception {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public ConversionException() {
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }

    public ConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}/* end ConversionException */