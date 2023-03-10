package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by brad on 12/7/15.
 */
public class ConformanceCriterionImpl implements ConformanceCriterion, Comparable<ConformanceCriterion> {

    private String id;
    private Integer number;
    private String name;
    private String description;
    private Set<Citation> citations;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public Set<Citation> getCitations() {
        if( citations == null )
            citations = new LinkedHashSet<>();
        return citations;
    }

    public void setCitations(Set<Citation> citations) {
        this.citations = citations;
    }

    public void addCitation(Citation cit){
        this.getCitations().add(cit);
    }

    public String toString() {
        return String.format("ConformanceCriterion[%d - %s]", this.getNumber(), this.getName());
    }

    public int compareTo(ConformanceCriterion other){
        if( other != null )
            return this.getNumber().compareTo(other.getNumber());
        return 0;
    }


}
