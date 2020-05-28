package edu.gatech.gtri.trustmark.v1_0.model;

import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Indicates that a fatal validation error occurred and the object could not be built.
 * <br/><br/>
 * @author brad
 * @date 3/14/17
 */
public class ValidationBuilderException extends BuilderException {

    public static String getMessageFromResults(Collection<ValidationResult> results){
        String msg = "The object cannot be built due to validation errors.";
        for( ValidationResult result : results ){
            if( result.getSeverity() == ValidationSeverity.FATAL ){
                msg = result.getMessage();
                break;
            }
        }
        return msg;
    }


    public ValidationBuilderException(Collection<ValidationResult> results) {
        super(getMessageFromResults(results));
        this.results = results;
    }

    private Collection<ValidationResult> results = null;

    public Collection<ValidationResult> getAllResults() {
        return results;
    }

    public Collection<ValidationResult> getResults(ValidationSeverity severity) {
        ArrayList<ValidationResult> matchingResults = new ArrayList<>();
        for( ValidationResult cur : this.results ){
            if( cur.getSeverity() == severity ){
                matchingResults.add(cur);
            }
        }
        return matchingResults;
    }

    public Collection<ValidationResult> getFatalResults() {
        return this.getResults(ValidationSeverity.FATAL);
    }

    public Collection<ValidationResult> getWarningResults() {
        return this.getResults(ValidationSeverity.WARNING);
    }


}/* end BuilderException */