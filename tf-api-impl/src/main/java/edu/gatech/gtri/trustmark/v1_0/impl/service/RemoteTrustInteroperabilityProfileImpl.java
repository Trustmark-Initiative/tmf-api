package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustInteroperabilityProfile;
import org.json.JSONObject;

/**
 * Created by brad on 2/5/16.
 */
public class RemoteTrustInteroperabilityProfileImpl extends AbstractTFIDObjectPlus implements RemoteTrustInteroperabilityProfile {

    public RemoteTrustInteroperabilityProfileImpl() {}
    public RemoteTrustInteroperabilityProfileImpl(JSONObject tipJson) throws RemoteException {
        super(TYPE_NAME_TRUST_INTEROPERABILITY_PROFILE_REMOTE, tipJson);
    }
}
