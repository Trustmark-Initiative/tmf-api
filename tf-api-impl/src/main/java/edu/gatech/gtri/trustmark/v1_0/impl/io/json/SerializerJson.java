package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlManager;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.HasSource;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.gtri.fj.function.TryEffect2;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static java.lang.String.format;

public class SerializerJson extends AbstractSerializer {

    public static final String APPLICATION_JSON = "application/json";

    public SerializerJson() {
        super(
                "JSON Serializer",
                "Serializes data into JSON, using the unofficial TF v1.0 JSON format.",
                APPLICATION_JSON);
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmark, writer);
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmarkStatusReport, writer);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustmarkDefinition, writer);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(trustInteroperabilityProfile, writer);
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(agreement, writer, (agreementInner, writerInner) -> AbstractDocumentJsonProducer.serialize(Agreement.class, agreementInner, writerInner));
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final Writer writer,
            final Map model)
            throws IOException {

        serializeHelper(agreementResponsibilityTemplate, writer, (agreementResponsibilityTemplateInner, writerInner) -> AbstractDocumentJsonProducer.serialize(AgreementResponsibilityTemplate.class, agreementResponsibilityTemplateInner, writerInner));
    }

    private void serializeHelper(
            final HasSource hasSource,
            final Writer writer)
            throws IOException {

        serializeHelper(hasSource, writer, (hasSourceInner, writerInner) -> {
            JSONObject asJson = (JSONObject) this.serialize(hasSourceInner);
            writerInner.write(asJson.toString(2));
        });
    }

    private void serializeHelper(
            final HasSource hasSource,
            final Writer writer,
            final TryEffect2<HasSource, Writer, IOException> serializer)
            throws IOException {

        if (hasSource.getOriginalSourceType() != null && hasSource.getOriginalSourceType().equalsIgnoreCase(getOutputMimeFormat())) {
            writer.write(hasSource.getOriginalSource());
            writer.flush();
        } else {
            serializer.f(hasSource, writer);
        }
    }

    public Object serialize(
            final Object object) {

        final JsonManager jsonManager = FactoryLoader.getInstance(JsonManager.class);

        if (jsonManager == null) {

            throw new UnsupportedOperationException(format("The system could not find an instance of '%s'; the system could not serialize '%s'.", XmlManager.class.getCanonicalName(), object.getClass().getCanonicalName()));

        } else {

            final JsonProducer jsonProducer = jsonManager.findProducer(object.getClass());

            if (jsonProducer == null) {

                throw new UnsupportedOperationException(format("The system could not find an instance of '%s' for '%s'.", XmlProducer.class.getCanonicalName(), object.getClass().getCanonicalName()));

            } else {

                return jsonProducer.serialize(object);
            }
        }
    }
}
