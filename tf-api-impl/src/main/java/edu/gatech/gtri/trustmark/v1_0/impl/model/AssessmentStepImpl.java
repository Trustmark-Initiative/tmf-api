package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStep;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionParameter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by brad on 12/7/15.
 */
public class AssessmentStepImpl implements AssessmentStep, Comparable<AssessmentStep> {

    private Integer number;
    private String name;
    private String description;
    private String id;
    private Set<ConformanceCriterion> conformanceCriteria;
    private Set<Artifact> artifacts;
    private Set<TrustmarkDefinitionParameter> parameters;

    @Override
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Set<ConformanceCriterion> getConformanceCriteria() {
        if( conformanceCriteria == null )
            conformanceCriteria = new LinkedHashSet<>();
        return conformanceCriteria;
    }

    public void setConformanceCriteria(Set<ConformanceCriterion> conformanceCriteria) {
        this.conformanceCriteria = conformanceCriteria;
    }

    public void addConformanceCriterion(ConformanceCriterion crit){
        this.getConformanceCriteria().add(crit);
    }

    @Override
    public Set<Artifact> getArtifacts() {
        if( artifacts == null ) {
            artifacts = new LinkedHashSet<Artifact>();
        }
        return artifacts;
    }

    public void setArtifacts(Set<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(Artifact artifact){
        this.getArtifacts().add(artifact);
    }




    public Set<TrustmarkDefinitionParameter> getParameters(){
        if( this.parameters == null )
            this.parameters = new LinkedHashSet<>();
        return this.parameters;
    }
    public void setParameters(Set<TrustmarkDefinitionParameter> parameters){
        this.parameters = parameters;
    }
    public void addParameter(TrustmarkDefinitionParameter param){
        this.getParameters().add(param);
    }




    public String toString() {
        return String.format("AssessmentStep[%d - %s]", this.getNumber(), this.getName());
    }

    @Override
    public int compareTo(AssessmentStep o) {
        if( o != null )
            return this.getNumber().compareTo(o.getNumber());
        return 0;
    }
}
