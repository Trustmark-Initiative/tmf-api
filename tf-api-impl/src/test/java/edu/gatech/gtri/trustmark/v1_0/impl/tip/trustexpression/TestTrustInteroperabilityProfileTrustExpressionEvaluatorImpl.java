package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import org.gtri.fj.product.P3;
import org.gtri.fj.product.P4;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.FAILURE;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.UNKNOWN;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState.SUCCESS;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustInteroperabilityProfileTrustExpressionEvaluatorImpl {

    @Test
    public void test() {
        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        assertNotNull(trustInteroperabilityProfileTrustExpressionEvaluatorFactory);

        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();
        assertNotNull(trustInteroperabilityProfileTrustExpressionEvaluator);
        assertThrows(NullPointerException.class, () -> new TrustInteroperabilityProfileTrustExpressionParserImpl(null));

        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(null, list("")));
        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionEvaluator.evaluate("", null));

        final Trustmark trustmark = new TrustmarkImpl();

        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(null, list(trustmark)));
        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(terminal(p(none(), left(parserFailureURI(list(), "", new RuntimeException())))), null));
    }

    @Test
    public void testFailure() {
        P4<TrustInteroperabilityProfileResolverFromMap, URI, TrustInteroperabilityProfile, TrustmarkDefinitionRequirement> resolverForTrustInteroperabilityProfile = resolverForTrustInteroperabilityProfile();
        P3<TrustmarkResolverFromMap, URI, Trustmark> resolverForTrustmark = resolverForTrustmark();

        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = new TrustInteroperabilityProfileTrustExpressionParserImpl(resolverForTrustInteroperabilityProfile._1());
        final TrustInteroperabilityProfileTrustExpressionEvaluatorImpl trustInteroperabilityProfileTrustExpressionEvaluator = new TrustInteroperabilityProfileTrustExpressionEvaluatorImpl(
                resolverForTrustmark._1(),
                trustInteroperabilityProfileTrustExpressionParser);

        final RuntimeException runtimeException = new RuntimeException();
        final ResolveException resolveException = new ResolveException();

        assertEquals(
                terminal(p(p(none(), UNKNOWN), left(parserFailureURI(nil(), "", runtimeException)))),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        terminal(p(none(), left(parserFailureURI(nil(), "", runtimeException)))),
                        nil()).getTrustExpression());

        assertEquals(
                p(list(evaluatorFailureURI("|", runtimeException)), terminal(p(p(some(resolverForTrustInteroperabilityProfile._3()), FAILURE), right(p(nel(resolverForTrustInteroperabilityProfile._3()), resolverForTrustInteroperabilityProfile._4(), nil()))))),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(resolverForTrustInteroperabilityProfile._2().toString(), list(resolverForTrustmark._2().toString(), "|")).getEvaluation()
                        .map1(list -> list
                                .map(trustExpressionEvaluatorFailure -> trustExpressionEvaluatorFailure.match(
                                        (string, exception) -> evaluatorFailureURI(string, runtimeException),
                                        (uri, exception) -> evaluatorFailureResolve(uri, exception)))));

        assertEquals(
                p(list(evaluatorFailureResolve(URI.create("failure"), resolveException)), terminal(p(p(some(resolverForTrustInteroperabilityProfile._3()), FAILURE), right(p(nel(resolverForTrustInteroperabilityProfile._3()), resolverForTrustInteroperabilityProfile._4(), nil()))))),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(resolverForTrustInteroperabilityProfile._2().toString(), list(resolverForTrustmark._2().toString(), "failure")).getEvaluation()
                        .map1(list -> list
                                .map(trustExpressionEvaluatorFailure -> trustExpressionEvaluatorFailure.match(
                                        (string, exception) -> evaluatorFailureURI(string, exception),
                                        (uri, exception) -> evaluatorFailureResolve(uri, resolveException)))));
    }

    @Test
    public void testTerminal() {

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

        assertEquals(
                terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))),
                        nil()).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNot() {

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("not A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

        assertEquals(
                not(terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))), p(none(), SUCCESS)),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        not(terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), none()),
                        nil()).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionAnd() {

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A and A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

        assertEquals(
                and(terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))), terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))), p(none(), FAILURE)),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        and(terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), none()),
                        nil()).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionOr() {

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A or A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileTrustExpressionEvaluatorFactory trustInteroperabilityProfileTrustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionEvaluatorFactory.class);
        final TrustInteroperabilityProfileTrustExpressionEvaluator trustInteroperabilityProfileTrustExpressionEvaluator = trustInteroperabilityProfileTrustExpressionEvaluatorFactory.createDefaultEvaluator();

        assertEquals(
                or(terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))), terminal(p(p(none(), FAILURE), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement, nil())))), p(none(), FAILURE)),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        or(terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), terminal(p(none(), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), none()),
                        nil()).getTrustExpression());
    }

    private P4<TrustInteroperabilityProfileResolverFromMap, URI, TrustInteroperabilityProfile, TrustmarkDefinitionRequirement> resolverForTrustInteroperabilityProfile() {

        final URI trustInteroperabilityProfileReferenceURI = URI.create("trust-interoperability-profile-reference");
        final URI trustmarkDefinitionRequirementURI = URI.create("trustmarkd-definition-requirement");

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setIdentifier(trustmarkDefinitionRequirementURI);
        trustmarkDefinitionRequirement.setId("B");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfileReferenced = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfileReferenced.setIdentifier(trustInteroperabilityProfileReferenceURI);
        trustInteroperabilityProfileReferenced.setTrustExpression("B");
        trustInteroperabilityProfileReferenced.setReferences(singletonList(trustmarkDefinitionRequirement));

        final TrustInteroperabilityProfileResolverFromMap trustInteroperabilityProfileResolver = new TrustInteroperabilityProfileResolverFromMap(new HashMap<URI, TrustInteroperabilityProfile>() {{
            put(trustInteroperabilityProfileReferenced.getIdentifier(), trustInteroperabilityProfileReferenced);
        }});

        return p(trustInteroperabilityProfileResolver, trustInteroperabilityProfileReferenceURI, trustInteroperabilityProfileReferenced, trustmarkDefinitionRequirement);
    }

    private P3<TrustmarkResolverFromMap, URI, Trustmark> resolverForTrustmark() {

        final URI trustmarkReferenceURI = URI.create("trustmark");
        final URI trustmarkDefinitionReferenceURI = URI.create("turstmark-definition-reference");

        final TrustmarkFrameworkIdentifiedObjectImpl trustmarkDefinitionReference = new TrustmarkFrameworkIdentifiedObjectImpl();
        trustmarkDefinitionReference.setIdentifier(trustmarkDefinitionReferenceURI);

        final TrustmarkImpl trustmarkReferenced = new TrustmarkImpl();
        trustmarkReferenced.setIdentifier(trustmarkReferenceURI);
        trustmarkReferenced.setTrustmarkDefinitionReference(trustmarkDefinitionReference);

        final TrustmarkResolverFromMap trustmarkResolver = new TrustmarkResolverFromMap(new HashMap<URI, Trustmark>() {{
            put(trustmarkReferenced.getIdentifier(), trustmarkReferenced);
        }});

        return p(trustmarkResolver, trustmarkReferenceURI, trustmarkReferenced);
    }
}
