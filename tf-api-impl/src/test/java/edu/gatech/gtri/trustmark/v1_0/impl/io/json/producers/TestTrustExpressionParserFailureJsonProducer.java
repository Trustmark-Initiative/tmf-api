package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;

import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;

public class TestTrustExpressionParserFailureJsonProducer {

    private static final Logger log = Logger.getLogger(TIPJsonProducer.class);
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

        JsonProducer<TrustExpressionParserFailure, JSONObject> jsonProducer = jsonManager.findProducerStrict(TrustExpressionParserFailure.class, JSONObject.class).some();

        log.info(jsonProducer.serialize(TrustExpressionParserFailure.parserFailureURI(nil(), "|", new RuntimeException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionParserFailure.parserFailureResolve(nil(), URI.create("uri"), new ResolveException())).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionParserFailure.parserFailureIdentifier(nel(trustInteroperabilityProfile), "identifier")).toString(2));
        log.info(jsonProducer.serialize(TrustExpressionParserFailure.parserFailureParser(nel(trustInteroperabilityProfile), "identifier", new RuntimeException())).toString(2));
    }
}
