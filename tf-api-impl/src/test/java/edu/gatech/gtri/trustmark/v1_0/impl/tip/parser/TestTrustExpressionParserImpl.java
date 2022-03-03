package edu.gatech.gtri.trustmark.v1_0.impl.tip.parser;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustInteroperabilityProfileResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.io.TrustmarkDefinitionResolverFromMap;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileReferenceImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.tip.TestTrustExpressionUtility;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfileReference;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserFactory;
import org.gtri.fj.data.TreeMap;
import org.gtri.fj.product.P3;
import org.gtri.fj.product.P5;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.noop;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureCycle;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureParser;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureResolveTrustInteroperabilityProfile;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureURI;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionRequirement;
import static java.lang.System.lineSeparator;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.List.single;
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
    public void testCycle() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, P3<TrustmarkDefinitionRequirement, TrustmarkDefinition, TreeMap<String, TrustmarkDefinitionParameter>>>> resolver = TestTrustExpressionUtility.resolver("A", TreeMap.empty(stringOrd), single(p("A", "trust-interoperability-profile-reference")));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                noop(terminal(fail(nel(failureCycle(nel(resolver._4(), resolver._4()))))), fail(nel(failureCycle(nel(resolver._4(), resolver._4()))))),
                trustExpressionParser.parse(resolver._3()));
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
                not(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionAnd() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A and A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                and(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionOr() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A or A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                or(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThan() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A < A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                lessThan(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionLessThanOrEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A <= A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                lessThanOrEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThanOrEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A >= A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                greaterThanOrEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionGreaterThan() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A > A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                greaterThan(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A == A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                equal(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionNotEqual() {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, TrustmarkDefinitionRequirement>> resolver = TestTrustExpressionUtility.resolver("A != A", arrayList("A"));
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                notEqual(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4()), resolver._5().get("A").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionExists() throws ResolveException {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, P3<TrustmarkDefinitionRequirement, TrustmarkDefinition, TreeMap<String, TrustmarkDefinitionParameter>>>> resolver = TestTrustExpressionUtility.resolver("exists(A.a)", TreeMap.iterableTreeMap(stringOrd, arrayList(p("A", arrayList(p("a", ParameterKind.STRING))))), nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                exists(terminal(success(dataReferenceTrustmarkDefinitionParameter(
                        nel(resolver._4()),
                        TYPE_STRING,
                        resolver._5().get("A").some()._1(),
                        resolver._5().get("A").some()._3().get("a").some()))), success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
                trustExpressionParser.parse(resolver._4()));
    }

    @Test
    public void testTrustmarkDefinitionRequirementWithTrustExpressionContains() throws ResolveException {

        final P5<TrustInteroperabilityProfileResolverFromMap, TrustmarkDefinitionResolverFromMap, URI, TrustInteroperabilityProfile, TreeMap<String, P3<TrustmarkDefinitionRequirement, TrustmarkDefinition, TreeMap<String, TrustmarkDefinitionParameter>>>> resolver = TestTrustExpressionUtility.resolver("contains(A.a, \"string\")", TreeMap.iterableTreeMap(stringOrd, arrayList(p("A", arrayList(p("a", ParameterKind.ENUM_MULTI))))), nil());
        final TrustExpressionParser trustExpressionParser = new TrustExpressionParserImpl(resolver._1(), resolver._2());

        assertEquals(
                contains(
                        terminal(success(dataReferenceTrustmarkDefinitionParameter(
                                nel(resolver._4()),
                                TYPE_STRING_LIST,
                                resolver._5().get("A").some()._1(),
                                resolver._5().get("A").some()._3().get("a").some()))),
                        terminal(success(dataLiteralString(
                                nel(resolver._4()),
                                "string"))),
                        success(dataNonTerminal(nel(resolver._4()), TYPE_BOOLEAN))),
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
        trustInteroperabilityProfile.setIdentifier(URI.create("trust-interoperability-profile"));
        trustInteroperabilityProfile.setTrustExpression("A");
        trustInteroperabilityProfile.setReferences(singletonList(trustInteroperabilityProfileReference));

        assertEquals(
                noop(terminal(success(dataReferenceTrustmarkDefinitionRequirement(nel(resolver._4(), trustInteroperabilityProfile), resolver._5().get("B").some()))), success(dataNonTerminal(nel(trustInteroperabilityProfile), TYPE_BOOLEAN))),
                trustExpressionParser.parse(trustInteroperabilityProfile));
    }

    @Ignore
    @Test
    public void test() throws IOException {

        final TrustExpressionParserFactory trustExpressionParserFactory = FactoryLoader.getInstance(TrustExpressionParserFactory.class);
        final TrustExpressionParser trustExpressionParser = trustExpressionParserFactory.createDefaultParser();

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/edu/gatech/gtri/trustmark/v1_0/impl/jparsec/trustInteroperabilityProfileUrl.txt")));
        String readLine;

        while ((readLine = bufferedReader.readLine()) != null) {

            trustExpressionParser.parse(readLine).getData().f().forEach(nel -> System.out.println(String.join(lineSeparator(), nel.map(TrustExpressionFailure::messageFor))));
        }
    }
}
