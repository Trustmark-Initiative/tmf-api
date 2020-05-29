package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class TermJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Term.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Term) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Term term = (Term) instance;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", term.getName());
        if( !term.getAbbreviations().isEmpty() ){
            JSONArray abbreviations = new JSONArray();
            for( String abbr : term.getAbbreviations() ){
                abbreviations.put(abbr);
            }
            jsonObject.put("Abbreviations", abbreviations);
        }
        jsonObject.put("Definition", term.getDefinition());
        return jsonObject;
    }
}
