package edu.gatech.gtri.trustmark.v1_0.service;

/**
 * Reponsible for creating instances of {@link TrustmarkFrameworkService} to connect to remote TF capable servers.
 * <br/><br/>
 * Created by brad on 2/4/16.
 */
public interface TrustmarkFrameworkServiceFactory {


    /**
     * Creates the service based on the given remote URL.  All TF API operations will use this URL as the basis for
     * queries.
     */
    public TrustmarkFrameworkService createService(String baseURL);

}
