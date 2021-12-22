package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator.TrustExpressionEvaluatorImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.parser.TrustExpressionParserImpl;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.product.P5;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;

public class TestTrustExpressionEvaluationJsonProducer {

    private static final Logger log = LoggerFactory.getLogger(TrustInteroperabilityProfileJsonProducer.class);
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

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = resolver(trustExpression, arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(
                FactoryLoader.getInstance(TrustmarkResolver.class),
                trustExpressionParser);

        JsonProducer<TrustExpressionEvaluation, JSONObject> jsonProducer = jsonManager.findProducerStrict(TrustExpressionEvaluation.class, JSONObject.class).some();

        log.info(jsonProducer.serialize(trustExpressionEvaluator.evaluate(
                trustExpressionParser.parse(resolver._4()),
                nil())).toString(2));
    }

    private P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver(
            final String trustExpression,
            final List<String> trustmarkDefinitionRequirementIdentifierList) {

        final URI trustInteroperabilityProfileReferenceURI = URI.create("trust-interoperability-profile-reference");

        final TreeMap<String, TrustmarkDefinitionRequirement> trustmarkDefinitionRequirementMap = TreeMap.treeMap(stringOrd, trustmarkDefinitionRequirementIdentifierList.map(trustmarkDefinitionRequirementIdentifier -> {

            final EntityImpl entity = new EntityImpl();
            entity.setIdentifier(URI.create(format("entity-uri-%s", trustmarkDefinitionRequirementIdentifier)));

            final URI trustmarkDefinitionRequirementURI = URI.create(format("trustmark-definition-requirement-%s", trustmarkDefinitionRequirementIdentifier));

            final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
            trustmarkDefinitionRequirement.setIdentifier(trustmarkDefinitionRequirementURI);
            trustmarkDefinitionRequirement.setId(trustmarkDefinitionRequirementIdentifier);
            trustmarkDefinitionRequirement.setProviderReferences(singletonList(entity));

            return p(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionRequirement);
        }));

        final TrustmarkDefinitionImpl trustmarkDefinition = new TrustmarkDefinitionImpl();

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfileReferenced = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfileReferenced.setIdentifier(trustInteroperabilityProfileReferenceURI);
        trustInteroperabilityProfileReferenced.setTrustExpression(trustExpression);
        trustInteroperabilityProfileReferenced.setReferences(trustmarkDefinitionRequirementMap.toList().map(p -> (AbstractTIPReference) p._2()).toCollection());

        final TrustInteroperabilityProfileResolverFromMap trustInteroperabilityProfileResolver = new TrustInteroperabilityProfileResolverFromMap(new HashMap<URI, TrustInteroperabilityProfile>() {{
            put(trustInteroperabilityProfileReferenced.getIdentifier(), trustInteroperabilityProfileReferenced);
        }});

        final TrustmarkDefinitionResolverFromMap trustmarkDefinitionResolverFromMap = new TrustmarkDefinitionResolverFromMap(new HashMap<URI, TrustmarkDefinition>() {{
            trustmarkDefinitionRequirementMap.forEach(p -> put(p._2().getIdentifier(), new TrustmarkDefinitionImpl()));
        }});

        return p(trustInteroperabilityProfileResolver, trustmarkDefinitionResolverFromMap, trustInteroperabilityProfileReferenceURI, trustInteroperabilityProfileReferenced, trustmarkDefinitionRequirementMap);
    }

}
