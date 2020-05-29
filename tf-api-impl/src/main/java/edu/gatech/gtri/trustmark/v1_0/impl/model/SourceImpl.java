package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Source;

/**
 * Created by brad on 12/7/15.
 */
public class SourceImpl implements Source, Comparable<Source> {

    private String identifier;
    private String reference;

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String toString() {
        return String.format("Source[%s]", this.getIdentifier());
    }

    @Override
    public int compareTo(Source o) {
        if( o != null )
            return this.getIdentifier().compareToIgnoreCase(o.getIdentifier());
        return 0;
    }
}
