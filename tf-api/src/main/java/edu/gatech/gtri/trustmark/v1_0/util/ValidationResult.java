package edu.gatech.gtri.trustmark.v1_0.util;

/**
 * Encapsulates the result returned during a validation event.
 * <br/><br/>
 * Created by brad on 3/9/17.
 */
public interface ValidationResult {

    /**
     * The severity of this validation result (ie, most notably whether it is fatal or just a warning).
     * <br/><br/>
     * @return a {@link ValidationSeverity}
     */
    public ValidationSeverity getSeverity();

    /**
     * The problem that was found, in human readable text.  Examples include "Missing required Identifier", etc.
     */
    public String getMessage();

    /**
     * The location of the error, for example "Metdata.Identifier".  This is specific to the implementation and artifact.
     */
    public String getLocation();

}
