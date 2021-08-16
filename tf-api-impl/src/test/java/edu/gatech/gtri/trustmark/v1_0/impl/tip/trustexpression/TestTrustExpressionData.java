package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralBoolean;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataLiteral.TrustExpressionDataLiteralString;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataNonTerminal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataReference.TrustExpressionDataReferenceTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.TrustExpressionDataReference.TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataReferenceTrustmarkDefinitionRequirement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustExpressionData {

    @Test
    public void testDataLiteralBoolean() {

        assertThrows(NullPointerException.class, () -> dataLiteralBoolean(true).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralBoolean(true).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralBoolean(true).matchLiteral(
                null,
                ignore -> null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralBoolean(true).matchLiteral(
                ignore -> true,
                ignore -> null,
                ignore -> null,
                ignore -> null));

        EqualsVerifier
                .forClass(TrustExpressionDataLiteralBoolean.class)
                .withNonnullFields("value")
                .verify();

        assertEquals(true, dataLiteralBoolean(true).getValue());
    }

    @Test
    public void testDataLiteralDateTimeStamp() {

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(null));

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(Instant.now()).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralDateTimeStamp(Instant.now()).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralDateTimeStamp(Instant.now()).matchLiteral(
                ignore -> null,
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralDateTimeStamp(Instant.now()).matchLiteral(
                ignore -> false,
                ignore -> true,
                ignore -> false,
                ignore -> false));

        EqualsVerifier
                .forClass(TrustExpressionDataLiteralDateTimeStamp.class)
                .withNonnullFields("value")
                .verify();


        final Instant now = Instant.now();

        assertEquals(now, dataLiteralDateTimeStamp(now).getValue());
    }

    @Test
    public void testDataLiteralDecimal() {

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(null));

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(BigDecimal.ONE).match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralDecimal(BigDecimal.ONE).match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralDecimal(BigDecimal.ONE).matchLiteral(
                ignore -> null,
                ignore -> null,
                null,
                ignore -> null));

        assertEquals(true, dataLiteralDecimal(BigDecimal.ONE).matchLiteral(
                ignore -> false,
                ignore -> false,
                ignore -> true,
                ignore -> false));

        EqualsVerifier
                .forClass(TrustExpressionDataLiteralDecimal.class)
                .withNonnullFields("value")
                .verify();

        assertEquals(BigDecimal.ONE, dataLiteralDecimal(BigDecimal.ONE).getValue());
    }

    @Test
    public void testDataLiteralString() {

        assertThrows(NullPointerException.class, () -> dataLiteralString(null));

        assertThrows(NullPointerException.class, () -> dataLiteralString("").match(
                null,
                ignore -> null,
                ignore -> null));

        assertEquals(true, dataLiteralString("").match(
                ignore -> true,
                ignore -> false,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataLiteralString("").matchLiteral(
                ignore -> null,
                ignore -> null,
                ignore -> null,
                null));

        assertEquals(true, dataLiteralString("").matchLiteral(
                ignore -> false,
                ignore -> false,
                ignore -> false,
                ignore -> true));

        EqualsVerifier
                .forClass(TrustExpressionDataLiteralString.class)
                .withNonnullFields("value")
                .verify();

        assertEquals("", dataLiteralString("").getValue());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionRequirement() {

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement(null));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement("").match(
                ignore -> null,
                null,
                ignore -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionRequirement("").match(
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionRequirement("").matchReference(
                null,
                (i1, i2) -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionRequirement("").matchReference(
                ignore -> true,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile.class)
                .withNonnullFields("identifier")
                .verify();

        assertEquals("", dataReferenceTrustmarkDefinitionRequirement("").getIdentifier());
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameter() {

        final String trustmarkDefinitionRequirementIdentifier = "trustmarkDefinitionRequirementIdentifier";
        final String trustmarkDefinitionParameterIdentifier = "trustmarkDefinitionParameterIdentifier";

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(null, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, null));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                ignore -> null,
                null,
                ignore -> null));

        assertEquals(true, dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                ignore -> false,
                ignore -> true,
                ignore -> false));

        assertThrows(NullPointerException.class, () -> dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).matchReference(
                ignore -> null,
                null));

        assertEquals(true, dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).matchReference(
                ignore -> false,
                (i1, i2) -> true));

        EqualsVerifier
                .forClass(TrustExpressionDataReferenceTrustmarkDefinitionParameter.class)
                .withNonnullFields("trustmarkDefinitionRequirementIdentifier", "trustmarkDefinitionParameterIdentifier")
                .verify();

        assertEquals(trustmarkDefinitionRequirementIdentifier, dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).getTrustmarkDefinitionRequirementIdentifier());
        assertEquals(trustmarkDefinitionParameterIdentifier, dataReferenceTrustmarkDefinitionParameter(trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).getTrustmarkDefinitionParameterIdentifier());
    }

    @Test
    public void testDataNonTerminal() {

        assertThrows(NullPointerException.class, () -> dataNonTerminal().match(
                ignore -> null,
                ignore -> null,
                null));

        assertEquals(true, dataNonTerminal().match(
                ignore -> false,
                ignore -> false,
                ignore -> true));
    }

    @Test
    public void testToString() {
        assertTrue(dataLiteralBoolean(true).toString().contains(TrustExpressionDataLiteralBoolean.class.getSimpleName()));
        assertTrue(dataLiteralDateTimeStamp(Instant.now()).toString().contains(TrustExpressionDataLiteralDateTimeStamp.class.getSimpleName()));
        assertTrue(dataLiteralDecimal(BigDecimal.ONE).toString().contains(TrustExpressionDataLiteralDecimal.class.getSimpleName()));
        assertTrue(dataLiteralString("").toString().contains(TrustExpressionDataLiteralString.class.getSimpleName()));
        assertTrue(dataReferenceTrustmarkDefinitionRequirement("").toString().contains(TrustExpressionDataReferenceTrustmarkDefinitionRequirementOrTrustInteroperabilityProfile.class.getSimpleName()));
        assertTrue(dataReferenceTrustmarkDefinitionParameter("", "").toString().contains(TrustExpressionDataReferenceTrustmarkDefinitionParameter.class.getSimpleName()));
        assertTrue(dataNonTerminal().toString().contains(TrustExpressionDataNonTerminal.class.getSimpleName()));
    }

}
