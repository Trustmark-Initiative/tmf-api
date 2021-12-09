package edu.gatech.gtri.trustmark.v1_0.impl.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource.TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource.TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource.TrustExpressionEvaluatorSourceValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorSource.source;
import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureProviderIdentifier;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestTrustExpressionEvaluatorSource {

    @Test
    public void testSourceValue() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> source(null));

        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList).match(
                null,
                (i1, i2, i3, i4) -> null,
                (i1, i2, i3, i4) -> null));

        assertEquals(true, source(trustInteroperabilityProfileNonEmptyList).match(
                ignore -> true,
                (i1, i2, i3, i4) -> false,
                (i1, i2, i3, i4) -> false));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorSourceValue.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList")
                .verify();
    }

    @Test
    public void testSourceTrustmarkDefinitionRequirement() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final List<Trustmark> trustmarkList = nil();

        assertThrows(NullPointerException.class, () -> source(null, trustmarkDefinitionRequirement, trustmarkList));
        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, null, trustmarkList));
        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, (List<Trustmark>) null));

        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).match(
                ignore -> null,
                null,
                (i1, i2, i3, i4) -> null));

        assertEquals(true, source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).match(
                ignore -> false,
                (i1, i2, i3, i4) -> true,
                (i1, i2, i3, i4) -> false));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withPrefabValues(List.class, nil(), arrayList(new TrustmarkImpl()))
                .withPrefabValues(List.class, nil(), arrayList(failureProviderIdentifier(new TrustmarkImpl())))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement", "trustmarkList", "trustmarkVerifierFailureList")
                .verify();
    }

    @Test
    public void testSourceTrustmarkDefinitionParameter() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());

        assertThrows(NullPointerException.class, () -> source(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null, trustmarkNonEmptyList));
        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, null));

        assertThrows(NullPointerException.class, () -> source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).match(
                ignore -> null,
                (i1, i2, i3, i4) -> null,
                null));

        assertEquals(true, source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).match(
                ignore -> false,
                (i1, i2, i3, i4) -> false,
                (i1, i2, i3, i4) -> true));

        EqualsVerifier
                .forClass(TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withPrefabValues(NonEmptyList.class, nel(new TrustmarkImpl()), nel(new TrustmarkImpl(), new TrustmarkImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement", "trustmarkDefinitionParameter", "trustmarkNonEmptyList")
                .verify();
    }

    @Test
    public void testToString() {

        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();
        final NonEmptyList<Trustmark> trustmarkNonEmptyList = nel(new TrustmarkImpl());
        final List<Trustmark> trustmarkList = nil();

        assertTrue(source(trustInteroperabilityProfileNonEmptyList).toString().contains(TrustExpressionEvaluatorSourceValue.class.getSimpleName()));
        assertTrue(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkList).toString().contains(TrustExpressionEvaluatorSourceTrustmarkDefinitionRequirement.class.getSimpleName()));
        assertTrue(source(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter, trustmarkNonEmptyList).toString().contains(TrustExpressionEvaluatorSourceTrustmarkDefinitionParameter.class.getSimpleName()));
    }

}
