package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Responsible for serializing Trustmark Framework objects to external formats.
 * <br/><br/>
 * Created by brad on 12/15/15.
 */
public interface Serializer {

    /**
     * Returns the name of this serializer, which should be a big clue as to what format it writes, ie "JSON Serializer"
     */
    String getName();

    /**
     * Returns a short description about what this serializer does, ie "Writes JSON data".  Note that it will probably
     * have more detail than the name field, and return specifics such as version numbers, etc.
     */
    String getDescription();

    /**
     * Returns the type of output format this serializer can write.  For example, application/json or text/xml.
     */
    String getOutputMimeFormat();

    void serialize(final Trustmark trustmark, final Writer writer) throws IOException;

    void serialize(final Trustmark trustmark, final OutputStream outputStream) throws IOException;

    void serialize(final Trustmark trustmark, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final Trustmark trustmark, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final Writer writer) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkStatusReport trustmarkStatusReport, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final Writer writer) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final OutputStream outputStream) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustmarkDefinition trustmarkDefinition, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final Writer writer) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final OutputStream outputStream) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final TrustInteroperabilityProfile trustInteroperabilityProfile, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final Agreement agreement, final Writer writer) throws IOException;

    void serialize(final Agreement agreement, final OutputStream outputStream) throws IOException;

    void serialize(final Agreement agreement, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final Agreement agreement, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final Writer writer) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final OutputStream outputStream) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final Writer writer, final Map<Object, Object> model) throws IOException;

    void serialize(final AgreementResponsibilityTemplate agreementResponsibilityTemplate, final OutputStream outputStream, final Map<Object, Object> model) throws IOException;
}
