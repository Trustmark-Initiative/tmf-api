package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkServiceFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by brad on 2/4/16.
 */
public class TrustmarkFrameworkServiceFactoryImpl implements TrustmarkFrameworkServiceFactory {

    @Override
    public TrustmarkFrameworkService createService(String baseURL)  {
        try {
            return new TrustmarkFrameworkServiceImpl(new URL(baseURL));
        }catch(MalformedURLException me){
            throw new UnsupportedOperationException("Bad URL: "+baseURL, me);
        }
    }


}
