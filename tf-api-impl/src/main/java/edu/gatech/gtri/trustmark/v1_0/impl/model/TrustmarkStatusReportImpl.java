package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkStatusReportImpl implements TrustmarkStatusReport {

    private URI identifier;
    private String id;
    private URI trustmarkReference;
    private TrustmarkStatusCode status;
    private Date statusDateTime;
    private Set<URI> supersederTrustmarkReferences;
    private String notes;
    private Extension extension;

    @Override
    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public URI getTrustmarkReference() {
        return trustmarkReference;
    }

    public void setTrustmarkReference(URI trustmarkReference) {
        this.trustmarkReference = trustmarkReference;
    }

    @Override
    public TrustmarkStatusCode getStatus() {
        return status;
    }

    public void setStatus(TrustmarkStatusCode status) {
        this.status = status;
    }

    @Override
    public Date getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(Date statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    @Override
    public Set<URI> getSupersederTrustmarkReferences() {
        if (supersederTrustmarkReferences == null)
            supersederTrustmarkReferences = new LinkedHashSet<>();
        return supersederTrustmarkReferences;
    }

    public void setSupersederTrustmarkReferences(Set<URI> supersederTrustmarkReferences) {
        this.supersederTrustmarkReferences = supersederTrustmarkReferences;
    }

    public void addSupersederTrustmarkReference(URI uri) {
        this.getSupersederTrustmarkReferences().add(uri);
    }

    @Override
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}
