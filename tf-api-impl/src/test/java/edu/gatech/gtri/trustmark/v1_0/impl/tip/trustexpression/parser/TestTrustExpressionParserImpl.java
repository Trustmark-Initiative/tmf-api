package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.parser;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.TestTrustExpressionUtility;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParserFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluator;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorFactory;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserFactory;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.data.Validation;
import org.gtri.fj.product.P3;
import org.gtri.fj.product.P5;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureResolveTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionFailure.failureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionRequirement;
import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.lang.StringUtility.stringOrd;
import static org.gtri.fj.product.P.p;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionParserImpl {


    @Test
    public void testNonNull() {

        final TrustExpressionParserFactory trustExpressionParserFactory = FactoryLoader.getInstance(TrustExpressionParserFactory.class);
        assertNotNull(trustExpressionParserFactory);

        final TrustExpressionParser trustExpressionParser = trustExpressionParserFactory.createDefaultParser();
        assertNotNull(trustExpressionParser);
        assertThrows(NullPointerException.class, () -> new TrustExpressionParserImpl(null, new TrustmarkDefinitionResolverFromMap(emptyMap())));
        assertThrows(NullPointerException.class, () -> new TrustExpressionParserImpl(new TrustInteroperabilityProfileResolverFromMap(emptyMap()), null));

        assertThrows(NullPointerException.class, () -> trustExpressionParser.parse((TrustInteroperabilityProfileReference) null));
        assertThrows(NullPointerException.class, () -> trustExpressionParser.parse((String) null));
        assertThrows(NullPointerException.class, () -> trustExpressionParser.parse((TrustInteroperabilityProfile) null));
        assertThrows(NullPointerException.class, () -> trustExpressionParser.parse((URI) null));
    }

    @Test
    public void testParse() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("B", arrayList("B"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        final TrustInteroperabilityProfileReferenceImpl trustInteroperabilityProfileReference = new TrustInteroperabilityProfileReferenceImpl();
        trustInteroperabilityProfileReference.setId("A");
        trustInteroperabilityProfileReference.setIdentifier(resolver._3());

        assertEquals(
                terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("B").some()))),
                trustExpressionParser.parse(trustInteroperabilityProfileReference));

        assertEquals(
                terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("B").some()))),
                trustExpressionParser.parse(resolver._3()));

        assertEquals(
                terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("B").some()))),
                trustExpressionParser.parse(resolver._3().toString()));

        final RuntimeException runtimeException = new RuntimeException();
        final ResolveException resolveException = new ResolveException();

        assertEquals(
                terminal(fail(nel(failureURI(nil(), "|", runtimeException)))),
                TestTrustExpressionUtility.normalizeExceptionForTrustExpressionParserData(trustExpressionParser.parse("|"), runtimeException, resolveException));

        assertEquals(
                terminal(fail(nel(failureResolveTrustInteroperabilityProfile(nil(), URI.create("failure"), resolveException)))),
                TestTrustExpressionUtility.normalizeExceptionForTrustExpressionParserData(trustExpressionParser.parse(URI.create("failure")), runtimeException, resolveException));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithoutTrustExpression() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("", nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        final RuntimeException runtimeException = new RuntimeException();
        final ResolveException resolveException = new ResolveException();

        assertEquals(
                terminal(fail(nel(failureParser(nel(resolver._4()), "", runtimeException)))),
                TestTrustExpressionUtility.normalizeExceptionForTrustExpressionParserData(trustExpressionParser.parse(resolver._4()), runtimeException, resolveException));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionBoolean() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("true", nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                terminal(success(dataLiteralBoolean(nel(resolver._4()), true))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionDateTimeStamp() {

        final Instant now = Instant.now();

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver(now.toString(), nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                terminal(success(dataLiteralDateTimeStamp(nel(resolver._4()), now))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionDecimal() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("+1.", nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                terminal(success(dataLiteralDecimal(nel(resolver._4()), BigDecimal.ONE))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionString() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("\"string\"", nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                terminal(success(dataLiteralString(nel(resolver._4()), "string"))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionTerminal() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNot() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("not A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                not(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionAnd() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A and A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                and(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionOr() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A or A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                or(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThan() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A < A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                lessThan(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThanOrEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A <= A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                lessThanOrEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThanOrEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A >= A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                greaterThanOrEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThan() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A > A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                greaterThan(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A == A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                equal(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNotEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A != A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                notEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionExists() throws ResolveException {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, P3<TrustmarkDefinitionRequirement, TrustmarkDefinition, TreeMap<String, TrustmarkDefinitionParameter>>>> resolver = TestTrustExpressionUtility.resolver("exists(A.a)", TreeMap.iterableTreeMap(stringOrd, arrayList(p("A", arrayList("a")))));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                exists(terminal(success(dataReferenceTrustmarkDefinitionParameter(
                        nel(resolver._4()),
                        resolver._5().get("A").some()._1(),
                        resolver._5().get("A").some()._3().get("a").some()))), success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionContains() throws ResolveException {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, P3<TrustmarkDefinitionRequirement, TrustmarkDefinition, TreeMap<String, TrustmarkDefinitionParameter>>>> resolver = TestTrustExpressionUtility.resolver("contains(A.a, \"string\")", TreeMap.iterableTreeMap(stringOrd, arrayList(p("A", arrayList("a")))));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                contains(
                        terminal(success(dataReferenceTrustmarkDefinitionParameter(
                                nel(resolver._4()),
                                resolver._5().get("A").some()._1(),
                                resolver._5().get("A").some()._3().get("a").some()))),
                        terminal(success(dataLiteralString(
                                nel(resolver._4()),
                                "string"))),
                        success(dataNonTerminal(nel(resolver._4())))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustInteroperabilityProfile() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("B", arrayList("B"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        final TrustInteroperabilityProfileReferenceImpl trustInteroperabilityProfileReference = new TrustInteroperabilityProfileReferenceImpl();
        trustInteroperabilityProfileReference.setId("A");
        trustInteroperabilityProfileReference.setIdentifier(resolver._3());

        final TrustInteroperabilityProfileImpl trustInteroperabilityProfile = new TrustInteroperabilityProfileImpl();
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setReferences(singletonList(trustInteroperabilityProfileReference));

        assertEquals(
                terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4(), trustInteroperabilityProfile), resolver._5().get("B").some()))),
                trustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Test
    public void test() {

        final TrustExpressionParserFactory trustExpressionParserFactory = FactoryLoader.getInstance(TrustExpressionParserFactory.class);
        final TrustExpressionParser trustExpressionParser = trustExpressionParserFactory.createDefaultParser();

        final TrustExpressionEvaluatorFactory trustExpressionEvaluatorFactory = FactoryLoader.getInstance(TrustExpressionEvaluatorFactory.class);
        final TrustExpressionEvaluator trustExpressionEvaluator = trustExpressionEvaluatorFactory.createDefaultEvaluator();

        System.out.println(trustExpressionParser.parse("https://artifacts.trustmarkinitiative.org/lib/tips/nief-mandatory-attributes/1.0/"));

        System.out.println(trustExpressionEvaluator.evaluate("https://artifacts.trustmarkinitiative.org/lib/tips/nief-mandatory-attributes/1.0/", nil()).getTrustExpression().getData().fail().head().getTrustInteroperabilityProfileList().map(trustInteroperabilityProfile -> trustInteroperabilityProfile.getIdentifier()));
    }

}
