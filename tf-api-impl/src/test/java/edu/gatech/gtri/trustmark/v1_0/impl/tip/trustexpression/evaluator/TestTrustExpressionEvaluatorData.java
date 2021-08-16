package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueBoolean;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueDecimal;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueNone;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueString;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.TrustExpressionEvaluatorDataValueStringList;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_DATE_TIME_STAMP;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_DECIMAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_NONE;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionType.TYPE_STRING_LIST;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueNone;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorData.dataValueStringList;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.evaluator.TrustExpressionEvaluatorSource.source;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestTrustExpressionEvaluatorData {

    @Test
    public void testDataValueBoolean3() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueBoolean(null, trustmarkDefinitionRequirement, trustmarkList));
        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, null, trustmarkList));
        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList), dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).getSource());
        assertEquals(TrustExpressionType.TYPE_BOOLEAN, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).getType());
        assertEquals(false, ((TrustExpressionEvaluatorDataValueBoolean) dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList)).getValue());

        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).match(
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).match(
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).matchValueNone(
                () -> true,
                () -> false));
    }

    @Test
    public void testDataValueBoolean5() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueBoolean(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true));
        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList, true));
        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList, true));
        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null, true));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).getSource());
        assertEquals(TrustExpressionType.TYPE_BOOLEAN, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).getType());
        assertEquals(true, ((TrustExpressionEvaluatorDataValueBoolean) dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true)).getValue());

        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).match(
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).match(
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).matchValueNone(
                () -> true,
                () -> false));
    }

    @Test
    public void testDataValueBoolean2() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueBoolean(null, true));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList), dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).getSource());
        assertEquals(true, ((TrustExpressionEvaluatorDataValueBoolean) dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true)).getValue());

        assertThrows(NullPointerException.class, () -> dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).match(
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).match(
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueDateTimeStamp5() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();
        final Instant now = Instant.now();

        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now));
        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList, now));
        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList, now));
        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null, now));
        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).getSource());
        assertEquals(now, ((TrustExpressionEvaluatorDataValueDateTimeStamp) dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now)).getValue());
        assertEquals(TYPE_DATE_TIME_STAMP, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).getType());

        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).match(
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, now).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueDateTimeStamp2() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();
        final Instant now = Instant.now();

        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(null, now));
        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList), dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).getSource());
        assertEquals(now, ((TrustExpressionEvaluatorDataValueDateTimeStamp) dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now)).getValue());
        assertEquals(TYPE_DATE_TIME_STAMP, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).getType());

        assertThrows(NullPointerException.class, () -> dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).match(
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, now).matchValueNone(
                () -> true,
                () -> false));
    }

    @Test
    public void testDataValueDecimal5() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueDecimal(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).getSource());
        assertEquals(BigDecimal.ONE, ((TrustExpressionEvaluatorDataValueDecimal) dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE)).getValue());
        assertEquals(TYPE_DECIMAL, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).getType());

        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueDecimal2() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueDecimal(null, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList), dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).getSource());
        assertEquals(BigDecimal.ONE, ((TrustExpressionEvaluatorDataValueDecimal) dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE)).getValue());
        assertEquals(TYPE_DECIMAL, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).getType());

        assertThrows(NullPointerException.class, () -> dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueString5() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueString(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, ""));
        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList, ""));
        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList, ""));
        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null, ""));
        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").getSource());
        assertEquals("", ((TrustExpressionEvaluatorDataValueString) dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "")).getValue());
        assertEquals(TYPE_STRING, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").getType());

        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueString2() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueString(null, ""));
        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList), dataValueString(trustInteroperabilityProfileNonEmptyList, "").getSource());
        assertEquals("", ((TrustExpressionEvaluatorDataValueString) dataValueString(trustInteroperabilityProfileNonEmptyList, "")).getValue());
        assertEquals(TYPE_STRING, dataValueString(trustInteroperabilityProfileNonEmptyList, "").getType());

        assertThrows(NullPointerException.class, () -> dataValueString(trustInteroperabilityProfileNonEmptyList, "").match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(true, dataValueString(trustInteroperabilityProfileNonEmptyList, "").match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> null,
                (i1, i2) -> null));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueString(trustInteroperabilityProfileNonEmptyList, "").matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueStringList() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueStringList(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")));
        assertThrows(NullPointerException.class, () -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")));
        assertThrows(NullPointerException.class, () -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList, arrayList("")));
        assertThrows(NullPointerException.class, () -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null, arrayList("")));
        assertThrows(NullPointerException.class, () -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).getSource());
        assertEquals(arrayList(""), ((TrustExpressionEvaluatorDataValueStringList) dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList(""))).getValue());
        assertEquals(TYPE_STRING_LIST, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).getType());

        assertThrows(NullPointerException.class, () -> dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2) -> null));

        assertEquals(true, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> true,
                (i1, i2) -> null));

        assertEquals(false, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testDataValueNone() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> dataValueNone(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> dataValueNone(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null));

        assertEquals(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList), dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).getSource());
        assertEquals(TYPE_NONE, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).getType());

        assertThrows(NullPointerException.class, () -> dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null));

        assertEquals(true, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> true));

        assertEquals(false, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueBoolean(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueDateTimeStamp(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueDecimal(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueString(
                (i) -> true,
                () -> false));

        assertEquals(false, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueStringList(
                (i) -> true,
                () -> false));

        assertEquals(true, dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).matchValueNone(
                () -> true,
                () -> false));

    }

    @Test
    public void testToString() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertTrue(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).toString().contains(TrustExpressionEvaluatorDataValueBoolean.class.getSimpleName()));
        assertTrue(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, true).toString().contains(TrustExpressionEvaluatorDataValueBoolean.class.getSimpleName()));
        assertTrue(dataValueBoolean(trustInteroperabilityProfileNonEmptyList, true).toString().contains(TrustExpressionEvaluatorDataValueBoolean.class.getSimpleName()));
        assertTrue(dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, Instant.now()).toString().contains(TrustExpressionEvaluatorDataValueDateTimeStamp.class.getSimpleName()));
        assertTrue(dataValueDateTimeStamp(trustInteroperabilityProfileNonEmptyList, Instant.now()).toString().contains(TrustExpressionEvaluatorDataValueDateTimeStamp.class.getSimpleName()));
        assertTrue(dataValueDecimal(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, BigDecimal.ONE).toString().contains(TrustExpressionEvaluatorDataValueDecimal.class.getSimpleName()));
        assertTrue(dataValueDecimal(trustInteroperabilityProfileNonEmptyList, BigDecimal.ONE).toString().contains(TrustExpressionEvaluatorDataValueDecimal.class.getSimpleName()));
        assertTrue(dataValueString(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, "").toString().contains(TrustExpressionEvaluatorDataValueString.class.getSimpleName()));
        assertTrue(dataValueString(trustInteroperabilityProfileNonEmptyList, "").toString().contains(TrustExpressionEvaluatorDataValueString.class.getSimpleName()));
        assertTrue(dataValueStringList(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList, arrayList("")).toString().contains(TrustExpressionEvaluatorDataValueStringList.class.getSimpleName()));
        assertTrue(dataValueNone(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).toString().contains(TrustExpressionEvaluatorDataValueNone.class.getSimpleName()));
    }

    @Test
    public void testEquals() {

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueBoolean.class)
                .withNonnullFields("source", "value")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueDateTimeStamp.class)
                .withNonnullFields("source", "value")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueDecimal.class)
                .withNonnullFields("source", "value")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueString.class)
                .withNonnullFields("source", "value")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueStringList.class)
                .withPrefabValues(List.class, nil(), arrayList(""))
                .withNonnullFields("source", "value")
                .verify();

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorDataValueNone.class)
                .withNonnullFields("source")
                .verify();
    }
}
