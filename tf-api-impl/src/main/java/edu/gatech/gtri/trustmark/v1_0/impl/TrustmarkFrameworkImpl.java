package edu.gatech.gtri.trustmark.v1_0.impl;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * Created by brad on 1/6/16.
 */
public class TrustmarkFrameworkImpl implements TrustmarkFramework {

    private static final Logger log = Logger.getLogger(TrustmarkFrameworkImpl.class);

    private ResourceBundle tfApiResources;
    private ResourceBundle tfApiImplResources;

    public TrustmarkFrameworkImpl(){
        try{
            tfApiResources = ResourceBundle.getBundle("tfapi");
            tfApiImplResources = ResourceBundle.getBundle("tfapi_impl");
        }catch(Throwable t){
            throw new UnsupportedOperationException("Unable to load the TF API bundles!");
        }

        String apiTfVersion = tfApiResources.getString("tf_version");
        String apiImplTfVersion = tfApiImplResources.getString("tf_version");
        if( !apiImplTfVersion.equalsIgnoreCase(apiTfVersion) ){
            throw new UnsupportedOperationException("The TrustmarkFramework API and IMPL do not agree on the TF versions they support[api={"+apiTfVersion+"}, impl={"+apiImplTfVersion+"}].");
        }

        String apiVersion = tfApiResources.getString("tfapi_version");
        String implApiVersion = tfApiImplResources.getString("tfapi_version");
        if( !apiVersion.equalsIgnoreCase(implApiVersion) ){
            throw new UnsupportedOperationException("The TrustmarkFramework API and IMPL do not agree on the API versions they support[api={"+apiVersion+"}, impl={"+implApiVersion+"}].");
        }

    }

    @Override
    public String getTrustmarkFrameworkVersion() {
        return tfApiResources.getString("tf_version");
    }

    @Override
    public String getApiVersion() {
        return tfApiResources.getString("tfapi_version");
    }

    @Override
    public String getApiBuildDate() {
        return tfApiResources.getString("tfapi_timestamp");
    }

    @Override
    public String getApiImplVersion() {
        return tfApiImplResources.getString("tfapi_impl_version");
    }

    @Override
    public String getApiImplBuildDate() {
        return tfApiImplResources.getString("tfapi_impl_timestamp");
    }



    public static final Class[] userRequiredClasses = {
            URIResolver.class
    };
    /**
     * Performs any necessary "Sanity Checks" on the system before usage can proceed.
     */
    public void validateSystem() {
        log.info("Checking necessary pre-requisites for the TF-API system to run...");

        log.debug("Checking that necessary, but not provided, classes are registered with FactoryLoader appropriately...");
        for( Class c : userRequiredClasses ){
            log.debug("   Finding instance of ["+c.getName()+"]...");
            Object cInstance = FactoryLoader.getInstance(c);
            if( cInstance == null ){
                log.error("Missing required implementation of class["+c.getName()+"]!  Please use either FactoryLoader.register() or ServiceLoader mechanism to register an instance of this class before library usage.");
                throw new UnsupportedOperationException("Missing required implementation of class["+c.getName()+"]");
            }
        }


        // TODO Anything else we need to check?

    }//end TrustmarkFrameworkImpl()

}
