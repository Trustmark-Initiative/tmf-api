package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.TrustmarkFrameworkService;

/**
 * Created by Nicholas Saney on 2017-06-26.
 */
@FunctionalInterface
public interface ServiceReferenceNameResolver {
    TrustmarkFrameworkIdentifiedObject resolve(TrustmarkFrameworkService tfs, String reference) throws RemoteException;
}
