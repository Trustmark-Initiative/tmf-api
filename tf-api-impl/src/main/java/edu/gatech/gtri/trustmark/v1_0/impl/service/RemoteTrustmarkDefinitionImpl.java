package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustmarkDefinition;
import org.json.JSONObject;

/**
 * Created by brad on 2/5/16.
 */
public class RemoteTrustmarkDefinitionImpl extends AbstractTFIDObjectPlus implements RemoteTrustmarkDefinition {

    public RemoteTrustmarkDefinitionImpl(){}
    public RemoteTrustmarkDefinitionImpl(JSONObject tdObj) throws RemoteException {
        super(TYPE_NAME_TRUSTMARK_DEFINITION_REMOTE, tdObj);
    }

}
