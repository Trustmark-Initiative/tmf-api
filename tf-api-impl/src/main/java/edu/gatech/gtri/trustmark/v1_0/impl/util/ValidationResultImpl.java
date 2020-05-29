package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.util.ValidationResult;
import edu.gatech.gtri.trustmark.v1_0.util.ValidationSeverity;

/**
 * Created by brad on 3/9/17.
 */
public class ValidationResultImpl implements ValidationResult {

    public ValidationResultImpl(String message){
        this(ValidationSeverity.FATAL, message, null);
    }
    public ValidationResultImpl(ValidationSeverity severity, String message){
        this(severity, message, null);
    }
    public ValidationResultImpl(ValidationSeverity severity, String message, String location){
        this.setSeverity(severity);
        this.setMessage(message);
        this.setLocation(location);
    }

    private ValidationSeverity severity;
    private String message;
    private String location;

    @Override
    public ValidationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ValidationSeverity severity) {
        this.severity = severity;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return String.format("[%s] %s", this.getSeverity(), this.getMessage());
    }
}
