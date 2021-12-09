package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Provides some basic implementations of Serializer methods that all of them can use or benefit from.
 * <br/><br/>
 *
 * @author brad
 * @date 12/9/16
 */
public abstract class AbstractSerializer implements Serializer {

    private final String name;
    private final String description;
    private final String mimeType;

    public AbstractSerializer(
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
            final Trustmark trustmark,
            final Writer writer)
            throws IOException {

        serialize(trustmark, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final OutputStream outputStream)
            throws IOException {

        serialize(trustmark, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final Writer writer)
            throws IOException {

        serialize(trustmarkStatusReport, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final OutputStream outputStream)
            throws IOException {

        serialize(trustmarkStatusReport, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final Writer writer)
            throws IOException {

        serialize(trustmarkDefinition, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final OutputStream outputStream)
            throws IOException {

        serialize(trustmarkDefinition, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer)
            throws IOException {

        serialize(trustInteroperabilityProfile, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final OutputStream outputStream)
            throws IOException {

        serialize(trustInteroperabilityProfile, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final Writer writer)
            throws IOException {

        serialize(agreement, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final OutputStream outputStream)
            throws IOException {

        serialize(agreement, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final Writer writer)
            throws IOException {

        serialize(agreementResponsibilityTemplate, writer, new HashMap<>());
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final OutputStream outputStream)
            throws IOException {

        serialize(agreementResponsibilityTemplate, outputStream, new HashMap<>());
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(trustmark, new OutputStreamWriter(outputStream), model);
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(trustmarkStatusReport, new OutputStreamWriter(outputStream), model);
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(trustmarkDefinition, new OutputStreamWriter(outputStream), model);
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(trustInteroperabilityProfile, new OutputStreamWriter(outputStream), model);
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(agreement, new OutputStreamWriter(outputStream), model);
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        serialize(agreementResponsibilityTemplate, new OutputStreamWriter(outputStream), model);
    }
}
