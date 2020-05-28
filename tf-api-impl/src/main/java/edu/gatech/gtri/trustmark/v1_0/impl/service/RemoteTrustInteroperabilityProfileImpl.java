package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustmarkDefinition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brad on 2/5/16.
 */
public class RemoteTrustInteroperabilityProfileImpl extends AbstractTFIDObjectPlus implements RemoteTrustInteroperabilityProfile {

    public RemoteTrustInteroperabilityProfileImpl() {}
    public RemoteTrustInteroperabilityProfileImpl(JSONObject tipJson) throws RemoteException {
        super("RemoteTrustInteroperabilityProfile", tipJson);
    }

}
