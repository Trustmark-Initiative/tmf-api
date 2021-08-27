package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.impl.io.bulk.BulkReadArtifact;
import edu.gatech.gtri.trustmark.v1_0.model.*;

import java.util.*;

/**
 * Created by brad on 12/7/15.
 */
public class TrustInteroperabilityProfileImpl extends TrustmarkFrameworkIdentifiedObjectImpl
        implements TrustInteroperabilityProfile, Comparable<TrustInteroperabilityProfile>, BulkReadArtifact {

    private String id;
    private String originalSource;
    private String originalSourceType;
    private Date publicationDateTime;
    private String legalNotice;
    private String notes;
    private String moniker;
    private Entity issuer;
    private Collection<AbstractTIPReference> references;
    private String trustExpression;
    private Boolean deprecated = Boolean.FALSE;
    private Boolean primary = Boolean.FALSE;
    private List<TrustmarkFrameworkIdentifiedObject> supersedes;
    private List<TrustmarkFrameworkIdentifiedObject> supersededBy;
    private List<String> keywords;
    private List<TrustmarkFrameworkIdentifiedObject> satisfies;
    private List<TrustmarkFrameworkIdentifiedObject> knownConflicts;
    private Collection<Term> terms;
    private Collection<Source> sources;
    private transient Map<String, Object> transientDataMap;

    @Override
    public String getId() { return id; }

    public void setId(final String id) { this.id = id; }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getKnownConflicts() {
        if( knownConflicts == null )
            knownConflicts = new ArrayList<>();
        return knownConflicts;
    }

    public void setKnownConflicts(List<TrustmarkFrameworkIdentifiedObject> knownConflicts) {
        this.knownConflicts = knownConflicts;
    }

    public void addToKnownConflict(TrustmarkFrameworkIdentifiedObject tmfi){
        this.getKnownConflicts().add(tmfi);
    }

    @Override
    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

    public Boolean isPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    @Override
    public String getOriginalSourceType() {
        return originalSourceType;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public void setOriginalSourceType(String originalSourceType) {
        this.originalSourceType = originalSourceType;
    }

    @Override
    public Date getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(Date publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }

    @Override
    public String getLegalNotice() {
        return legalNotice;
    }

    public void setLegalNotice(String legalNotice) {
        this.legalNotice = legalNotice;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Entity getIssuer() {
        return issuer;
    }

    public void setIssuer(Entity issuer) {
        this.issuer = issuer;
    }

    @Override
    public Collection<AbstractTIPReference> getReferences() {
        if( references == null )
            references = new ArrayList<AbstractTIPReference>();
        return references;
    }

    public void setReferences(Collection<AbstractTIPReference> references) {
        this.references = references;
    }

    public void addReference(AbstractTIPReference ref){
        this.getReferences().add(ref);
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
    public String getTrustExpression() {
        return trustExpression;
    }

    public void setTrustExpression(String trustExpression) {
        this.trustExpression = trustExpression;
    }

    public int compareTo(TrustInteroperabilityProfile tip){
        if( tip != null )
            return this.getName().compareToIgnoreCase(tip.getName());
        return 0;
    }


    public Boolean isDeprecated(){
        return this.deprecated;
    }
    public Boolean getDeprecated(){
        return this.isDeprecated();
    }

    public void setDeprecated(Boolean deprecated){
        this.deprecated = deprecated;
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersedes() {
        if( supersedes == null )
            supersedes = new ArrayList<>();
        return supersedes;
    }

    public void setSupersedes(List<TrustmarkFrameworkIdentifiedObject> supersedes) {
        this.supersedes = supersedes;
    }
    public void addToSupersedes(TrustmarkFrameworkIdentifiedObject supersedes){
        if( !this.getSupersedes().contains(supersedes) )
            this.getSupersedes().add(supersedes);
    }
    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersededBy() {
        if( supersededBy == null )
            supersededBy = new ArrayList<>();
        return supersededBy;
    }

    public void setSupersededBy(List<TrustmarkFrameworkIdentifiedObject> supersededBy) {
        this.supersededBy = supersededBy;
    }
    public void addToSupersededBy(TrustmarkFrameworkIdentifiedObject supersededBy){
        if( !this.getSupersededBy().contains(supersededBy) )
            this.getSupersededBy().add(supersededBy);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSatisfies() {
        if( satisfies == null )
            satisfies = new ArrayList<>();
        return satisfies;
    }

    public void setSatisfies(List<TrustmarkFrameworkIdentifiedObject> satisfies) {
        this.satisfies = satisfies;
    }
    public void addToSatisfies(TrustmarkFrameworkIdentifiedObject satisfies){
        if( !this.getSatisfies().contains(satisfies) )
            this.getSatisfies().add(satisfies);
    }

    @Override
    public List<String> getKeywords() {
        if( keywords == null )
            keywords = new ArrayList<>();
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public void addToKeywords( String keyword ){
        if( !this.getKeywords().contains(keyword) )
            this.getKeywords().add(keyword);
    }
    
    @Override
    public Map<String, Object> getTransientDataMap() {
        if (this.transientDataMap == null) {
            this.transientDataMap = new HashMap<>();
        }
        return this.transientDataMap;
    }

}
