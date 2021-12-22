package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkParameterBindingImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.json.JSONObject;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupported;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readExtensionOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObject;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readTrustmarkDefinitionReference;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURL;
import static java.util.Objects.requireNonNull;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkJsonDeserializer implements JsonDeserializer<Trustmark> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkJsonDeserializer.class);

    public Trustmark deserialize(final String jsonString) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark JSON . . .");

        final JSONObject jsonObject = new JSONObject(jsonString);

        assertSupported(jsonObject);

        final TrustmarkImpl trustmark = new TrustmarkImpl();

        trustmark.setOriginalSource(jsonString);
        trustmark.setOriginalSourceType(SerializerJson.APPLICATION_JSON);

        trustmark.setExpirationDateTime(readDate(jsonObject, "ExpirationDateTime"));
        trustmark.setIdentifier(readURI(jsonObject, "Identifier"));
        trustmark.setIssueDateTime(readDate(jsonObject, "IssueDateTime"));
        trustmark.setPolicyURL(readURL(jsonObject, "PolicyURL"));
        trustmark.setProvider(readEntity(readJSONObject(jsonObject, "Provider")));
        trustmark.setRecipient(readEntity(readJSONObject(jsonObject, "Recipient")));
        trustmark.setRelyingPartyAgreementURL(readURL(jsonObject, "RelyingPartyAgreementURL"));
        trustmark.setStatusURL(readURL(jsonObject, "StatusURL"));
        trustmark.setTrustmarkDefinitionReference(readTrustmarkDefinitionReference(readJSONObject(jsonObject, "TrustmarkDefinitionReference")));

        readExtensionOption(jsonObject, "DefinitionExtensions").forEach(trustmark::setDefinitionExtension);
        readExtensionOption(jsonObject, "ProviderExtensions").forEach(trustmark::setProviderExtension);

        readJSONObjectList(jsonObject, "ParameterBindings").mapException(TrustmarkJsonDeserializer::readTrustmarkParameterBinding).forEach(trustmark::addParameterBinding);

        readStringList(jsonObject, "ExceptionInfo").forEach(trustmark::addExceptionInfo);

        readStringOption(jsonObject, "$id").forEach(trustmark::setId);

        return trustmark;
    }

    private static TrustmarkParameterBindingImpl readTrustmarkParameterBinding(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        final TrustmarkParameterBindingImpl trustmarkParameterBinding = new TrustmarkParameterBindingImpl();

        trustmarkParameterBinding.setIdentifier(readString(jsonObject, "$identifier"));
        trustmarkParameterBinding.setParameterKind(ParameterKind.fromString(readString(jsonObject, "$kind")));
        trustmarkParameterBinding.setValue(readString(jsonObject, "value"));

        return trustmarkParameterBinding;
    }
}
