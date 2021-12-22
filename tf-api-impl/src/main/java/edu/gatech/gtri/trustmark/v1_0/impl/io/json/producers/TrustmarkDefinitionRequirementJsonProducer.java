package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.IdUtility;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Option;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.fromString;

@MetaInfServices
public class TrustmarkDefinitionRequirementJsonProducer implements JsonProducer<TrustmarkDefinitionRequirement, JSONObject> {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);
    private static final JsonProducer<TrustmarkFrameworkIdentifiedObject, JSONObject> jsonProducerForTrustmarkFrameworkIdentifiedObject = new TrustmarkFrameworkIdentifiedObjectJsonProducer();
    private static final JsonProducer<Entity, JSONObject> jsonProducerForEntity = new EntityJsonProducer();

    private static final Logger log = LoggerFactory.getLogger(TrustmarkDefinitionRequirementJsonProducer.class);

    @Override
    public Class<TrustmarkDefinitionRequirement> getSupportedType() {
        return TrustmarkDefinitionRequirement.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {
        requireNonNull(trustmarkDefinitionRequirement);

        return new JSONObject(new HashMap<String, Object>() {{
            putAll(jsonProducerForTrustmarkFrameworkIdentifiedObject.serialize(trustmarkDefinitionRequirement).toMap());
            put("$id", trustmarkDefinitionRequirement.getId() == null ? IdUtility.trustmarkDefinitionId() : trustmarkDefinitionRequirement.getId());
            put("$Type", "TrustmarkDefinitionRequirement");
            put("TrustmarkDefinitionReference", serializeTrustmarkDefinitionReference(trustmarkDefinitionRequirement));
            if(trustmarkDefinitionRequirement.getProviderReferences() != null && !trustmarkDefinitionRequirement.getProviderReferences().isEmpty()) {
                Option<JSONArray> providerReferenceJSONArray = serializerProviderReferenceArray(trustmarkDefinitionRequirement);
                if (providerReferenceJSONArray != null)
                    providerReferenceJSONArray.map(providerReferenceArray -> put("ProviderReferences", providerReferenceArray));
            }
        }});
    }

    private static Option<JSONArray> serializerProviderReferenceArray(final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {
        Option<JSONArray> providerReferenceJSONArray;
        providerReferenceJSONArray = NonEmptyList.fromList(fromNull(trustmarkDefinitionRequirement.getProviderReferences())
                .map(List::iterableList)
                .orSome(nil())
                .filter(entity -> {
                    if (entity.getIdentifier() == null || StringUtils.isBlank(entity.getIdentifier().toString())) {
                        return false;
                    }
                    return true;
                })
                .map(provider ->
                        new JSONObject(new HashMap<String, Object>() {{
                            putAll(jsonProducerForEntity.serialize(provider).toMap());
                            put("$id", "provider" + provider.getIdentifier().toString().hashCode());
                        }})))
                .map(nel -> new JSONArray(nel.toCollection()));
        return providerReferenceJSONArray;
    }

    private static JSONObject serializeTrustmarkDefinitionReference(final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {
        return new JSONObject(new HashMap<String, Object>() {{
            fromNull(trustmarkDefinitionRequirement.getIdentifier()).map(value -> put("Identifier", value));
            fromString(trustmarkDefinitionRequirement.getName()).map(value -> put("Name", value));
            fromNull(trustmarkDefinitionRequirement.getNumber()).map(value -> put("Number", value));
            fromString(trustmarkDefinitionRequirement.getVersion()).map(value -> put("Version", value));
            fromString(trustmarkDefinitionRequirement.getDescription()).map(value -> put("Description", value));
        }});
    }
}
