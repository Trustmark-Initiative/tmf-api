package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.EntityImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustInteroperabilityProfileTrustExpressionParserFactory;
import org.gtri.fj.data.List;
import org.gtri.fj.product.P4;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFailure.parserFailureURI;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.Either.left;
import static org.gtri.fj.data.Either.right;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Option.none;
import static org.gtri.fj.data.Option.some;
import static org.gtri.fj.product.P.p;
import static org.gtri.fj.product.Unit.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustInteroperabilityProfileTrustExpressionParserImpl {
    @Test
    public void testNonNull() {

        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        assertNotNull(trustInteroperabilityProfileTrustExpressionParserFactory);

        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();
        assertNotNull(trustInteroperabilityProfileTrustExpressionParser);
        assertThrows(NullPointerException.class, () -> new TrustInteroperabilityProfileTrustExpressionParserImpl(null));

        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionParser.parse((TrustInteroperabilityProfileReference) null));
        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionParser.parse((String) null));
        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionParser.parse((TrustInteroperabilityProfile) null));
        assertThrows(NullPointerException.class, () -> trustInteroperabilityProfileTrustExpressionParser.parse((URI) null));
    }

    @Test
    public void testParse() {

        final P4<TrustInteroperabilityProfileResolverFromMap, URI, TrustInteroperabilityProfile, TrustmarkDefinitionRequirement> resolver = resolver();
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = new TrustInteroperabilityProfileTrustExpressionParserImpl(resolver._1());

        final TrustInteroperabilityProfileReferenceImpl trustInteroperabilityProfileReference = new TrustInteroperabilityProfileReferenceImpl();
        trustInteroperabilityProfileReference.setId("A");
        trustInteroperabilityProfileReference.setIdentifier(resolver._2());

        assertEquals(
                terminal(p(some(resolver._3()), right(p(nel(resolver._3()), resolver._4())))),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfileReference));

        assertEquals(
                terminal(p(some(resolver._3()), right(p(nel(resolver._3()), resolver._4())))),
                trustInteroperabilityProfileTrustExpressionParser.parse(resolver._2()));

        assertEquals(
                terminal(p(some(resolver._3()), right(p(nel(resolver._3()), resolver._4())))),
                trustInteroperabilityProfileTrustExpressionParser.parse(resolver._2().toString()));

        final RuntimeException runtimeException = new RuntimeException();

        assertEquals(
                terminal(left(parserFailureURI(List.nil(), "|", runtimeException))),
                trustInteroperabilityProfileTrustExpressionParser.parse("|").match(
                        terminal -> terminal(terminal._2().bimap(
                                trustExpressionParserFailure -> trustExpressionParserFailure.match(
                                        (trustInteroperabilityProfileList, uriString, exception) -> parserFailureURI(trustInteroperabilityProfileList, uriString, runtimeException),
                                        (trustInteroperabilityProfileList, uri, exception) -> parserFailureResolve(trustInteroperabilityProfileList, uri, exception),
                                        (trustInteroperabilityProfileList, trustExpression, exception) -> parserFailureParser(trustInteroperabilityProfileList, trustExpression, exception),
                                        (trustInteroperabilityProfileList, identifier) -> parserFailureIdentifier(trustInteroperabilityProfileList, identifier)),
                                p -> p)),
                        (operator, expression, data) -> operator.matchUnary(
                                ignore -> not(expression, data)),
                        (operator, left, right, data) -> operator.matchBinary(
                                ignore -> and(left, right, data),
                                ignore -> or(left, right, data))));

        final ResolveException resolveException = new ResolveException();

        assertEquals(
                terminal(left(parserFailureResolve(List.nil(), URI.create("failure"), resolveException))),
                trustInteroperabilityProfileTrustExpressionParser.parse(URI.create("failure")).match(
                        terminal -> terminal(terminal._2().bimap(
                                trustExpressionParserFailure -> trustExpressionParserFailure.match(
                                        (trustInteroperabilityProfileList, uriString, exception) -> parserFailureURI(trustInteroperabilityProfileList, uriString, exception),
                                        (trustInteroperabilityProfileList, uri, exception) -> parserFailureResolve(trustInteroperabilityProfileList, uri, resolveException),
                                        (trustInteroperabilityProfileList, trustExpression, exception) -> parserFailureParser(trustInteroperabilityProfileList, trustExpression, exception),
                                        (trustInteroperabilityProfileList, identifier) -> parserFailureIdentifier(trustInteroperabilityProfileList, identifier)),
                                p -> p)),
                        (operator, expression, data) -> operator.matchUnary(
                                ignore -> not(expression, data)),
                        (operator, left, right, data) -> operator.matchBinary(
                                ignore -> and(left, right, data),
                                ignore -> or(left, right, data))));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithoutTrustExpression() {
        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        final RuntimeException runtimeException = new RuntimeException();

        assertEquals(
                terminal(left(parserFailureParser(nel(trustInteroperabilityProfile), "", runtimeException))),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile).match(
                        terminal -> terminal(terminal._2().bimap(
                                trustExpressionParserFailure -> trustExpressionParserFailure.match(
                                        (trustInteroperabilityProfileList, uriString, exception) -> parserFailureURI(trustInteroperabilityProfileList, uriString, exception),
                                        (trustInteroperabilityProfileList, uri, exception) -> parserFailureResolve(trustInteroperabilityProfileList, uri, exception),
                                        (trustInteroperabilityProfileList, trustExpression, exception) -> parserFailureParser(trustInteroperabilityProfileList, trustExpression, runtimeException),
                                        (trustInteroperabilityProfileList, identifier) -> parserFailureIdentifier(trustInteroperabilityProfileList, identifier)),
                                p -> p)),
                        (operator, expression, data) -> operator.matchUnary(
                                ignore -> not(expression, data)),
                        (operator, left, right, data) -> operator.matchBinary(
                                ignore -> and(left, right, data),
                                ignore -> or(left, right, data))));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionTerminal() {
        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        assertEquals(
                terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNot() {
        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("not A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        assertEquals(
                not(terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), some(trustInteroperabilityProfile)),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionAnd() {
        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A and A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        assertEquals(
                and(terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), some(trustInteroperabilityProfile)),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionOr() {
        final TrustInteroperabilityProfileTrustExpressionParserFactory trustInteroperabilityProfileTrustExpressionParserFactory = FactoryLoader.getInstance(TrustInteroperabilityProfileTrustExpressionParserFactory.class);
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = trustInteroperabilityProfileTrustExpressionParserFactory.createDefaultParser();

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        trustmarkDefinitionRequirement.setId("A");
        trustmarkDefinitionRequirement.setProviderReferences(singletonList(new EntityImpl()));

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A or A");
        trustInteroperabilityProfile.setReferences(singletonList(trustmarkDefinitionRequirement));

        assertEquals(
                or(terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), terminal(p(some(trustInteroperabilityProfile), right(p(nel(trustInteroperabilityProfile), trustmarkDefinitionRequirement)))), some(trustInteroperabilityProfile)),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Test
    public void testTrustInteroperabilityProfile() {

        final P4<TrustInteroperabilityProfileResolverFromMap, URI, TrustInteroperabilityProfile, TrustmarkDefinitionRequirement> resolver = resolver();
        final TrustInteroperabilityProfileTrustExpressionParser trustInteroperabilityProfileTrustExpressionParser = new TrustInteroperabilityProfileTrustExpressionParserImpl(resolver._1());

        final TrustInteroperabilityProfileReferenceImpl trustInteroperabilityProfileReference = new TrustInteroperabilityProfileReferenceImpl();
        trustInteroperabilityProfileReference.setId("A");
        trustInteroperabilityProfileReference.setIdentifier(resolver._2());

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setReferences(singletonList(trustInteroperabilityProfileReference));

        assertEquals(
                terminal(p(some(resolver._3()), right(p(nel(resolver._3(), trustInteroperabilityProfile), resolver._4())))),
                trustInteroperabilityProfileTrustExpressionParser.parse(trustInteroperabilityProfile));
    }

    private P4<TrustInteroperabilityProfileResolverFromMap, URI, TrustInteroperabilityProfile, TrustmarkDefinitionRequirement> resolver() {

        final URI trustInteroperabilityProfileReferenceURI = URI.create("");

        final TrustmarkDefinitionRequirementImpl trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
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

}
