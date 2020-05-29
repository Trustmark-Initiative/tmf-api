package edu.gatech.gtri.trustmark.v1_0.util;


/**
 * Created by brad on 7/27/16.
 */
public class IssuanceCriteriaEvaluationException extends Exception {

    public IssuanceCriteriaEvaluationException() {
    }

    public IssuanceCriteriaEvaluationException(String message) {
        super(message);
    }

    public IssuanceCriteriaEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IssuanceCriteriaEvaluationException(Throwable cause) {
        super(cause);
    }

    public IssuanceCriteriaEvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
