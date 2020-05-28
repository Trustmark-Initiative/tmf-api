package edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json;

import edu.gatech.gtri.trustmark.v1_0.impl.util.AbstractTypeSpecificComponentManager;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiff;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffManager;

/**
 * Created by Nicholas on 09/19/2016.
 */
public class JsonDiffManagerImpl extends AbstractTypeSpecificComponentManager<JsonDiff> implements JsonDiffManager {
    @Override
    public Class<? extends JsonDiff> getComponentType() {
        return JsonDiff.class;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> JsonDiff<? super T> getComponent(Class<T> type) {
        return (JsonDiff<? super T>)this.findComponent(type);
    }
}
