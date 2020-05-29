package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;

/**
 * Represents something in the framework that contains an identifier, name, version, and description.  A useful
 * abstraction for displaying objects uniquely in a system.
 * <br/><br/>
 * Created by brad on 12/7/15.
 */
public interface TrustmarkFrameworkIdentifiedObject {

    /**
     * Returns the type of this object (ie, Trustmark, TrustmarkDefinition, TrustInteroperabilityProfile, etc).  In
     * general, assume this is just a short cut for "this.getClass().getSimpleName()".
     */
    public String getTypeName();

    /**
     * The globally-unique Identifier of this object.  Guaranteed to be non-null.
     */
    public URI getIdentifier();

    /**
     * The Name of this object. May be null depending on context.
     */
    public String getName();

    /**
     * The Number or order of this object. May be null depending on context.
     */
    public Integer getNumber();

    /**
     * The Version of this object. May be null depending on context.
     */
    public String getVersion();

    /**
     * The Description of this object. May be null.
     */
    public String getDescription();

}//end TrustmarkFrameworkIdentifiedObject