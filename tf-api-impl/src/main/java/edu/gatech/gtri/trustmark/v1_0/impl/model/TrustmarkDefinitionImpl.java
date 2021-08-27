package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.impl.io.bulk.BulkReadArtifact;
import edu.gatech.gtri.trustmark.v1_0.model.*;

import java.util.*;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkDefinitionImpl implements TrustmarkDefinition, Comparable<TrustmarkDefinition>, BulkReadArtifact {

    private String id;
    private String originalSource;
    private String originalSourceType;
    private TrustmarkDefinition.Metadata metadata;
    private Collection<Term> terms;
    private Collection<Source> sources;
    private String conformanceCriteriaPreface;
    private List<ConformanceCriterion> conformanceCriteria;
    private String assessmentStepPreface;
    private List<AssessmentStep> assessmentSteps;
    private String issuanceCriteria;
    private transient Map<String, Object> transientDataMap;

    @Override
    public String getId() { return id; }

    public void setId(final String id) { this.id = id; }

    @Override
    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

    @Override
    public String getOriginalSourceType() {
        return originalSourceType;
    }

    public void setOriginalSourceType(String originalSourceType) {
        this.originalSourceType = originalSourceType;
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public Collection<Term> getTerms() {
        if( terms == null )
            terms = new HashSet<Term>();
        return terms;
    }

    public void setTerms(Collection<Term> terms) {
        this.terms = terms;
    }

    public void addTerm(Term term){
        this.getTerms().add(term);
    }

    @Override
    public Collection<Source> getSources() {
        if( sources == null )
            sources = new HashSet<Source>();
        return sources;
    }

    public void setSources(Collection<Source> sources) {
        this.sources = sources;
    }

    public void addSource(Source source){
        this.getSources().add(source);
    }

    @Override
    public String getConformanceCriteriaPreface() {
        return conformanceCriteriaPreface;
    }

    public void setConformanceCriteriaPreface(String conformanceCriteriaPreface) {
        this.conformanceCriteriaPreface = conformanceCriteriaPreface;
    }

    @Override
    public List<ConformanceCriterion> getConformanceCriteria() {
        if( conformanceCriteria == null )
            conformanceCriteria = new ArrayList<ConformanceCriterion>();
        return conformanceCriteria;
    }

    public void setConformanceCriteria(List<ConformanceCriterion> conformanceCriteria) {
        this.conformanceCriteria = conformanceCriteria;
    }

    public void addConformanceCriterion(ConformanceCriterion crit){
        this.getConformanceCriteria().add(crit);
    }

    @Override
    public String getAssessmentStepPreface() {
        return assessmentStepPreface;
    }

    public void setAssessmentStepPreface(String assessmentStepPreface) {
        this.assessmentStepPreface = assessmentStepPreface;
    }

    @Override
    public List<AssessmentStep> getAssessmentSteps() {
        if( assessmentSteps == null )
            assessmentSteps = new ArrayList<AssessmentStep>();
        return assessmentSteps;
    }

    public void setAssessmentSteps(List<AssessmentStep> assessmentSteps) {
        this.assessmentSteps = assessmentSteps;
    }

    public void addAssessmentStep(AssessmentStep step){
        this.getAssessmentSteps().add(step);
    }

    @Override
    public String getIssuanceCriteria() {
        return issuanceCriteria;
    }

    public void setIssuanceCriteria(String issuanceCriteria) {
        this.issuanceCriteria = issuanceCriteria;
    }


    public String toString() {
        return String.format("TrustmarkDefinition[%s, v.%s]", this.getMetadata().getName(), this.getMetadata().getVersion());
    }

    @Override
    public int compareTo(TrustmarkDefinition o) {
        if( o != null )
            return o.getMetadata().getName().compareToIgnoreCase(this.getMetadata().getName());
        return 0;
    }

    @Override
    public List<Term> getTermsSorted() {
        List<Term> terms = new ArrayList<Term>();
        terms.addAll(this.getTerms());
        Collections.sort(terms, new Comparator<Term>(){
            @Override
            public int compare(Term o1, Term o2) {
                if( o1 != null && o2 != null ){
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
                return 0;
            }
        });
        return terms;
    }


    @Override
    public Set<TrustmarkDefinitionParameter> getAllParameters(){
        HashSet<TrustmarkDefinitionParameter> params = new HashSet<>();
        for( AssessmentStep step : this.getAssessmentSteps() ){
            if( step.getParameters() != null ){
                params.addAll(step.getParameters());
            }
        }
        return params;
    }
    
    @Override
    public Map<String, Object> getTransientDataMap() {
        if (this.transientDataMap == null) {
            this.transientDataMap = new HashMap<>();
        }
        return this.transientDataMap;
    }

}//end TrustmarkDefinitionImpl
