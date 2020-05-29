package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Artifact;

/**
 * Created by brad on 12/7/15.
 */
public class ArtifactImpl implements Artifact, Comparable<Artifact> {

    private String name;
    private String description;

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


    public String toString(){
        return "Artifact["+this.getName()+"]";
    }

    @Override
    public int compareTo(Artifact o) {
        if( o != null )
            return this.getName().compareToIgnoreCase(o.getName());
        return 0;
    }

}
