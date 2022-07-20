package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkResolverImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustExpressionEvaluationJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkFrameworkIdentifiedObjectImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.TrustExpressionEnvironment;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.parser.TrustExpressionParserImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import org.gtri.fj.data.List;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.product.P3;
import org.gtri.fj.product.P5;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;

import static edu.gatech.gtri.trustmark.v1_0.impl.tip.TestTrustExpressionUtility.normalizeException;
import static edu.gatech.gtri.trustmark.v1_0.impl.tip.TestTrustExpressionUtility.resolver;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTrustmarkAbsent;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTypeUnexpectedLeft;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluation.trustExpressionEvaluation;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueNone;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueString;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData.dataValueStringList;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureResolve;
import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorFailure.evaluatorFailureURI;
import static java.util.Collections.emptyMap;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.TreeMap.iterableTreeMap;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionEvaluatorImpl {

    @Test
    public void test() {
        final TrustExpressionEvaluatorFactory trustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustExpressionEvaluatorFactory.class);
        assertNotNull(trustExpressionEvaluatorFactory);

        final TrustExpressionEvaluator trustExpressionEvaluator = trustExpressionEvaluatorFactory.createDefaultEvaluator();
        assertNotNull(trustExpressionEvaluator);
        assertThrows(NullPointerException.class, () -> new TrustExpressionParserImpl(null, new TrustmarkDefinitionResolverFromMap(emptyMap())));
        assertThrows(NullPointerException.class, () -> new TrustExpressionParserImpl(new TrustInteroperabilityProfileResolverFromMap(emptyMap()), null));

        assertThrows(NullPointerException.class, () -> trustExpressionEvaluator.evaluate(null, list("")));
        assertThrows(NullPointerException.class, () -> trustExpressionEvaluator.evaluate("", null));

        final Trustmark trustmark = new TrustmarkImpl();

        assertThrows(NullPointerException.class, () -> trustExpressionEvaluator.evaluate(null, list(trustmark)));
        assertThrows(NullPointerException.class, () -> trustExpressionEvaluator.evaluate(terminal(fail(nel(failureURI(list(), "", new URISyntaxException("", ""))))), null));
    }

    @Test
    public void testFailure() {
        P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = resolver("B", arrayList("B"));
        P3<TrustmarkResolverFromMap, URI, Trustmark> resolverForTrustmark = resolverForTrustmark();

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());
        final TrustExpressionEvaluatorImpl trustInteroperabilityProfileTrustExpressionEvaluator = new TrustExpressionEvaluatorImpl(
                resolverForTrustmark._1(),
                trustExpressionParser);

        final URISyntaxException uriSyntaxException = new URISyntaxException("", "");
        final ResolveException resolveException = new ResolveException();
        final RuntimeException runtimeException = new RuntimeException();

        assertEquals(
                terminal(fail(nel(failureURI(nil(), "", uriSyntaxException)))),
                trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(
                        terminal(fail(nel(failureURI(nil(), "", uriSyntaxException)))),
                        nil()).getTrustExpression());

        assertEquals(
                trustExpressionEvaluation(list(evaluatorFailureURI("|", uriSyntaxException)), terminal(success(dataValueBoolean(nel(resolver._4()), resolver._5().get("B").some(), nil())))),
                normalizeException(trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(resolver._3().toString(), list(resolverForTrustmark._2().toString(), "|")), uriSyntaxException, resolveException, runtimeException));

        assertEquals(
                trustExpressionEvaluation(list(evaluatorFailureResolve(URI.create("failure"), resolveException)), terminal(success(dataValueBoolean(nel(resolver._4()), resolver._5().get("B").some(), nil())))),
                normalizeException(trustInteroperabilityProfileTrustExpressionEvaluator.evaluate(resolver._3().toString(), list(resolverForTrustmark._2().toString(), "failure")), uriSyntaxException, resolveException, runtimeException));
    }

    @Test
    public void testDataValueBoolean() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("true");

        assertEquals(
                terminal(success(dataValueBoolean(
                        nel(trustInteroperabilityProfile),
                        true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionRequirementBoolean() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionRequirement("A");

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, nil())).toList();

        assertEquals(
                terminal(success(dataValueBoolean(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustmark))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());

        assertEquals(
                terminal(success(dataValueBoolean(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        nil()))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterBoolean() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.BOOLEAN);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "true")))).toList();

        assertEquals(
                terminal(success(dataValueBoolean(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataValueDateTimeStamp() {

        final Instant now = Instant.now();

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile(now.toString());

        assertEquals(
                terminal(success(dataValueDateTimeStamp(
                        nel(trustInteroperabilityProfile),
                        now))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterDateTimeStamp() {

        final Instant now = Instant.now();

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.DATETIME);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", now.toString())))).toList();

        assertEquals(
                terminal(success(dataValueDateTimeStamp(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        now))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataValueDecimal() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("+1.");

        assertEquals(
                terminal(success(dataValueDecimal(
                        nel(trustInteroperabilityProfile),
                        BigDecimal.ONE))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterDecimal() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "+1.")))).toList();

        assertEquals(
                terminal(success(dataValueDecimal(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        BigDecimal.ONE))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataValueString() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("\"string\"");

        assertEquals(
                terminal(success(dataValueString(
                        nel(trustInteroperabilityProfile),
                        "string"))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterStringAsString() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.STRING);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "string")))).toList();

        assertEquals(
                terminal(success(dataValueString(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        "string"))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterStringAsEnum() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.ENUM);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "string")))).toList();

        assertEquals(
                terminal(success(dataValueString(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        "string"))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterStringList() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.ENUM_MULTI);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "string1|string2")))).toList();

        assertEquals(
                terminal(success(dataValueStringList(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail()),
                        arrayList("string1", "string2")))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameterNone() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.ENUM_MULTI);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, nil())).toList();

        assertEquals(
                terminal(success(dataValueNone(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                        nel(trustmark.head(), trustmark.tail())))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkAbsent() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.ENUM_MULTI);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a");

        assertEquals(
                terminal(fail(nel(failureTrustmarkAbsent(
                        nel(trustInteroperabilityProfile),
                        trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                        trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some())))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), nil()).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNot() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.BOOLEAN);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("not A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "true")))).toList();

        assertEquals(
                not(
                        terminal(success(dataValueBoolean(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                true))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), false))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionExistsTrue() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.BOOLEAN);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("exists(A.a)");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "true")))).toList();

        assertEquals(
                exists(
                        terminal(success(dataValueBoolean(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                true))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionExistsFalse() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.BOOLEAN);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("exists(A.a)");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, nil())).toList();

        assertEquals(
                exists(
                        terminal(success(dataValueNone(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail())))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), false))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionAnd() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionRequirement("A");
        trustExpressionEnvironment.setTrustmarkDefinitionRequirement("B");

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A and B");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, nil())).toList();

        assertEquals(
                and(
                        terminal(success(dataValueBoolean(nel(trustInteroperabilityProfile), trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(), trustmark))),
                        terminal(success(dataValueBoolean(nel(trustInteroperabilityProfile), trustExpressionEnvironment.getTrustmarkDefinitionRequirement("B").some(), nil()))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), false))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionOr() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionRequirement("A");
        trustExpressionEnvironment.setTrustmarkDefinitionRequirement("B");

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A or B");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, nil())).toList();

        assertEquals(
                or(
                        terminal(success(dataValueBoolean(nel(trustInteroperabilityProfile), trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(), trustmark))),
                        terminal(success(dataValueBoolean(nel(trustInteroperabilityProfile), trustExpressionEnvironment.getTrustmarkDefinitionRequirement("B").some(), nil()))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThan() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a < A.b");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                lessThan(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThanOrEqual() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.a <= A.b");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                lessThanOrEqual(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThanOrEqual() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.b >= A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                greaterThanOrEqual(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThan() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.b > A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                greaterThan(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionEqual() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.b == A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                equal(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), false))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNotEqual() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.NUMBER);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile = trustExpressionEnvironment.getTrustInteroperabilityProfile("A.b != A.a");
        final List<Trustmark> trustmark = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "1"), p("b", "2")))).toList();

        assertEquals(
                notEqual(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(2)))),
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark.head(), trustmark.tail()),
                                new BigDecimal(1)))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile), trustmark).getTrustExpression());
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionContains() {

        final TrustExpressionEnvironment trustExpressionEnvironment = new TrustExpressionEnvironment();
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "a", ParameterKind.ENUM_MULTI);
        trustExpressionEnvironment.setTrustmarkDefinitionParameter("A", "b", ParameterKind.NUMBER);

        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(trustExpressionEnvironment.getTrustInteroperabilityProfileResolver(), trustExpressionEnvironment.getTrustmarkDefinitionResolver());
        final TrustExpressionEvaluator trustExpressionEvaluator = new TrustExpressionEvaluatorImpl(FactoryLoader.getInstance(TrustmarkResolver.class), trustExpressionParser);
        final TrustInteroperabilityProfile trustInteroperabilityProfile1 = trustExpressionEnvironment.getTrustInteroperabilityProfile("contains(A.a, \"string1\")");
        final List<Trustmark> trustmark1 = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("a", "string1|string2")))).toList();

        assertEquals(
                contains(
                        terminal(success(dataValueStringList(
                                nel(trustInteroperabilityProfile1),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "a").some(),
                                nel(trustmark1.head(), trustmark1.tail()),
                                arrayList("string1", "string2")))),
                        terminal(success(dataValueString(
                                nel(trustInteroperabilityProfile1),
                                "string1"))),
                        success(dataValueBoolean(nel(trustInteroperabilityProfile1), true))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile1), trustmark1).getTrustExpression());

        final TrustInteroperabilityProfile trustInteroperabilityProfile2 = trustExpressionEnvironment.getTrustInteroperabilityProfile("contains(A.b, \"string1\")");
        final List<Trustmark> trustmark2 = trustExpressionEnvironment.getTrustmark("A", iterableTreeMap(stringOrd, arrayList(p("b", "+1.")))).toList();

        assertEquals(
                contains(
                        terminal(success(dataValueDecimal(
                                nel(trustInteroperabilityProfile2),
                                trustExpressionEnvironment.getTrustmarkDefinitionRequirement("A").some(),
                                trustExpressionEnvironment.getTrustmarkDefinitionParameter("A", "b").some(),
                                nel(trustmark2.head(), trustmark2.tail()),
                                BigDecimal.ONE))),
                        terminal(success(dataValueString(
                                nel(trustInteroperabilityProfile2),
                                "string1"))),
                        fail(nel(failureTypeUnexpectedLeft(nel(trustInteroperabilityProfile2), nel(TYPE_STRING_LIST), TrustExpressionTypeDecimal.TYPE_DECIMAL)))),
                trustExpressionEvaluator.evaluate(trustExpressionParser.parse(trustInteroperabilityProfile2), trustmark2).getTrustExpression());
    }

    private P3<TrustmarkResolverFromMap, URI, Trustmark> resolverForTrustmark() {

        final URI trustmarkReferenceURI = URI.create("trustmark");
        final URI trustmarkDefinitionReferenceURI = URI.create("trustmark-definition-reference");

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
