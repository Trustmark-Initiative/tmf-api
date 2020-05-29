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

/**
 * Provides some basic implementations of Serializer methods that all of them can use or benefit from.
 * <br/><br/>
 * @author brad
 * @date 12/9/16
 */
public abstract class AbstractSerializer implements Serializer {

    public AbstractSerializer(String name, String description, String mimeType){
        this.name = name;
        this.description = description;
        this.mimeType = mimeType;
    }

    private String name;
    private String description;
    private String mimeType;

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

    private Map emptyMap() {
        return new HashMap();
    }

    @Override
    public void serialize(Trustmark trustmark, Writer writer) throws IOException {
        serialize(trustmark, writer, emptyMap());
    }

    @Override
    public void serialize(Trustmark trustmark, OutputStream outputStream) throws IOException {
        serialize(trustmark, outputStream, emptyMap());
    }

    @Override
    public void serialize(TrustmarkStatusReport tsr, Writer writer) throws IOException {
        serialize(tsr, writer, emptyMap());
    }

    @Override
    public void serialize(TrustmarkStatusReport tsr, OutputStream outputStream) throws IOException {
        serialize(tsr, outputStream, emptyMap());
    }

    @Override
    public void serialize(TrustmarkDefinition td, Writer writer) throws IOException {
        serialize(td, writer, emptyMap());
    }

    @Override
    public void serialize(TrustmarkDefinition td, OutputStream outputStream) throws IOException {
        serialize(td, outputStream, emptyMap());
    }

    @Override
    public void serialize(TrustInteroperabilityProfile tip, Writer writer) throws IOException {
        serialize(tip, writer, emptyMap());
    }

    @Override
    public void serialize(TrustInteroperabilityProfile tip, OutputStream outputStream) throws IOException {
        serialize(tip, outputStream, emptyMap());
    }

    @Override
    public void serialize(Agreement agreement, Writer writer) throws IOException {
        serialize(agreement, writer, emptyMap());
    }

    @Override
    public void serialize(Agreement agreement, OutputStream outputStream) throws IOException {
        serialize(agreement, outputStream, emptyMap());
    }

    @Override
    public void serialize(AgreementResponsibilityTemplate art, Writer writer) throws IOException {
        serialize(art, writer, emptyMap());
    }

    @Override
    public void serialize(AgreementResponsibilityTemplate art, OutputStream outputStream) throws IOException {
        serialize(art, outputStream, emptyMap());
    }

    @Override
    public void serialize(Trustmark trustmark, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(trustmark, writer, model);
    }
    @Override
    public void serialize(TrustmarkStatusReport tsr, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(tsr, writer, model);
    }
    @Override
    public void serialize(TrustmarkDefinition td, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(td, writer, model);
    }
    @Override
    public void serialize(TrustInteroperabilityProfile tip, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(tip, writer, model);
    }
    @Override
    public void serialize(Agreement agreement, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(agreement, writer, model);
    }
    @Override
    public void serialize(AgreementResponsibilityTemplate art, OutputStream outputStream, Map model) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        serialize(art, writer, model);
    }


}/* end AbstractSerializer */