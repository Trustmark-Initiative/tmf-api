package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.tip.TIPEvaluationException;

/**
 * Indicates that the TIP being evaluated depends on the evaluation of a sub-TIP that does not exist or has not yet
 * been evaluated.  In all circumstances, this is an error, since the TIP should have been evaluated by the point this
 * was thrown.
 * <br/><br/>
 * Created by brad on 5/31/16.
 */
public class MissingPreviousEvaluationException extends TIPEvaluationException {
    public MissingPreviousEvaluationException(String message) {
        super(message);
    }

    public MissingPreviousEvaluationException(Throwable cause) {
        super(cause);
    }
}
