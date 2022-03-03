package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.impl.io.bulk.BulkReadArtifact;
import edu.gatech.gtri.trustmark.v1_0.model.AbstractTIPReference;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class TrustInteroperabilityProfileImpl extends TrustmarkFrameworkReferenceImpl implements TrustInteroperabilityProfile, Comparable<TrustInteroperabilityProfile>, BulkReadArtifact {

    private String id;
    private String moniker;
    private Entity issuer;
    private String trustExpression;
    private boolean primary;
    private Collection<AbstractTIPReference> references;
    private transient Map<String, Object> transientDataMap;

    public TrustInteroperabilityProfileImpl() {
        super();
        this.id = null;
        this.moniker = null;
        this.issuer = null;
        this.trustExpression = null;
        this.primary = false;
        this.references = new ArrayList<>();
        this.transientDataMap = new HashMap<>();
    }

    public TrustInteroperabilityProfileImpl(
            final String typeName,
            final URI identifier,
            final String name,
            final Integer number,
            final String version,
            final String description,
            final String originalSource,
            final String originalSourceType,
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
            final Collection<Term> terms,
            final String id,
            final String moniker,
            final Entity issuer,
            final String trustExpression,
            final boolean primary,
            final Collection<AbstractTIPReference> references,
            final Map<String, Object> transientDataMap) {

        super(typeName, identifier, name, number, version, description, originalSource, originalSourceType, publicationDateTime, legalNotice, notes, deprecated, keywords, sources, supersedes, supersededBy, satisfies, knownConflicts, terms);
        this.id = id;
        this.moniker = moniker;
        this.issuer = issuer;
        this.trustExpression = trustExpression;
        this.primary = primary;
        this.references = references;
        this.transientDataMap = transientDataMap;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(final String moniker) {
        this.moniker = moniker;
    }

    @Override
    public Entity getIssuer() {
        return issuer;
    }

    public void setIssuer(final Entity issuer) {
        this.issuer = issuer;
    }

    @Override
    public String getTrustExpression() {
        return trustExpression;
    }

    public void setTrustExpression(final String trustExpression) {
        this.trustExpression = trustExpression;
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(final boolean primary) {
        this.primary = primary;
    }

    @Override
    public Collection<AbstractTIPReference> getReferences() {
        return references;
    }

    public void setReferences(final Collection<AbstractTIPReference> references) {
        this.references = references;
    }

    public void addReference(final AbstractTIPReference abstractTIPReference) {
        references.add(abstractTIPReference);
    }

    public Map<String, Object> getTransientDataMap() {
        return transientDataMap;
    }

    @Override
    public int compareTo(final TrustInteroperabilityProfile trustInteroperabilityProfile) {

        requireNonNull(trustInteroperabilityProfile);

        return this.getName().compareTo(trustInteroperabilityProfile.getName()) != 0 ? this.getName().compareTo(trustInteroperabilityProfile.getName()) : this.getIdentifier().compareTo(trustInteroperabilityProfile.getIdentifier());
    }
}
