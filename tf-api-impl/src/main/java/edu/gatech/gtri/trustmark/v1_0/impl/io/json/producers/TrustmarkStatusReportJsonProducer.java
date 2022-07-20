package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.io.json.JsonUtils;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.JsonProducerUtility.toJson;

/**
 * Created by brad on 1/7/16.
 */
public final class TrustmarkStatusReportJsonProducer implements JsonProducer<TrustmarkStatusReport, JSONObject> {

    @Override
    public Class<TrustmarkStatusReport> getSupportedType() {
        return TrustmarkStatusReport.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(TrustmarkStatusReport tsr) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$TMF_VERSION", FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion());
        jsonObject.put("$Type", TrustmarkStatusReport.class.getSimpleName());

        if (tsr.getId() != null) {
            jsonObject.put("$id", tsr.getId());
        }

        JSONObject refObj = new JSONObject();
        refObj.put("Identifier", tsr.getTrustmarkReference().toString());
        jsonObject.put("TrustmarkReference", refObj);
        jsonObject.put("StatusCode", tsr.getStatus().toString());
        jsonObject.put("StatusDateTime", JsonUtils.toDateTimeString(tsr.getStatusDateTime()));

        if (tsr.getSupersederTrustmarkReferences() != null && !tsr.getSupersederTrustmarkReferences().isEmpty()) {
            JSONArray array = new JSONArray();
            for (URI ref : tsr.getSupersederTrustmarkReferences()) {
                JSONObject ssRefObj = new JSONObject();
                ssRefObj.put("Identifier", ref.toString());
                array.put(ssRefObj);
            }
            jsonObject.put("SupersederTrustmarkReferences", array);
        }

        if (tsr.getNotes() != null) {
            jsonObject.put("Notes", tsr.getNotes());
        }

        if (tsr.getExtension() != null) {
            jsonObject.put("Extensions", toJson(tsr.getExtension()));
        }

        return jsonObject;
    }
}
