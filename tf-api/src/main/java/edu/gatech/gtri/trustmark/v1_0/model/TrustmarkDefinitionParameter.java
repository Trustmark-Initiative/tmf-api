package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.List;

/**
 * A parameter defined by a TrustmarkDefinition.
 * <br/><br/>
 * Created by brad on 5/20/16.
 */
public interface TrustmarkDefinitionParameter {

    /**
     * This is a machine readable identifier which is intended to be referenced in the Trust Expressions which use this
     * parmeter.  As of now, this is required to be a simple identifier like: [a-Z_]{1}[a-Z0-9_]*
     */
    public String getIdentifier();

    /**
     * Should be a name which is meaningful to an end user.  This would be displayed during field collection on a user interface.
     */
    public String getName();

    /**
     * A tool-tip type text describing this field to an end user.  This can be multiple sentences of HTML formatted text.
     */
    public String getDescription();

    /**
     * The kind of parameter that this is.
     */
    public ParameterKind getParameterKind();

    /**
     * If the parameter is of kind ENUM, then this field lists the possible values that the enum can have.
     */
    public List<String> getEnumValues();

    /**
     * Whether or not this field MUST be collected to create a valid Trustmark.  If true, then ALL trustmarks based on
     * the TD containing this parameter will have this value.
     */
    public Boolean isRequired();

}/* end TrustmarkDefinitionParameter */