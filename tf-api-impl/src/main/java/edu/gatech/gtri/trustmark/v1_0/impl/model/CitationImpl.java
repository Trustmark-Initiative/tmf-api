package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.Source;

/**
 * Created by brad on 12/7/15.
 */
public class CitationImpl implements Citation {

    private Source source;
    private String description;

    @Override
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString() {
        return String.format("Citation[%s, %s]", this.getSource().getIdentifier(), this.getDescription());
    }


}
