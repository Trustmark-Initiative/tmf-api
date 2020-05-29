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
    public String getName();

    /**
     * Returns a short description about what this serializer does, ie "Writes JSON data".  Note that it will probably
     * have more detail than the name field, and return specifics such as version numbers, etc.
     */
    public String getDescription();

    /**
     * Returns the type of output format this serializer can write.  For example, application/json or text/xml.
     */
    public String getOutputMimeFormat();



    public void serialize(Trustmark trustmark, Writer writer) throws IOException;
    public void serialize(Trustmark trustmark, OutputStream outputStream) throws IOException;
    public void serialize(Trustmark trustmark, Writer writer, Map model) throws IOException;
    public void serialize(Trustmark trustmark, OutputStream outputStream, Map model) throws IOException;


    public void serialize(TrustmarkStatusReport tsr, Writer writer) throws IOException;
    public void serialize(TrustmarkStatusReport tsr, OutputStream outputStream) throws IOException;
    public void serialize(TrustmarkStatusReport tsr, Writer writer, Map model) throws IOException;
    public void serialize(TrustmarkStatusReport tsr, OutputStream outputStream, Map model) throws IOException;


    public void serialize(TrustmarkDefinition td, Writer writer) throws IOException;
    public void serialize(TrustmarkDefinition td, OutputStream outputStream) throws IOException;
    public void serialize(TrustmarkDefinition td, Writer writer, Map model) throws IOException;
    public void serialize(TrustmarkDefinition td, OutputStream outputStream, Map model) throws IOException;


    public void serialize(TrustInteroperabilityProfile tip, Writer writer) throws IOException;
    public void serialize(TrustInteroperabilityProfile tip, OutputStream outputStream) throws IOException;
    public void serialize(TrustInteroperabilityProfile tip, Writer writer, Map model) throws IOException;
    public void serialize(TrustInteroperabilityProfile tip, OutputStream outputStream, Map model) throws IOException;


    public void serialize(Agreement agreement, Writer writer) throws IOException;
    public void serialize(Agreement agreement, OutputStream outputStream) throws IOException;
    public void serialize(Agreement agreement, Writer writer, Map model) throws IOException;
    public void serialize(Agreement agreement, OutputStream outputStream, Map model) throws IOException;


    public void serialize(AgreementResponsibilityTemplate art, Writer writer) throws IOException;
    public void serialize(AgreementResponsibilityTemplate art, OutputStream outputStream) throws IOException;
    public void serialize(AgreementResponsibilityTemplate art, Writer writer, Map model) throws IOException;
    public void serialize(AgreementResponsibilityTemplate art, OutputStream outputStream, Map model) throws IOException;

}