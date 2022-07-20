package edu.gatech.gtri.trustmark.v1_0.model;

import java.net.URL;
import java.util.Date;
import java.util.Set;

/**
 * Implementations represent a Trustmark from the Trustmark Framework Technical
 * Specification.
 *
 * A Trustmark is a machine-readable, cryptographically signed digital artifact
 * that represents a statement of conformance to a well-scoped set of trust
 * interoperability requirements.
 *
 * @author GTRI Trustmark Team
 */
public interface Trustmark extends TrustmarkFrameworkIdentifiedObject {

    /**
     * Returns the value of the tf:id attribute of the document element.
     *
     * @return the value of the tf:id attribute of the document element.
     */
    String getId();

    /**
     * Returns a reference to the Trustmark Definition under which this
     * Trustmark was issued.
     *
     * @return a reference to the Trustmark Definition under which this
     * Trustmark was issued
     */
    TrustmarkFrameworkIdentifiedObject getTrustmarkDefinitionReference();

    /**
     * Returns the date and time at which the Trustmark Provider issued this
     * Trustmark to the Trustmark Recipient.
     *
     * @return the date and time at which the Trustmark Provider issued this
     * Trustmark to the Trustmark Recipient
     */
    Date getIssueDateTime();

    /**
     * Returns the date and time at which this Trustmark expires.
     *
     * @return the date and time at which this Trustmark expires
     */
    Date getExpirationDateTime();

    /**
     * Returns the URL of the Trustmark Policy under which this Trustmark was
     * issued.
     *
     * @return the URL of the Trustmark Policy under which this Trustmark was
     * issued
     */
    URL getPolicyURL();

    /**
     * Returns the URL of the Trustmark Relying Party Agreement under which this
     * Trustmark was issued.
     *
     * @return Returns the URL of the Trustmark Relying Party Agreement under
     * which this Trustmark was issued
     */
    URL getRelyingPartyAgreementURL();

    /**
     * Returns the URL at which the Trustmark Relying Party may query the
     * Trustmark Provider for the current status of this Trustmark.
     *
     * @return the URL at which the Trustmark Relying Party may query the
     * Trustmark Provider for the current status of this Trustmark
     */
    URL getStatusURL();

    /**
     * Returns the handle to the Trustmark Provider that issued this Trustmark.
     *
     * Note that the content of the <Identifier> of this Entity is the Trustmark
     * Provider Identifier as described in Section 5.2.1 of the Trustmark
     * Framework Technical Specification.
     *
     * @return the handle to the Trustmark Provider that issued this Trustmark
     */
    Entity getProvider();

    /**
     * Returns the handle to the the Trustmark Recipient to which and about
     * which this Trustmark was issued.
     *
     * Note that the content of the <Identifier> of this Entity is the Trustmark
     * Recipient Identifier, as described in Section 5.2.4 of the Trustmark
     * Framework Technical Specification.
     *
     * @return the handle to the the Trustmark Recipient to which and about
     * which this Trustmark was issued
     */
    Entity getRecipient();

    /**
     * Returns additional content about this Trustmark, as normatively defined
     * by the Trustmark Definition under which this Trustmark was issued.
     *
     * @return additional content about this Trustmark, as normatively defined
     * by the Trustmark Definition under which this Trustmark was issued
     */
    Extension getDefinitionExtension();

    /**
     * Returns additional content about this Trustmark, provided at the
     * discretion of the Trustmark Provider that issued this Trustmark.
     *
     * @return additional content about this Trustmark, provided at the
     * discretion of the Trustmark Provider that issued this Trustmark
     */
    Extension getProviderExtension();

    /**
     * Returns true if the exception info is non-empty; false otherwise.
     *
     * @return true if the exception info is non-empty; false otherwise
     */
    default boolean hasExceptions() {
        return !this.getExceptionInfo().isEmpty();
    }

    /**
     * Returns the string value which provides information about any exceptions
     * that pertain to this trustmark; non-null.
     *
     * @return the string value which provides information about any exceptions
     * that pertain to this trustmark; non-null
     */
    Set<String> getExceptionInfo();

    /**
     * Returns the set of parameter bindings that apply to this Trustmark;
     * non-null.
     *
     * @return the set of parameter bindings that apply to this Trustmark;
     * non-null
     */
    Set<TrustmarkParameterBinding> getParameterBindings();

    /**
     * Returns the source representation; nullable.
     *
     * @return the source representation; nullable
     */
    String getOriginalSource();

    /**
     * Returns the source type; nullable.
     *
     * @return the source type; nullable
     */
    String getOriginalSourceType();

    /**
     * Returns the individual Trustmark Parameter Binding which apples to the
     * given identifier, or null if it is not found.
     *
     * @return the individual Trustmark Parameter Binding which apples to the
     * given identifier, or null if it is not found
     */
    default TrustmarkParameterBinding getParameterBinding(final String identifier) {

        return getParameterBindings().stream()
                .filter(trustmarkParameterBinding -> trustmarkParameterBinding.getIdentifier().equalsIgnoreCase(identifier))
                .findFirst()
                .orElse(null);
    }
}
