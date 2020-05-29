package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.Collection;

/**
 * Indicates that an object can be Superseded by another one, or supersede one.
 * <br/><br/>
 * @user brad
 * @date 10/12/16
 */
public interface Supersedable {
    /**
     * A TrustmarkFrameworkIdentifiedObject can declare that it supersedes another TrustmarkFrameworkIdentifiedObject
     */
    public Collection<TrustmarkFrameworkIdentifiedObject> getSupersedes();

    /**
     * A TrustmarkFrameworkIdentifiedObject can be superseded by other TrustmarkFrameworkIdentifiedObjects.
     */
    public Collection<TrustmarkFrameworkIdentifiedObject> getSupersededBy();

    /**
     * If true, it indicates this TrustmarkFrameworkIdentifiedObject should no longer be used.
     * @return
     */
    public Boolean isDeprecated();

    /**
     * A TrustmarkFrameworkIdentifiedObject can be superseded by other TrustmarkFrameworkIdentifiedObjects.
     */
    public Collection<TrustmarkFrameworkIdentifiedObject> getSatisfies();

    /**
     * A TrustmarkFrameworkIdentifiedObject can be in conflict with other TrustmarkFrameworkIdentifiedObjects, they are
     * represented here.
     */
    public Collection<TrustmarkFrameworkIdentifiedObject> getKnownConflicts();


}/* end Supersedable */