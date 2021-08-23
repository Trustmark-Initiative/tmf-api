package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;

import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;

public class TestTrustExpressionFailureJsonProducer {

    private static final Logger log = LogManager.getLogger(TrustInteroperabilityProfileJsonProducer.class);
    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    @Test
    public void test() {

        final EntityImpl entity = new EntityImpl();
        entity.setIdentifier(URI.create("uri"));

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(entity));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setIdentifier(URI.create("uri"));
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        JsonProducer<TrustExpressionFailure, JSONObject> jsonProducer = jsonManager.findProducerStrict(TrustExpressionFailure.class, JSONObject.class).some();

        log.info(jsonProducer.serialize(TrustExpressionFailure.failureURI(nil(), "|", new RuntimeException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(nil(), URI.create("uri"), new ResolveException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureResolveTrustmarkDefinition(nel(trustInteroperabilityProfile), URI.create("uri"), new ResolveException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureParser(nel(trustInteroperabilityProfile), "identifier", new RuntimeException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureIdentifierUnknown(nel(trustInteroperabilityProfile), "identifier")).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(nel(trustInteroperabilityProfile), "identifier", "identifier")).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, "identifier")).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(nel(trustInteroperabilityProfile), "identifier", "identifier")).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionFailure.failureNonTerminalUnexpected(nel(trustInteroperabilityProfile))).toString(2));
    }
}
