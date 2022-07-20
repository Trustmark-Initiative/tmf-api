package edu.gatech.gtri.trustmark.v1_0;

/**
 * Implementations describe the tf-api and tf-api-impl libraries and the
 * Trustmark Framework version they support.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkFramework {

    /**
     * Returns the Trustmark Framework version that the tf-api supports.
     *
     * @return the Trustmark Framework version that the tf-api supports
     */
    String getTrustmarkFrameworkVersion();

    /**
     * Returns the Trustmark Framework version that the tf-api-impl supports.
     *
     * @return the Trustmark Framework version that the tf-api-impl supports
     */
    String getTrustmarkFrameworkVersionImpl();

    /**
     * Returns the tf-api version.
     *
     * @return the tf-api version
     */
    String getApiVersion();

    /**
     * Returns the tf-api-parent version of tf-api.
     *
     * @return the tf-api-parent version of tf-api
     */
    String getApiParentVersion();

    /**
     * Returns the tf-api timestamp as a String (for example, 2022-03-11
     * 15:15:25).
     *
     * @return the tf-api timestamp as a String (for example, 2022-03-11
     * 15:15:25)
     */
    String getApiBuildDate();

    /**
     * Returns the tf-api-impl version.
     *
     * @return the tf-api-impl version
     */
    String getApiImplVersion();

    /**
     * Returns the tf-api-parent version of tf-api-impl.
     *
     * @return the tf-api-parent version of tf-api-impl
     */
    String getApiImplParentVersion();

    /**
     * Returns the tf-api-impl timestamp as a String (for example, 2022-03-11
     * 15:15:25).
     *
     * @return the tf-api-impl timestamp as a String (for example, 2022-03-11
     * 15:15:25)
     */
    String getApiImplBuildDate();

    /**
     * Verifies that the classes the system requires are present.
     */
    void validateSystem();
}
