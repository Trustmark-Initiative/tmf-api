package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonUtils;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Trustmark.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Trustmark) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Trustmark trustmark = (Trustmark) instance;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$TMF_VERSION", FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion());
        jsonObject.put("$Type", Trustmark.class.getSimpleName());
        jsonObject.put("Identifier", trustmark.getIdentifier());
        jsonObject.put("TrustmarkDefinitionReference", toJson(trustmark.getTrustmarkDefinitionReference()));
        jsonObject.put("IssueDateTime", JsonUtils.toDateTimeString(trustmark.getIssueDateTime()));
        jsonObject.put("ExpirationDateTime", JsonUtils.toDateTimeString(trustmark.getExpirationDateTime()));
        jsonObject.put("PolicyURL", trustmark.getPolicyURL().toString());
        jsonObject.put("RelyingPartyAgreementURL", trustmark.getRelyingPartyAgreementURL().toString());
        jsonObject.put("StatusURL", trustmark.getStatusURL().toString());
        jsonObject.put("Provider", toJson(trustmark.getProvider()));
        jsonObject.put("Recipient", toJson(trustmark.getRecipient()));

        if( trustmark.hasExceptions() ){
            JSONArray exceptionInfo = new JSONArray();
            for( String exceptionInfostring : trustmark.getExceptionInfo() ){
                exceptionInfo.put(exceptionInfostring);
            }
            jsonObject.put("ExceptionInfo", exceptionInfo);
        }

        if( trustmark.getParameterBindings() != null && !trustmark.getParameterBindings().isEmpty() ){
            JSONArray bindingArray = new JSONArray();
            for(TrustmarkParameterBinding binding : trustmark.getParameterBindings() ){
                JSONObject bindingJson = new JSONObject();
                bindingJson.put("$identifier", binding.getIdentifier());
                bindingJson.put("$kind", binding.getParameterKind().toString());
                bindingJson.put("value", binding.getStringValue());
                bindingArray.put(bindingJson);
            }
            jsonObject.put("ParameterBindings", bindingArray);
        }

        if( trustmark.getDefinitionExtension() != null ){
            jsonObject.put("DefinitionExtensions", toJson(trustmark.getDefinitionExtension()));
        }
        if( trustmark.getProviderExtension() != null ){
            jsonObject.put("ProviderExtensions", toJson(trustmark.getProviderExtension()));
        }
        return jsonObject;
    }



}//end TrustmarkJsonProducer