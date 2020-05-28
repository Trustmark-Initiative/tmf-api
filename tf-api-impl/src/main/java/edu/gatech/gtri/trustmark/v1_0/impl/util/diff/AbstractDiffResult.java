package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brad on 4/14/16.
 */
public abstract class AbstractDiffResult implements DiffResult {

    protected AbstractDiffResult(){}
    protected AbstractDiffResult(DiffSeverity severity, String location, String description){
        this.severity = severity;
        this.location = location;
        this.description = description;
    }

    private DiffSeverity severity;
    private String location;
    private String description;
    private Map data = new HashMap();

    @Override
    public DiffSeverity getSeverity() {
        return this.severity;
    }
    @Override
    public String getLocation() {
        return this.location;
    }
    @Override
    public String getDescription() {
        return this.description;
    }
    @Override
    public Map getData() {
        return this.data;
    }
    public Object getData(Object key){
        return this.data.get(key);
    }

    public void setLocation(String loc){
        this.location = loc;
    }
    public void setDescription(String desc){
        this.description = desc;
    }
    public void setData(Object key, Object value){
        this.data.put(key, value);
    }
    public void setSeverity(DiffSeverity severity){
        this.severity = severity;
    }

}
