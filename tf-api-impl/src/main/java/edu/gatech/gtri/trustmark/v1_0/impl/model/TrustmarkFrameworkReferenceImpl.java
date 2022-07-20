package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkReference;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TrustmarkFrameworkReferenceImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkFrameworkReference {

    @Override
    public List<Term> getTermsSorted() {
        List<Term> terms = new ArrayList<>();
        terms.addAll(this.getTerms());
        Collections.sort(terms, (Term o1, Term o2) -> {
            if (o1 != null && o2 != null) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return 0;
        });
        return terms;
    }

    private Date publicationDateTime;
    private String legalNotice;
    private String notes;
    private boolean deprecated;

    private List<String> keywords;
    private Collection<Source> sources;
    private List<TrustmarkFrameworkIdentifiedObject> supersedes;
    private List<TrustmarkFrameworkIdentifiedObject> supersededBy;
    private List<TrustmarkFrameworkIdentifiedObject> satisfies;
    private List<TrustmarkFrameworkIdentifiedObject> knownConflicts;
    private Collection<Term> terms;

    public TrustmarkFrameworkReferenceImpl() {
        super();
        this.publicationDateTime = null;
        this.legalNotice = null;
        this.notes = null;
        this.deprecated = false;
        this.keywords = new ArrayList<>();
        this.sources = new ArrayList<>();
        this.supersedes = new ArrayList<>();
        this.supersededBy = new ArrayList<>();
        this.satisfies = new ArrayList<>();
        this.knownConflicts = new ArrayList<>();
        this.terms = new ArrayList<>();
    }

    public TrustmarkFrameworkReferenceImpl(
            final String typeName,
            final URI identifier,
            final String name,
            final Integer number,
            final String version,
            final String description,
            final Date publicationDateTime,
            final String legalNotice,
            final String notes,
            final boolean deprecated,
            final List<String> keywords,
            final Collection<Source> sources,
            final List<TrustmarkFrameworkIdentifiedObject> supersedes,
            final List<TrustmarkFrameworkIdentifiedObject> supersededBy,
            final List<TrustmarkFrameworkIdentifiedObject> satisfies,
            final List<TrustmarkFrameworkIdentifiedObject> knownConflicts,
            final Collection<Term> terms) {

        super(typeName, identifier, name, number, version, description);
        this.publicationDateTime = publicationDateTime;
        this.legalNotice = legalNotice;
        this.notes = notes;
        this.deprecated = deprecated;
        this.keywords = keywords;
        this.sources = sources;
        this.supersedes = supersedes;
        this.supersededBy = supersededBy;
        this.satisfies = satisfies;
        this.knownConflicts = knownConflicts;
        this.terms = terms;
    }

    @Override
    public Date getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(final Date publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }

    @Override
    public String getLegalNotice() {
        return legalNotice;
    }

    public void setLegalNotice(final String legalNotice) {
        this.legalNotice = legalNotice;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    @Override
    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(final boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }

    public void addToKeywords(final String keyword) {
        if (!keywords.contains(keyword))
            keywords.add(keyword);
    }

    @Override
    public Collection<Source> getSources() {
        return sources;
    }

    public void setSources(final Collection<Source> sources) {
        this.sources = sources;
    }

    public void addSource(final Source source) {
        sources.add(source);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersedes() {
        return supersedes;
    }

    public void setSupersedes(final List<TrustmarkFrameworkIdentifiedObject> supersedes) {
        this.supersedes = supersedes;
    }

    public void addToSupersedes(final TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        if (!supersedes.contains(trustmarkFrameworkIdentifiedObject))
            supersedes.add(trustmarkFrameworkIdentifiedObject);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSupersededBy() {
        return supersededBy;
    }

    public void setSupersededBy(final List<TrustmarkFrameworkIdentifiedObject> supersededBy) {
        this.supersededBy = supersededBy;
    }

    public void addToSupersededBy(final TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        if (!supersededBy.contains(trustmarkFrameworkIdentifiedObject))
            supersededBy.add(trustmarkFrameworkIdentifiedObject);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getSatisfies() {
        return satisfies;
    }

    public void setSatisfies(final List<TrustmarkFrameworkIdentifiedObject> satisfies) {
        this.satisfies = satisfies;
    }

    public void addToSatisfies(final TrustmarkFrameworkIdentifiedObject trustmarkFrameworkIdentifiedObject) {
        if (!satisfies.contains(trustmarkFrameworkIdentifiedObject))
            satisfies.add(trustmarkFrameworkIdentifiedObject);
    }

    @Override
    public List<TrustmarkFrameworkIdentifiedObject> getKnownConflicts() {
        return knownConflicts;
    }

    public void setKnownConflicts(final List<TrustmarkFrameworkIdentifiedObject> knownConflicts) {
        this.knownConflicts = knownConflicts;
    }

    public void addToKnownConflict(final TrustmarkFrameworkIdentifiedObject knownConflict) {
        knownConflicts.add(knownConflict);
    }

    @Override
    public Collection<Term> getTerms() {
        return terms;
    }

    public void setTerms(final Collection<Term> terms) {
        this.terms = terms;
    }

    public void addTerm(final Term term) {
        terms.add(term);
    }
}
