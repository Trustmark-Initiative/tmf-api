package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Calendar;
import java.util.List;

/**
 * The value of a TrustmarkDefinitionParameter as defined by a Trustmark Definition.
 * <br/><br/>
 * Created by brad on 5/20/16.
 */
public interface TrustmarkParameterBinding {

    /**
     * This is a machine readable identifier which is intended to be referenced in the Trust Expressions which use this
     * parmeter.  As of now, this is required to be a simple identifier like: [a-Z_]{1}[a-Z0-9_]*
     */
    public String getIdentifier();

    /**
     * The kind of parameter this is.  Ie, the Type
     * @return
     */
    public ParameterKind getParameterKind();

    /**
     * Returns the value of this parameter, as a generic object.
     */
    public Object getValue();

    /**
     * If Kind == STRING, then this field will return the string given.  Note that the field can always be "coerced"
     * into a string, so this will always return a String representation even if kind != STRING.  Note that Kind == ENUM
     * or Kind == ENUM_MULTI will be returned as a String also.
     */
    public String getStringValue();

    /**
     * If Kind == NUMBER, then this field will contain the numeric value.  Returns null when Kind != NUMBER.
     */
    public Number getNumericValue();

    /**
     * If Kind == BOOLEAN, then this field will contain the Boolean value.  Returns null when Kind != BOOLEAN.
     */
    public Boolean getBooleanValue();

    /**
     * If Kind == DATETIME, then this field will return the value as a java.util.Calendar.  Note that it returns null
     * when Kind != DATETIME.
     */
    public Calendar getDateTimeValue();
    
    /**
     * If Kind == ENUM_MULTI, then this field will return the multi-selection as a List of String values. Returns null
     * when Kind != ENUM_MULTI.
     */
    public List<String> getStringListValue();
    
}/* end TrustmarkParameterBinding */