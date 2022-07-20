package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.SerializerAbstract;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationTrustmarkMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.APPLICATION_JSON;
import static java.util.Objects.requireNonNull;


public final class SerializerJson extends SerializerAbstract {

    private static final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

    public SerializerJson() {
        super(
                SerializerJson.class.getCanonicalName(),
                SerializerJson.class.getCanonicalName(),
                MediaType.APPLICATION_JSON.getMediaType());
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final Agreement artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        AbstractDocumentJsonProducer.serialize(Agreement.class, artifact, writer);
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        AbstractDocumentJsonProducer.serialize(AgreementResponsibilityTemplate.class, artifact, writer);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonObject(artifact, writer);
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final Trustmark artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        if (artifact.getOriginalSourceType().equals(APPLICATION_JSON.getMediaType())) {
            // TODO: Serialize trustmark based on content of trustmark; it's not possible at present, as not all of the information necessary for serialization is present.

            writer.write(artifact.getOriginalSource());
            writer.flush();
        } else {

            serializeHelperForJsonObject(artifact, writer);
        }
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonObject(artifact, writer);
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistryOrganizationTrustmarkMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonObject(artifact, writer);
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkBindingRegistrySystemMap artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonArray(artifact, writer);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonObject(artifact, writer);
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport artifact,
            final Writer writer,
            final Map<Object, Object> model) throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        serializeHelperForJsonObject(artifact, writer);
    }

    private void serializeHelperForJsonObject(
            final Object artifact,
            final Writer writer)
            throws IOException {

        final JSONObject jsonObject = (JSONObject) jsonManager.findProducer(artifact.getClass()).serialize(artifact);
        writer.write(jsonObject.toString(2));
        writer.flush();
    }

    private void serializeHelperForJsonArray(
            final Object artifact,
            final Writer writer)
            throws IOException {

        final JSONArray jsonArray = (JSONArray) jsonManager.findProducer(artifact.getClass()).serialize(artifact);
        writer.write(jsonArray.toString(2));
        writer.flush();
    }
}
