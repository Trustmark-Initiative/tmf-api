package edu.gatech.gtri.trustmark.v1_0.conversion;

/**
 * Defines a Trustmark Framework data model version.
 * <br/><br/>
 * @author brad
 * @date 10/25/16
 */
public interface ModelVersion {

    /**
     * The major version supported.  Note, this is something like 1 from "1.0"
     */
    public Integer getMajorVersion();

    /**
     * The minor version supported.  Note, this is something like 2 from "1.2"
     */
    public Integer getMinorVersion();

    /**
     * Returns the version as a printable string, like "1.0"
     */
    public String getStringRepresentation();

}/* end ModelVersion */