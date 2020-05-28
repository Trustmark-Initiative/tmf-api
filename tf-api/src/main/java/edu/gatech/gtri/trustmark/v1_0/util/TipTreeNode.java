package edu.gatech.gtri.trustmark.v1_0.util;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the Tree structure of a TIP.
 * <br/><br/>
 * Created by brad on 5/8/17.
 */
public interface TipTreeNode extends TrustmarkFrameworkIdentifiedObject, Serializable {

    /**
     * If true, it means the system couldn't resolve or parse this TipTreeNode.
     */
    public boolean hasError();

    /**
     * If hasError is true, then this will contain the underlying cause.
     */
    public Throwable getError();


    /**
     * Returns true if this TipTreeNode is a TD
     */
    public boolean isTrustmarkDefinition();

    /**
     * Returns the TrustmarkDefinition object (may make separate network call for this).
     */
    public TrustmarkDefinition getTrustmarkDefinition();

    /**
     * Returns true if this TipTreeNode is a TIP
     */
    public boolean isTrustInteropProfile();

    /**
     * Returns the TIP object (may make separate network call).
     */
    public TrustInteroperabilityProfile getTrustInteropProfile();

    /**
     * If this TipTreeNode is a TIP (as opposed to TD), then this contains the children nodes referenced by this TIP.
     * For TrustmarkDefinitions, this is always an empty list.
     */
    public List<TipTreeNode> getChildren();

}/* end TipTreeNode */