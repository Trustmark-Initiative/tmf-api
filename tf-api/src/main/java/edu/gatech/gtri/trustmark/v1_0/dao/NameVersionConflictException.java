package edu.gatech.gtri.trustmark.v1_0.dao;

/**
 * Created by brad on 3/23/17.
 */
public class NameVersionConflictException extends DaoException {


    public NameVersionConflictException(Class type, String name, String version){
        super("Two "+type.getSimpleName()+" objects share a name/version of "+name+"/"+version);
        this.type = type;
        this.name = name;
        this.version = version;
    }


    private Class type;
    private String name;
    private String version;

    public Class getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
