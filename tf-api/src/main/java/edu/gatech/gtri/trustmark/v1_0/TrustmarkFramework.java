package edu.gatech.gtri.trustmark.v1_0;

/**
 * Provides some convenient static methods for accessing information.
 */
public interface TrustmarkFramework {

    /**
     * Returns the version of the Trustmark Framework that this library is compatible with.
     */
    String getTrustmarkFrameworkVersion();

    /**
     * Returns the version of the API.  Should match the Trustmark Framework Version in all cases.
     */
    String getApiVersion();

    /**
     * Returns the timestamp string for when the API was built.
     */
    String getApiBuildDate();


    /**
     * Returns the version of the API implementation.
     */
    String getApiImplVersion();

    /**
     * Returns the timestamp string for when the API Implementation was built.
     */
    String getApiImplBuildDate();


    /**
     * Performs a "Sanity Check" of the TF-API system to make sure it is safe to use.  Examples of things checked by
     * this method are that necessary pre-requisite classes are loaded, etc.
     */
    void validateSystem();

}//end TrustmarkFramework