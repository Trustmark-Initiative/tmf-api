package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkJsonDeserializer extends AbstractDeserializer {

    private static final Logger log = LogManager.getLogger(TrustmarkJsonDeserializer.class);


    public static Trustmark deserialize( String jsonString ) throws ParseException {
        log.debug("Deserializing Trustmark JSON...");

        JSONObject jsonObject = new JSONObject(jsonString);
        isSupported(jsonObject);

        TrustmarkImpl trustmark = new TrustmarkImpl();
        trustmark.setOriginalSource(jsonString);
        trustmark.setOriginalSourceType("application/json");

        trustmark.setIdentifier(getUri(jsonObject, "Identifier", true));

        trustmark.setTrustmarkDefinitionReference(readTrustmarkFrameworkIdentifiedObject(jsonObject, "TrustmarkDefinitionReference", true));

        trustmark.setIssueDateTime(getDate(jsonObject, "IssueDateTime", true));
        trustmark.setExpirationDateTime(getDate(jsonObject, "ExpirationDateTime", true));

        trustmark.setPolicyURL(getUrl(jsonObject, "PolicyURL", true));
        trustmark.setRelyingPartyAgreementURL(getUrl(jsonObject, "RelyingPartyAgreementURL", true));
        trustmark.setStatusURL(getUrl(jsonObject, "StatusURL", true));

        trustmark.setProvider(readEntity(jsonObject, "Provider", true));
        trustmark.setRecipient(readEntity(jsonObject, "Recipient", true));

        if( jsonObject.has("ExceptionInfo") ) {
            if( jsonObject.optJSONArray("ExceptionInfo") != null ){
                JSONArray jsonArray = jsonObject.optJSONArray("ExceptionInfo");
                for( int i = 0; i < jsonArray.length(); i++ ){
                    String exceptionInfo = jsonArray.optString(i);
                    if( exceptionInfo != null && exceptionInfo.trim().length() > 0 ){
                        trustmark.addExceptionInfo(exceptionInfo);
                    }
                }
            }else if( jsonObject.optString("ExceptionInfo") != null ){
                trustmark.addExceptionInfo(jsonObject.optString("ExceptionInfo"));
            }
        }

        if( jsonObject.has("ParameterBindings") ){
            JSONArray paramBindingsArray = jsonObject.getJSONArray("ParameterBindings");
            for( int i = 0; i < paramBindingsArray.length(); i++ ){
                JSONObject paramBinding = paramBindingsArray.getJSONObject(i);
                TrustmarkParameterBindingImpl bindingImpl = new TrustmarkParameterBindingImpl();
                bindingImpl.setIdentifier(getString(paramBinding, "$identifier", true));
                bindingImpl.setParameterKind(ParameterKind.fromString(getString(paramBinding, "$kind", true)));
                bindingImpl.setValue(getString(paramBinding, "value", true));
                trustmark.addParameterBinding(bindingImpl);
            }
        }

        trustmark.setDefinitionExtension(readExtension(jsonObject, "DefinitionExtensions", false));
        trustmark.setProviderExtension(readExtension(jsonObject, "ProviderExtensions", false));

        return trustmark;
    }//end deserialize



}//end TrustmarkJsonDeserializer()