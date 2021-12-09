package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class SerializerHtmlEditors extends AbstractSerializer {

    public SerializerHtmlEditors() {
        super(
                "HTML Editor Serializer",
                "Serializes data into HTML, suitable for display and editing in a browser",
                "text/html");
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for Trustmark");
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for TrustmarkStatusReport");
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        model.put("td", trustmarkDefinition);
        model.put("TRUSTMARK_DEFINITION_JSON", "\n\n// Inserted by the TF API \nTRUSTMARK_DEFINITION = " + SerializerHtmlUtility.serialize(trustmarkDefinition));

        // Make sure some required things are present.
        if (!model.containsKey("ADDED_JAVASCRIPT")) {
            model.put("ADDED_JAVASCRIPT", "");
        }

        if (!model.containsKey("MENU_JSON")) {
            model.put("MENU_JSON", "");
        }

        // These two things assert that they aren't trapped in comments at all.
        model.put("ADDED_JAVASCRIPT", "\n\n" + model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n" + model.get("MENU_JSON"));

        SerializerHtmlUtility.process(model, "td-editor.html", writer);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        model.put("tip", trustInteroperabilityProfile);
        model.put("TIP_JSON", "\n\n// Inserted by the TF API \nTIP = " + SerializerHtmlUtility.serialize(trustInteroperabilityProfile));

        // Make sure some required things are present.
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

        // These two things assert that they aren't trapped in comments at all.
        model.put("ADDED_JAVASCRIPT", "\n\n" + model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n" + model.get("MENU_JSON"));

        SerializerHtmlUtility.process(model, "tip-editor.html", writer);
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for Agreement");
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for AgreementResponsibilityTemplate");
    }
}
