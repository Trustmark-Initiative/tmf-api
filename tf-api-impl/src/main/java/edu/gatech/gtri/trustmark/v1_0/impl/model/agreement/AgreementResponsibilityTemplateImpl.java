package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nicholas Saney on 2017-06-06.
 */
public class AgreementResponsibilityTemplateImpl implements AgreementResponsibilityTemplate {
    
    ////// Instance Fields //////
    protected String originalSource;
    protected String originalSourceType;
    protected String name;
    protected String category;
    protected String definition;
    protected List<URI> tipIdentifiers;
    
    
    ////// Instance Methods //////
    @Override
    public int compareTo(AgreementResponsibilityTemplate that) {
        if (that == null) { return -1; }
        String thisName = this.getName();
        String thatName = that.getName();
        return thisName.compareToIgnoreCase(thatName);
    }
    
    @Override
    public String getOriginalSource() { return this.originalSource; }
    public void setOriginalSource(String originalSource) { this.originalSource = originalSource; }
    
    @Override
    public String getOriginalSourceType() { return this.originalSourceType; }
    public void setOriginalSourceType(String originalSourceType) { this.originalSourceType = originalSourceType; }
    
    @Override
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }
    
    @Override
    public String getDefinition() { return this.definition; }
    public void setDefinition(String definition) { this.definition = definition; }
    @Override
    public List<URI> getTipIdentifiers() {
        if (this.tipIdentifiers == null) { this.tipIdentifiers = new ArrayList<>(); }
        return this.tipIdentifiers;
    }
    public void setTipIdentifiers(Collection<? extends URI> tipIdentifier) {
        this.getTipIdentifiers().clear();
        this.getTipIdentifiers().addAll(tipIdentifier);
    }
    public void addToTipIdentifiers(URI tipIdentifier) {
        this.getTipIdentifiers().add(tipIdentifier);
    }
}
