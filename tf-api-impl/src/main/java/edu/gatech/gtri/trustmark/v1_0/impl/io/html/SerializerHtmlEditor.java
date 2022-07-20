package edu.gatech.gtri.trustmark.v1_0.impl.io.html;

import edu.gatech.gtri.trustmark.v1_0.impl.io.SerializerAbstract;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_HTML;
import static java.util.Objects.requireNonNull;

public final class SerializerHtmlEditor extends SerializerAbstract {

    public SerializerHtmlEditor() {
        super(
                SerializerHtmlEditor.class.getCanonicalName(),
                SerializerHtmlEditor.class.getCanonicalName(),
                TEXT_HTML.getMediaType());
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
        model.put("TRUSTMARK_DEFINITION_JSON", "\n\n// Inserted by the TF API \nTRUSTMARK_DEFINITION = " + stringWriter.toString());

        if (!model.containsKey("ADDED_JAVASCRIPT")) {
            model.put("ADDED_JAVASCRIPT", "");
        }

        if (!model.containsKey("MENU_JSON")) {
            model.put("MENU_JSON", "");
        }

        model.put("ADDED_JAVASCRIPT", "\n\n" + model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n" + model.get("MENU_JSON"));

        SerializerHtmlUtility.process(model, "td-editor.html", writer);
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
        model.put("TIP_JSON", "\n\n// Inserted by the TF API \nTIP = " + stringWriter.toString());

        if (!model.containsKey("ADDED_JAVASCRIPT")) {
            model.put("ADDED_JAVASCRIPT", "");
        }

        if (!model.containsKey("MENU_JSON")) {
            model.put("MENU_JSON", "");
        }

        if (!model.containsKey("SEARCH_URL")) {
            model.put("SEARCH_URL", "");
        } else {
            model.put("SEARCH_URL", "\n\nSEARCH_URL = '" + model.get("SEARCH_URL") + "';");
        }

        if (!model.containsKey("PROVIDER_LIST_JSON")) {
            model.put("PROVIDER_LIST_JSON", "");
        } else {
            model.put("PROVIDER_LIST_JSON", "\n\nPROVIDERS = " + model.get("PROVIDER_LIST_JSON") + ";");
        }

        model.put("ADDED_JAVASCRIPT", "\n\n" + model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n" + model.get("MENU_JSON"));

        SerializerHtmlUtility.process(model, "tip-editor.html", writer);
    }
}
