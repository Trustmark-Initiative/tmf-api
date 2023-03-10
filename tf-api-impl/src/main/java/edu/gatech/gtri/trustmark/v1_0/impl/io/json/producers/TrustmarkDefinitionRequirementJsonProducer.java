package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import org.json.JSONObject;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.Option.fromNull;
import static org.gtri.fj.data.Option.fromString;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

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
        requireNonNull(trustmarkDefinitionRequirement.getId());

        return new JSONObject(new HashMap<String, Object>() {{
            putAll(jsonProducerForTrustmarkFrameworkIdentifiedObject.serialize(trustmarkDefinitionRequirement).toMap());
            put("$id", trustmarkDefinitionRequirement.getId());
            put(ATTRIBUTE_KEY_JSON_TYPE, "TrustmarkDefinitionRequirement");
            put("TrustmarkDefinitionReference", serializeTrustmarkDefinitionReference(trustmarkDefinitionRequirement));
        }});
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
