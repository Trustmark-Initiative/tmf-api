package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brad on 12/15/15.
 */
public class SerializerHtml extends AbstractHtmlSerializer implements Serializer {

    private static final Logger log = Logger.getLogger(AbstractHtmlSerializer.class);

    public static final String TD_TEMPLATE = "trustmark-definition.ftlh";
    public static final String TIP_TEMPLATE = "tip.ftlh";
    public static final String OUTPUT_MIME_FORMAT = "text/html";
    public static final String NAME = "HTML Serializer";
    public static final String DESCRIPTION = "Serializes data into HTML, suitable for display in a browser";

    public SerializerHtml(){
        super(NAME, DESCRIPTION, OUTPUT_MIME_FORMAT);
    }


    @Override
    public String getName() {
        return NAME;
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    @Override
    public String getOutputMimeFormat() {
        return OUTPUT_MIME_FORMAT;
    }

    @Override
    public void serialize(Trustmark trustmark, Writer writer, Map model) throws IOException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for Trustmark");
    }
    @Override
    public void serialize(TrustmarkStatusReport tsr, Writer writer, Map model) throws IOException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for TrustmarkStatusReport");
    }
    @Override
    public void serialize(TrustmarkDefinition td, Writer writer, Map model) throws IOException {
        model.put("td", td);
        model.put("TRUSTMARK_DEFINITION_JSON", asJsonString(td));
        executeTemplate(model, TD_TEMPLATE, writer);

    }
    @Override
    public void serialize(TrustInteroperabilityProfile tip, Writer writer, Map model) throws IOException {
        model.put("tip", tip);
        model.put("TIP_JSON", asJsonString(tip));
        executeTemplate(model, TIP_TEMPLATE, writer);
    }
    @Override
    public void serialize(Agreement agreement, Writer writer, Map model) throws IOException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for Agreement");
    }
    @Override
    public void serialize(AgreementResponsibilityTemplate art, Writer writer, Map model) throws IOException {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: HTML rendering for AgreementResponsibilityTemplate");
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
    
    
}//end SerializerJson