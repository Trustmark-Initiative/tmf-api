package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public final class TermJsonProducer implements JsonProducer<Term, JSONObject> {

    @Override
    public Class<Term> getSupportedType() {
        return Term.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Term term) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", term.getName());
        if (!term.getAbbreviations().isEmpty()) {
            JSONArray abbreviations = new JSONArray();
            for (String abbr : term.getAbbreviations()) {
                abbreviations.put(abbr);
            }
            jsonObject.put("Abbreviations", abbreviations);
        }
        jsonObject.put("Definition", term.getDefinition());
        return jsonObject;
    }
}
