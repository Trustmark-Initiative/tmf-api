package edu.gatech.gtri.trustmark.v1_0.impl.model;

/**
 * Indicates that a value could not be coerced into a given type.
 * Created by brad on 5/20/16.
 */
public class CoerceTypeError extends RuntimeException {

    public CoerceTypeError() {
    }

    public CoerceTypeError(String message) {
        super(message);
    }

    public CoerceTypeError(String message, Throwable cause) {
        super(message, cause);
    }

    public CoerceTypeError(Throwable cause) {
        super(cause);
    }

    public CoerceTypeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }




}
