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

public class SerializerHtml extends AbstractSerializer {

    public static final String TEXT_HTML = "text/html";

    public SerializerHtml() {
        super(
                "HTML Serializer",
                "Serializes data into HTML, suitable for display in a browser",
                TEXT_HTML);
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
        model.put("TRUSTMARK_DEFINITION_JSON", SerializerHtmlUtility.serialize(trustmarkDefinition));
        SerializerHtmlUtility.process(model, "trustmark-definition.ftlh", writer);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        model.put("tip", trustInteroperabilityProfile);
        model.put("TIP_JSON", SerializerHtmlUtility.serialize(trustInteroperabilityProfile));
        SerializerHtmlUtility.process(model, "tip.ftlh", writer);
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
