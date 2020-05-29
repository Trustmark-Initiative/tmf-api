package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import org.json.JSONObject;

/**
 * Created by brad on 1/7/16.
 */
public class ArtifactJsonProducer extends AbstractJsonProducer implements JsonProducer {

    @Override
    public Class getSupportedType() {
        return Artifact.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Artifact) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Artifact artifact = (Artifact) instance;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", artifact.getName());
        jsonObject.put("Description", artifact.getDescription());
        return jsonObject;
    }



}
