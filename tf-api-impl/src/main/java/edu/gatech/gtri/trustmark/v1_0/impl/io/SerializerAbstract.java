package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
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
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public abstract class SerializerAbstract implements Serializer {

    private final String name;
    private final String description;
    private final String mimeType;

    public SerializerAbstract(
            final String name,
            final String description,
            final String mimeType) {

        requireNonNull(name);
        requireNonNull(description);
        requireNonNull(mimeType);

        this.name = name;
        this.description = description;
        this.mimeType = mimeType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getOutputMimeFormat() {
        return mimeType;
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final OutputStream outputStream) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);

        serialize(artifact, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final Writer writer) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);

        serialize(artifact, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        throw new UnsupportedOperationException();
    }
}
