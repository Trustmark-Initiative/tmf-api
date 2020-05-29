package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/27/17
 */
public class RemoteFormatException extends RemoteException {

    public RemoteFormatException() {
    }

    public RemoteFormatException(String message) {
        super(message);
    }

    public RemoteFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteFormatException(Throwable cause) {
        super(cause);
    }

    public RemoteFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}/* end RemoteFormatException */