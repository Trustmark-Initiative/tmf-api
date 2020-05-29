package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import freemarker.cache.TemplateLoader;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Map;

/**
 * Created by brad on 12/15/15.
 */
public class SerializerHtmlEditors extends AbstractHtmlSerializer implements Serializer {

    private static final Logger log = Logger.getLogger(AbstractHtmlSerializer.class);

    public static final String TD_EDITOR_TEMPLATE = "td-editor.html";
    public static final String TIP_EDITOR_TEMPLATE = "tip-editor.html";

    public static final String OUTPUT_MIME_FORMAT = "text/html";
    public static final String NAME = "HTML Editor Serializer";
    public static final String DESCRIPTION = "Serializes data into HTML, suitable for display and editing in a browser";

    public SerializerHtmlEditors(){
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
        model.put("TRUSTMARK_DEFINITION_JSON", "\n\n// Inserted by the TF API \nTRUSTMARK_DEFINITION = " + asJsonString(td));

        // Make sure some required things are present.
        if( !model.containsKey("ADDED_JAVASCRIPT") )
            model.put("ADDED_JAVASCRIPT", "");
        if( !model.containsKey("MENU_JSON") )
            model.put("MENU_JSON", "");

        // These two things assert that they aren't trapped in comments at all.
        model.put("ADDED_JAVASCRIPT", "\n\n"+model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n"+model.get("MENU_JSON"));

        executeTemplate(model, TD_EDITOR_TEMPLATE, writer);

    }
    @Override
    public void serialize(TrustInteroperabilityProfile tip, Writer writer, Map model) throws IOException {
        model.put("tip", tip);
        model.put("TIP_JSON", "\n\n// Inserted by the TF API \nTIP = "+asJsonString(tip));

        // Make sure some required things are present.
        if( !model.containsKey("ADDED_JAVASCRIPT") )
            model.put("ADDED_JAVASCRIPT", "");
        if( !model.containsKey("MENU_JSON") )
            model.put("MENU_JSON", "");
        if( !model.containsKey("SEARCH_URL") )
            model.put("SEARCH_URL", "");
        else {
            String url = (String) model.get("SEARCH_URL");
            model.put("SEARCH_URL", "\n\nSEARCH_URL = '" + url + "';");
        }
        if( !model.containsKey("PROVIDER_LIST_JSON") ){
            model.put("PROVIDER_LIST_JSON", "");
        }else{
            model.put("PROVIDER_LIST_JSON", "\n\nPROVIDERS = "+model.get("PROVIDER_LIST_JSON")+";");
        }

        // These two things assert that they aren't trapped in comments at all.
        model.put("ADDED_JAVASCRIPT", "\n\n"+model.get("ADDED_JAVASCRIPT"));
        model.put("MENU_JSON", "\n\n"+model.get("MENU_JSON"));



        executeTemplate(model, TIP_EDITOR_TEMPLATE, writer);
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