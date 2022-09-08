package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Implementations serialize Trustmark Framework artifacts.
 *
 * @author GTRI Trustmark Team
 */
public interface Serializer {

    /**
     * Returns the name of the serializer.
     *
     * @return the name of the serializer
     */
    String getName();

    /**
     * Returns the description of the serializer.
     *
     * @return the description of the serializer
     */
    String getDescription();

    /**
     * Returns the media type of the artifact.
     *
     * @return the media type of the artifact
     */
    String getOutputMimeFormat();

    void serialize(final Agreement agreement, final OutputStream outputStream) throws IOException;

    void serialize(final Agreement agreement, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final Agreement agreement, final Writer writer) throws IOException;

    void serialize(final Agreement agreement, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final OutputStream outputStream) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final Writer writer) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final OutputStream outputStream) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final Writer writer) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final Trustmark trustmark, final OutputStream outputStream) throws IOException;

    void serialize(final Trustmark trustmark, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final Trustmark trustmark, final Writer writer) throws IOException;

    void serialize(final Trustmark trustmark, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationMap agreement, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationMap agreement, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationMap agreement, final Writer writer) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationMap agreement, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap agreement, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap agreement, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap agreement, final Writer writer) throws IOException;

    void serialize(final TrustmarkBindingRegistryOrganizationTrustmarkMap agreement, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistrySystemMap agreement, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkBindingRegistrySystemMap agreement, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkBindingRegistrySystemMap agreement, final Writer writer) throws IOException;

    void serialize(final TrustmarkBindingRegistrySystemMap agreement, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final Writer writer) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final Writer writer) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final Writer writer, final Map<Object, Object> model) throws IOException;
}
