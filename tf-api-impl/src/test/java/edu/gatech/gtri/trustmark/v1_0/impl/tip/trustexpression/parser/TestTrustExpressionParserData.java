package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.parser;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralBoolean;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataLiteral.TrustExpressionParserDataLiteralString;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataNonTerminal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataReference.TrustExpressionParserDataReferenceTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.TrustExpressionParserDataReference.TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.NonEmptyList;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.parser.TrustExpressionParserData.dataReferenceTrustmarkDefinitionRequirement;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionParserData {

    @Test
    public void testDataLiteralBoolean() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> dataLiteralBoolean(null, true));

        assertThrows(NullPointerException.class, () -> dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).matchLiteral(
                null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).matchLiteral(
                (i1, i2) -> true,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        EqualsVerifier
                .forClass(TrustExpressionParserDataLiteralBoolean.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "value")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(true, dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).getValue());
    }

    @Test
    public void testDataLiteralDateTimeStamp() {

        final Instant now = Instant.now();

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(null, now));
        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchLiteral(
                (i1, i2) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchLiteral(
                (i1, i2) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionParserDataLiteralDateTimeStamp.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "value")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(now, dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).getValue());
    }

    @Test
    public void testDataLiteralDecimal() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(null, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchLiteral(
                (i1, i2) -> null,
                (i1, i2) -> null,
                null,
                (i1, i2) -> null));

        assertEquals(true, dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchLiteral(
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionParserDataLiteralDecimal.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "value")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(BigDecimal.ONE, dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).getValue());
    }

    @Test
    public void testDataLiteralString() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> dataLiteralString(null, ""));
        assertThrows(NullPointerException.class, () -> dataLiteralString(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").matchLiteral(
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                null));

        assertEquals(true, dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").matchLiteral(
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true));

        EqualsVerifier
                .forClass(TrustExpressionParserDataLiteralString.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "value")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").getTrustInteroperabilityProfileNonEmptyList());
        assertEquals("", dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").getValue());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionRequirement() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement(null, trustmarkDefinitionRequirement));
        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).match(
                ignore -> null,
                null,
                ignore -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).match(
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).matchReference(
                null,
                (i1, i2, i3) -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).matchReference(
                (i1, i2) -> true,
                (i1, i2, i3) -> false));

        EqualsVerifier
                .forClass(TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(trustmarkDefinitionRequirement, dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).getTrustmarkDefinitionRequirement());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameter() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter));
        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).match(
                ignore -> null,
                null,
                ignore -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).match(
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).matchReference(
                (i1, i2) -> null,
                null));

        assertEquals(true, dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).matchReference(
                (i1, i2) -> false,
                (i1, i2, i3) -> true));

        EqualsVerifier
                .forClass(TrustExpressionParserDataReferenceTrustmarkDefinitionParameter.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement", "trustmarkDefinitionParameter")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).getTrustInteroperabilityProfileNonEmptyList());
        assertEquals(trustmarkDefinitionRequirement, dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).getTrustmarkDefinitionRequirement());
        assertEquals(trustmarkDefinitionParameter, dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).getTrustmarkDefinitionParameter());
    }

    @Test
    public void testDataNonTerminal() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> dataNonTerminal(trustInteroperabilityProfileNonEmptyList).match(
                ignore -> null,
                ignore -> null,
                null));

        assertEquals(true, dataNonTerminal(trustInteroperabilityProfileNonEmptyList).match(
                ignore -> false,
                ignore -> false,
                ignore -> true));

        EqualsVerifier
                .forClass(TrustExpressionParserDataNonTerminal.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList")
                .verify();

        assertEquals(trustInteroperabilityProfileNonEmptyList, dataNonTerminal(trustInteroperabilityProfileNonEmptyList).getTrustInteroperabilityProfileNonEmptyList());
    }

    @Test
    public void testToString() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();

        assertTrue(dataLiteralBoolean(trustInteroperabilityProfileNonEmptyList, true).toString().contains(TrustExpressionParserDataLiteralBoolean.class.getSimpleName()));
        assertTrue(dataLiteralDateTimeStamp(trustInteroperabilityProfileNonEmptyList, Instant.now()).toString().contains(TrustExpressionParserDataLiteralDateTimeStamp.class.getSimpleName()));
        assertTrue(dataLiteralDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).toString().contains(TrustExpressionParserDataLiteralDecimal.class.getSimpleName()));
        assertTrue(dataLiteralString(trustInteroperabilityProfileNonEmptyList, "").toString().contains(TrustExpressionParserDataLiteralString.class.getSimpleName()));
        assertTrue(dataReferenceTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement).toString().contains(TrustExpressionParserDataReferenceTrustmarkDefinitionRequirement.class.getSimpleName()));
        assertTrue(dataReferenceTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).toString().contains(TrustExpressionParserDataReferenceTrustmarkDefinitionParameter.class.getSimpleName()));
        assertTrue(dataNonTerminal(trustInteroperabilityProfileNonEmptyList).toString().contains(TrustExpressionParserDataNonTerminal.class.getSimpleName()));
    }

}
