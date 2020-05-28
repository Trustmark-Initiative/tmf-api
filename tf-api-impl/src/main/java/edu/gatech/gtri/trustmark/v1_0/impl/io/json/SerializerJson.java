package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.AbstractSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonManager;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by brad on 12/15/15.
 */
public class SerializerJson extends AbstractSerializer {

    public static String NAME = "JSON Serializer";
    public static String DESCRIPTION = "Serializes data into JSON, using the unofficial TF v1.0 JSON format.";
    public static String OUTPUT_MIME_FORMAT = "application/json";

    public SerializerJson(){
        super(NAME, DESCRIPTION, OUTPUT_MIME_FORMAT);
    }


    @Override
    public void serialize(Trustmark trustmark, Writer writer, Map model) throws IOException {
        if( trustmark.getOriginalSourceType() != null && trustmark.getOriginalSourceType().equalsIgnoreCase("application/json") ){
            writer.write(trustmark.getOriginalSource());
            return;
        }

        JSONObject asJson = (JSONObject) this.serialize(trustmark);
        writer.write(asJson.toString(2));
    }
    @Override
    public void serialize(TrustmarkStatusReport tsr, Writer writer, Map model) throws IOException {
        if( tsr.getOriginalSourceType() != null && tsr.getOriginalSourceType().equalsIgnoreCase("application/json") ){
            writer.write(tsr.getOriginalSource());
            return;
        }
        JSONObject asJson = (JSONObject) this.serialize(tsr);
        writer.write(asJson.toString(2));
    }
    @Override
    public void serialize(TrustmarkDefinition td, Writer writer, Map model) throws IOException {
        if( td.getOriginalSourceType() != null && td.getOriginalSourceType().equalsIgnoreCase("application/json") ){
            writer.write(td.getOriginalSource());
            return;
        }
        JSONObject asJson = (JSONObject) this.serialize(td);
        writer.write(asJson.toString(2));
    }
    @Override
    public void serialize(TrustInteroperabilityProfile tip, Writer writer, Map model) throws IOException {
        JSONObject asJson = (JSONObject) this.serialize(tip);
        writer.write(asJson.toString(2));
    }
    @Override
    public void serialize(Agreement agreement, Writer writer, Map model) throws IOException {
        if( agreement.getOriginalSourceType() != null && agreement.getOriginalSourceType().equalsIgnoreCase(OUTPUT_MIME_FORMAT) ){
            writer.write(agreement.getOriginalSource());
            return;
        }
        AbstractDocumentJsonProducer.serialize(Agreement.class, agreement, writer);
    }
    @Override
    public void serialize(AgreementResponsibilityTemplate art, Writer writer, Map model) throws IOException {
        if( art.getOriginalSourceType() != null && art.getOriginalSourceType().equalsIgnoreCase(OUTPUT_MIME_FORMAT) ){
            writer.write(art.getOriginalSource());
            return;
        }
        AbstractDocumentJsonProducer.serialize(AgreementResponsibilityTemplate.class, art, writer);
    }
    
    
    private JsonManager getManager(){
        return FactoryLoader.getInstance(JsonManager.class);
    }

    public Object serialize(Object thing){
        JsonManager manager = getManager();
        if( manager == null )
            throw new UnsupportedOperationException("No such class loaded: JsonManager.  Cannot serialize object: "+thing.getClass().getName()+": "+thing);
        JsonProducer producer = manager.findProducer(thing.getClass());
        if( producer == null )
            throw new UnsupportedOperationException("Cannot find JsonProducer for class: "+thing.getClass().getName());
        return producer.serialize(thing);
    }

}//end SerializerJson