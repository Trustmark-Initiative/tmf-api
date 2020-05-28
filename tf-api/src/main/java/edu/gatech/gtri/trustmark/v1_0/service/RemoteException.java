package edu.gatech.gtri.trustmark.v1_0.service;

/**
 * Indicates a failure when working with a Remote TrustmarkFramework service.
 * Created by brad on 2/4/16.
 */
public class RemoteException extends Exception {

    public RemoteException() {
    }

    public RemoteException(String message) {
        super(message);
    }

    public RemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteException(Throwable cause) {
        super(cause);
    }

    public RemoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
