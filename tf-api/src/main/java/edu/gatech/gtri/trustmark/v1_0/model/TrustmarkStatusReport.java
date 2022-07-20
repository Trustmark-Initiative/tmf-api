package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URI;
import java.util.Date;
import java.util.Set;

/**
 * Provides status information about a Trustmark, updated as needed if the
 * Trustmark’s status changes, e.g., from “active” to “revoked” or “expired”.
 *
 * @author GTRI Trustmark Team
 */
public interface TrustmarkStatusReport extends HasIdentifier {

    /**
     * The element identifier of the encoded Trustmark Status Report. May be
     * null.
     */
    String getId();

    /**
     * The reference to the Trustmark that is the subject of this Trustmark
     * Status Report. Guaranteed to be non-null for validated objects.
     */
    URI getTrustmarkReference();

    /**
     * The current status code of the Trustmark indicated by {@link
     * TrustmarkStatusReport#getTrustmarkReference()}. Guaranteed to be non-null
     * for validated objects.
     */
    TrustmarkStatusCode getStatus();

    /**
     * The date and time at which the Trustmark Provider published this
     * Trustmark Status Report. Guaranteed to be non-null for validated
     * objects.
     */
    Date getStatusDateTime();

    /**
     * A set of references to Trustmarks that supersede the Trustmark indicated
     * by {@link TrustmarkStatusReport#getTrustmarkReference()}. May be null.
     */
    Set<URI> getSupersederTrustmarkReferences();

    /**
     * Contains any of the extension information.
     */
    Extension getExtension();

    /**
     * Additional text content about the status of the Trustmark indicated by
     * {@link TrustmarkStatusReport#getTrustmarkReference()}. May be null.
     */
    String getNotes();
}
