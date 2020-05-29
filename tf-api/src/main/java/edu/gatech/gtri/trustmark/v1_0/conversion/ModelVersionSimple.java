package edu.gatech.gtri.trustmark.v1_0.conversion;

import java.util.regex.Pattern;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 10/25/16
 */
public class ModelVersionSimple implements ModelVersion, Comparable<ModelVersion> {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public ModelVersionSimple(Integer major, Integer minor){
        this.major = major;
        this.minor = minor;
    }
    public ModelVersionSimple(String versionString){
        String modifiedVersionString = versionString.replaceAll("^[0-9\\.]", "");
        if( modifiedVersionString.contains(".") ){
            String[] parts = modifiedVersionString.split(Pattern.quote("."));
            this.major = Integer.parseInt(parts[0]);
            this.minor = Integer.parseInt(parts[1]);
            // Note this is intentionally designed to drop any sub numbers, like 1.1.111 - the .111 is ignored.
        }else{
            this.major = Integer.parseInt(modifiedVersionString);
            this.minor = 0;
        }
    }
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private Integer major;
    private Integer minor;
    //==================================================================================================================
    //  INTERFACE METHODS
    //==================================================================================================================
    @Override
    public Integer getMajorVersion() {
        return major;
    }
    @Override
    public Integer getMinorVersion() {
        return minor;
    }
    @Override
    public String getStringRepresentation() {
        return major + "." + minor;
    }


    public boolean equals(Object other){
        if( other != null && other instanceof ModelVersion ){
            return this.getStringRepresentation().equals(((ModelVersion) other).getStringRepresentation());
        }
        return false;
    }

    @Override
    public int compareTo(ModelVersion o) {
        if( this.getMajorVersion().equals(o.getMajorVersion()) ){
            return this.getMinorVersion().compareTo(o.getMinorVersion());
        }else{
            return this.getMajorVersion().compareTo(o.getMajorVersion());
        }
    }


}/* end ModelVersionSimple */