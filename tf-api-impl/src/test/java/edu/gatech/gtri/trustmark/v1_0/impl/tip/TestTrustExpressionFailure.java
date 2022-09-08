package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustInteroperabilityProfileImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionParameterImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpressionLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureExpressionRight;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknown;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureNonTerminalUnexpected;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureParser;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureResolveTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureResolveTrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTrustmarkAbsent;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeMismatch;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableLeft;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureTypeUnorderableRight;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.TrustExpressionFailureURI;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static org.gtri.fj.data.List.arrayList;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionFailure {

    @Test
    public void testFailureURI() {
        final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList = arrayList(new TrustInteroperabilityProfileImpl());
        final String uriString = "";
        final URISyntaxException uriSyntaxException = new URISyntaxException("", "");

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureURI(null, uriString, uriSyntaxException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureURI(trustInteroperabilityProfileList, null, uriSyntaxException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureURI(trustInteroperabilityProfileList, uriString, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureURI(trustInteroperabilityProfileList, uriString, uriSyntaxException).match(
                null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureURI(trustInteroperabilityProfileList, uriString, uriSyntaxException).match(
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureURI.class)
                .withPrefabValues(List.class, nil(), arrayList(new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileList", "uriString", "exception")
                .verify();
    }

    @Test
    public void testFailureResolveTrustInteroperabilityProfile() {
        final List<TrustInteroperabilityProfile> trustInteroperabilityProfileList = arrayList(new TrustInteroperabilityProfileImpl());
        final URI uri = URI.create("");
        final ResolveException resolveException = new ResolveException();

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(null, uri, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, null, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, uri, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, uri, resolveException).match(
                (i1, i2, i3) -> null,
                null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureResolveTrustInteroperabilityProfile(trustInteroperabilityProfileList, uri, resolveException).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1) -> null,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureResolveTrustInteroperabilityProfile.class)
                .withPrefabValues(List.class, nil(), arrayList(new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileList", "uri", "exception")
                .verify();
    }

    @Test
    public void testFailureResolveTrustmarkDefinition() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final URI uri = URI.create("");
        final ResolveException resolveException = new ResolveException();

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustmarkDefinition(null, uri, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustmarkDefinition(trustInteroperabilityProfileNonEmptyList, null, resolveException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustmarkDefinition(trustInteroperabilityProfileNonEmptyList, uri, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureResolveTrustmarkDefinition(trustInteroperabilityProfileNonEmptyList, uri, resolveException).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureResolveTrustmarkDefinition(trustInteroperabilityProfileNonEmptyList, uri, resolveException).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
        EqualsVerifier
                .forClass(TrustExpressionFailureResolveTrustmarkDefinition.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "uri", "exception")
                .verify();
    }

    @Test
    public void testFailureParser() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final String trustExpression = "";
        final RuntimeException runtimeException = new RuntimeException();

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureParser(null, trustExpression, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureParser(trustInteroperabilityProfileNonEmptyList, null, runtimeException));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureParser(trustInteroperabilityProfileNonEmptyList, trustExpression, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureParser(trustInteroperabilityProfileNonEmptyList, trustExpression, runtimeException).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureParser(trustInteroperabilityProfileNonEmptyList, trustExpression, runtimeException).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
        EqualsVerifier
                .forClass(TrustExpressionFailureParser.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustExpression", "exception")
                .verify();
    }

    @Test
    public void testFailureIdentifierUnknown() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final String identifier = "";

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknown(null, identifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, identifier).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureIdentifierUnknown(trustInteroperabilityProfileNonEmptyList, identifier).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
        EqualsVerifier
                .forClass(TrustExpressionFailureIdentifierUnknown.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "identifier")
                .verify();
    }

    @Test
    public void testFailureIdentifierUnexpectedTrustInteroperabilityProfile() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final String trustmarkDefinitionRequirementIdentifier = "";
        final String trustmarkDefinitionParameterIdentifier = "";

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(null, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureIdentifierUnexpectedTrustInteroperabilityProfile(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
        EqualsVerifier
                .forClass(TrustExpressionFailureIdentifierUnexpectedTrustInteroperabilityProfile.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirementIdentifier", "trustmarkDefinitionParameterIdentifier")
                .verify();
    }

    @Test
    public void testFailureIdentifierUnknownTrustmarkDefinitionParameter() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final String trustmarkDefinitionParameterIdentifier = "";

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionParameter(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionParameter.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement", "trustmarkDefinitionParameterIdentifier")
                .verify();
    }

    @Test
    public void testFailureIdentifierUnknownTrustmarkDefinitionRequirement() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final String trustmarkDefinitionRequirementIdentifier = "";
        final String trustmarkDefinitionParameterIdentifier = "";

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(null, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameterIdentifier));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureIdentifierUnknownTrustmarkDefinitionRequirement(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirementIdentifier, trustmarkDefinitionParameterIdentifier).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureIdentifierUnknownTrustmarkDefinitionRequirement.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirementIdentifier", "trustmarkDefinitionParameterIdentifier")
                .verify();
    }

    @Test
    public void testFailureNonTerminalUnexpected() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureNonTerminalUnexpected(null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureNonTerminalUnexpected(trustInteroperabilityProfileNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureNonTerminalUnexpected(trustInteroperabilityProfileNonEmptyList).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
        EqualsVerifier
                .forClass(TrustExpressionFailureNonTerminalUnexpected.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList")
                .verify();
    }

    @Test
    public void testFailureTrustmarkAbsent() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement = new TrustmarkDefinitionRequirementImpl();
        final TrustmarkDefinitionParameter trustmarkDefinitionParameter = new TrustmarkDefinitionParameterImpl();

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTrustmarkAbsent(null, trustmarkDefinitionRequirement, trustmarkDefinitionParameter));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, null, trustmarkDefinitionParameter));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTrustmarkAbsent(trustInteroperabilityProfileNonEmptyList, trustmarkDefinitionRequirement, trustmarkDefinitionParameter).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureTrustmarkAbsent.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustmarkDefinitionRequirement", "trustmarkDefinitionParameter")
                .verify();
    }

    @Test
    public void testFailureTypeUnexpected() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType typeExpected = TYPE_BOOLEAN;
        final TrustExpressionType typeActual = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpected(null, nel(typeExpected), typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, null, typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeUnexpected(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
    }

    @Test
    public void testFailureTypeUnexpectedLeft() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType typeExpected = TYPE_BOOLEAN;
        final TrustExpressionType typeActual = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedLeft(null, nel(typeExpected), typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedLeft(trustInteroperabilityProfileNonEmptyList, null, typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedLeft(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedLeft(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeUnexpectedLeft(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
    }

    @Test
    public void testFailureTypeUnexpectedRight() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType typeExpected = TYPE_BOOLEAN;
        final TrustExpressionType typeActual = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedRight(null, nel(typeExpected), typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedRight(trustInteroperabilityProfileNonEmptyList, null, typeActual));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedRight(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnexpectedRight(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeUnexpectedRight(trustInteroperabilityProfileNonEmptyList, nel(typeExpected), typeActual).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));
    }

    @Test
    public void testFailureTypeMismatch() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType typeLeft = TYPE_BOOLEAN;
        final TrustExpressionType typeRight = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeMismatch(null, typeLeft, typeRight));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeMismatch(trustInteroperabilityProfileNonEmptyList, null, typeRight));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeMismatch(trustInteroperabilityProfileNonEmptyList, typeLeft, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeMismatch(trustInteroperabilityProfileNonEmptyList, typeLeft, typeRight).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeMismatch(trustInteroperabilityProfileNonEmptyList, typeLeft, typeRight).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> true,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureTypeMismatch.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "typeLeft", "typeRight")
                .verify();
    }

    @Test
    public void testFailureTypeUnorderableLeft() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType type = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableLeft(null, type));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableLeft(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableLeft(trustInteroperabilityProfileNonEmptyList, type).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeUnorderableLeft(trustInteroperabilityProfileNonEmptyList, type).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureTypeUnorderableLeft.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "type")
                .verify();
    }

    @Test
    public void testFailureTypeUnorderableRight() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final TrustExpressionType type = TYPE_BOOLEAN;

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableRight(null, type));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableRight(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureTypeUnorderableRight(trustInteroperabilityProfileNonEmptyList, type).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureTypeUnorderableRight(trustInteroperabilityProfileNonEmptyList, type).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureTypeUnorderableRight.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "type")
                .verify();
    }

    @Test
    public void testFailureExpression() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList = nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", "")));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpression(null, trustExpressionFailureNonEmptyList));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpression(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpression(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                null,
                (i1, i2) -> null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureExpression(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureExpression.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withPrefabValues(NonEmptyList.class, nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))), nel(TrustExpressionFailure.failureURI(nil(), "",  new URISyntaxException("", "")), TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustExpressionFailureNonEmptyList")
                .verify();
    }

    @Test
    public void testFailureExpressionLeft() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList = nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", "")));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionLeft(null, trustExpressionFailureNonEmptyList));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionLeft(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionLeft(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                null,
                (i1, i2) -> null));

        assertEquals(true, TrustExpressionFailure.failureExpressionLeft(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true,
                (i1, i2) -> false));

        EqualsVerifier
                .forClass(TrustExpressionFailureExpressionLeft.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withPrefabValues(NonEmptyList.class, nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))), nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", "")), TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustExpressionFailureNonEmptyList")
                .verify();
    }

    @Test
    public void testFailureExpressionRight() {
        final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList = nel(new TrustInteroperabilityProfileImpl());
        final NonEmptyList<TrustExpressionFailure> trustExpressionFailureNonEmptyList = nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", "")));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionRight(null, trustExpressionFailureNonEmptyList));
        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionRight(trustInteroperabilityProfileNonEmptyList, null));

        assertThrows(NullPointerException.class, () -> TrustExpressionFailure.failureExpressionRight(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2, i3) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                (i1, i2) -> null,
                null));

        assertEquals(true, TrustExpressionFailure.failureExpressionRight(trustInteroperabilityProfileNonEmptyList, trustExpressionFailureNonEmptyList).match(
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2, i3) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> false,
                (i1, i2) -> true));

        EqualsVerifier
                .forClass(TrustExpressionFailureExpressionRight.class)
                .withPrefabValues(NonEmptyList.class, nel(new TrustInteroperabilityProfileImpl()), nel(new TrustInteroperabilityProfileImpl(), new TrustInteroperabilityProfileImpl()))
                .withPrefabValues(NonEmptyList.class, nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))), nel(TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", "")), TrustExpressionFailure.failureURI(nil(), "", new URISyntaxException("", ""))))
                .withNonnullFields("trustInteroperabilityProfileNonEmptyList", "trustExpressionFailureNonEmptyList")
                .verify();
    }
}
