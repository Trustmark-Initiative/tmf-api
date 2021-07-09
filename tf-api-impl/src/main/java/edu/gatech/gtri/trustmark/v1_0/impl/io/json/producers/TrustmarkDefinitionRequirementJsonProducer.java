package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
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
    private static final JsonProducer<TrustmarkFrameworkIdentifiedObject, JSONObject> jsonProducerForTrustmarkFrameworkIdentifiedObject = jsonManager.findProducerStrict(TrustmarkFrameworkIdentifiedObject.class, JSONObject.class).some();
    private static final JsonProducer<Entity, JSONObject> jsonProducerForEntity = jsonManager.findProducerStrict(Entity.class, JSONObject.class).some();

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
            put("$id", trustmarkDefinitionRequirement.getId());
            put("$Type", "TrustmarkDefinitionRequirement");
            put("TrustmarkDefinitionReference", serializeTrustmarkDefinitionReference(trustmarkDefinitionRequirement));
            serializerProviderReferenceArray(trustmarkDefinitionRequirement).map(providerReferenceArray -> put("ProviderReferences", providerReferenceArray));
        }});
    }

    private static Option<JSONArray> serializerProviderReferenceArray(final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement) {
        return NonEmptyList.fromList(fromNull(trustmarkDefinitionRequirement.getProviderReferences())
                .map(List::iterableList)
                .orSome(nil())
                .map(provider ->
                        new JSONObject(new HashMap<String, Object>() {{
                            putAll(jsonProducerForEntity.serialize(provider).toMap());
                            put("$id", "provider" + provider.getIdentifier().toString().hashCode());
                        }})))
                .map(nel -> new JSONArray(nel.toCollection()));
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
