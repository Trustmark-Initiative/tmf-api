package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParserFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.product.P.p;

public class TestTrustExpressionEvaluationJsonProducer {

    private static final Logger log = Logger.getLogger(TIPJsonProducer.class);
    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    @Test
    public void test() {
        testHelper("A");
        testHelper("not A");
        testHelper("A and A");
        testHelper("A and not A");
        testHelper("A and A and A");
        testHelper("(A or A or A) and (A or A or A) and (A or A or A)");
        testHelper("A or A");
        testHelper("A or not A");
        testHelper("A or A or A");
        testHelper("A or A or A or A");
        testHelper("A and A and A or A and A and A or A and A and A");
    }

    private void testHelper(String trustExpression) {

        final EntityImpl entity = new EntityImpl();
        entity.setIdentifier(URI.create("uri"));

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(entity));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression(trustExpression);
        trustInteroperabilityProfile.setIdentifier(URI.create("uri"));
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducer = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();

        log.info(jsonProducer.serialize(trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile),
                nil())).toString(2));
    }

}
