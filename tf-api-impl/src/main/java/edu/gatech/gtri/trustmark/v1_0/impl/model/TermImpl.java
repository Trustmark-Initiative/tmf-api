package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Term;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by brad on 12/7/15.
 */
public class TermImpl implements Term, Comparable<Term> {

    private String name;
    private Set<String> abbreviations;
    private String definition;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getAbbreviations() {
        if( abbreviations == null )
            abbreviations = new LinkedHashSet<>();
        return abbreviations;
    }

    public void setAbbreviations(Set<String> abbreviations) {
        this.abbreviations = abbreviations;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


    public void addAbbreviation(String abbr){
        this.getAbbreviations().add(abbr);
    }


    public String toString(){
        return String.format("Term[%s]", this.getName());
    }

    @Override
    public int compareTo(Term o) {
        if( o != null )
            return this.getName().compareToIgnoreCase(o.getName());
        return 0;
    }
}
