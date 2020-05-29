package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 5/20/16.
 */
public class TrustmarkDefinitionParameterImpl implements TrustmarkDefinitionParameter, Comparable<TrustmarkDefinitionParameter> {


    private String identifier;
    private String name;
    private String description;
    private ParameterKind parameterKind;
    private List<String> enumValues;
    private Boolean required;

    @Override
    public String getIdentifier() {
        return identifier;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public ParameterKind getParameterKind() {
        return parameterKind;
    }
    @Override
    public List<String> getEnumValues() {
        if( enumValues == null )
            enumValues = new ArrayList<>();
        return enumValues;
    }
    public Boolean getRequired() {
        return required;
    }
    @Override
    public Boolean isRequired(){
        return getRequired();
    }


    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setParameterKind(ParameterKind parameterKind) {
        this.parameterKind = parameterKind;
    }
    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void addEnumValue(String val){
        this.getEnumValues().add(val);
    }


    public String toString(){
        return this.getName();
    }

    public boolean equals(Object other){
        if( other != null && other instanceof TrustmarkDefinitionParameter ){
            return this.getIdentifier().equalsIgnoreCase( ((TrustmarkDefinitionParameter) other).getIdentifier() );
        }
        return false;
    }

    public int hashCode(){
        return this.getIdentifier().hashCode();
    }

    @Override
    public int compareTo(TrustmarkDefinitionParameter o) {
        if( o != null && o.getName() != null )
            return this.getName().compareToIgnoreCase(o.getName());
        return -1;
    }

}
