package edu.gatech.gtri.trustmark.v1_0.impl.io.html;

import edu.gatech.gtri.trustmark.v1_0.impl.io.SerializerAbstract;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public final class SerializerHtml extends SerializerAbstract {

    public SerializerHtml() {
        super(
                SerializerHtml.class.getCanonicalName(),
                SerializerHtml.class.getCanonicalName(),
                MediaType.TEXT_HTML.getMediaType());
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition artifact,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        final StringWriter stringWriter = new StringWriter();
        new SerializerJson().serialize(artifact, stringWriter);

        model.put("td", artifact);
        model.put("TRUSTMARK_DEFINITION_JSON", stringWriter.toString());
        SerializerHtmlUtility.process(model, "trustmark-definition.ftlh", writer);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        requireNonNull(artifact);
        requireNonNull(outputStream);
        requireNonNull(model);

        serialize(artifact, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), model);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile artifact,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        requireNonNull(artifact);
        requireNonNull(writer);
        requireNonNull(model);

        final StringWriter stringWriter = new StringWriter();
        new SerializerJson().serialize(artifact, stringWriter);

        model.put("tip", artifact);
        model.put("TIP_JSON", stringWriter.toString());
        SerializerHtmlUtility.process(model, "tip.ftlh", writer);
    }
}
