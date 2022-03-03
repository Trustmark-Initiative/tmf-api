package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class TrustmarkImpl extends TrustmarkFrameworkIdentifiedObjectImpl implements Trustmark, Comparable<Trustmark> {

    private String id;
    private String originalSource;
    private String originalSourceType;
    private TrustmarkFrameworkIdentifiedObject trustmarkDefinitionReference;
    private Date issueDateTime;
    private Date expirationDateTime;
    private URL policyURL;
    private URL relyingPartyAgreementURL;
    private URL statusURL;
    private EntityImpl provider;
    private EntityImpl recipient;
    private ExtensionImpl definitionExtension;
    private ExtensionImpl providerExtension;
    private Set<String> exceptionInfo;
    private Set<TrustmarkParameterBinding> parameterBindings;

    public TrustmarkImpl() {
        super();
        this.id = null;
        this.originalSource = null;
        this.originalSourceType = null;
        this.trustmarkDefinitionReference = null;
        this.issueDateTime = null;
        this.expirationDateTime = null;
        this.policyURL = null;
        this.relyingPartyAgreementURL = null;
        this.statusURL = null;
        this.provider = null;
        this.recipient = null;
        this.definitionExtension = null;
        this.providerExtension = null;
        this.exceptionInfo = new HashSet<>();
        this.parameterBindings = new HashSet<>();
    }

    public TrustmarkImpl(
            final String typeName,
            final URI identifier,
            final String name,
            final Integer number,
            final String version,
            final String description,
            final String id,
            final String originalSource,
            final String originalSourceType,
            final TrustmarkFrameworkIdentifiedObject trustmarkDefinitionReference,
            final Date issueDateTime,
            final Date expirationDateTime,
            final URL policyURL,
            final URL relyingPartyAgreementURL,
            final URL statusURL,
            final EntityImpl provider,
            final EntityImpl recipient,
            final ExtensionImpl definitionExtension,
            final ExtensionImpl providerExtension,
            final Set<String> exceptionInfo,
            final Set<TrustmarkParameterBinding> parameterBindings) {
        super(typeName, identifier, name, number, version, description);
        this.id = id;
        this.originalSource = originalSource;
        this.originalSourceType = originalSourceType;
        this.trustmarkDefinitionReference = trustmarkDefinitionReference;
        this.issueDateTime = issueDateTime;
        this.expirationDateTime = expirationDateTime;
        this.policyURL = policyURL;
        this.relyingPartyAgreementURL = relyingPartyAgreementURL;
        this.statusURL = statusURL;
        this.provider = provider;
        this.recipient = recipient;
        this.definitionExtension = definitionExtension;
        this.providerExtension = providerExtension;
        this.exceptionInfo = exceptionInfo;
        this.parameterBindings = parameterBindings;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(final String originalSource) {
        this.originalSource = originalSource;
    }

    @Override
    public String getOriginalSourceType() {
        return originalSourceType;
    }

    public void setOriginalSourceType(final String originalSourceType) {
        this.originalSourceType = originalSourceType;
    }

    @Override
    public TrustmarkFrameworkIdentifiedObject getTrustmarkDefinitionReference() {
        return trustmarkDefinitionReference;
    }

    public void setTrustmarkDefinitionReference(final TrustmarkFrameworkIdentifiedObject trustmarkDefinitionReference) {
        this.trustmarkDefinitionReference = trustmarkDefinitionReference;
    }

    @Override
    public Date getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(final Date issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    @Override
    public Date getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(final Date expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    @Override
    public URL getPolicyURL() {
        return policyURL;
    }

    public void setPolicyURL(final URL policyURL) {
        this.policyURL = policyURL;
    }

    @Override
    public URL getRelyingPartyAgreementURL() {
        return relyingPartyAgreementURL;
    }

    public void setRelyingPartyAgreementURL(final URL relyingPartyAgreementURL) {
        this.relyingPartyAgreementURL = relyingPartyAgreementURL;
    }

    @Override
    public URL getStatusURL() {
        return statusURL;
    }

    public void setStatusURL(final URL statusURL) {
        this.statusURL = statusURL;
    }

    @Override
    public EntityImpl getProvider() {
        return provider;
    }

    public void setProvider(final EntityImpl provider) {
        this.provider = provider;
    }

    @Override
    public EntityImpl getRecipient() {
        return recipient;
    }

    public void setRecipient(final EntityImpl recipient) {
        this.recipient = recipient;
    }

    @Override
    public ExtensionImpl getDefinitionExtension() {
        return definitionExtension;
    }

    public void setDefinitionExtension(final ExtensionImpl definitionExtension) {
        this.definitionExtension = definitionExtension;
    }

    @Override
    public ExtensionImpl getProviderExtension() {
        return providerExtension;
    }

    public void setProviderExtension(final ExtensionImpl providerExtension) {
        this.providerExtension = providerExtension;
    }

    @Override
    public Set<String> getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(final Set<String> exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public void addExceptionInfo(final String exceptionInfoItem) {
        exceptionInfo.add(exceptionInfoItem);
    }

    @Override
    public Set<TrustmarkParameterBinding> getParameterBindings() {
        return this.parameterBindings;
    }

    public void setParameterBindings(final Set<TrustmarkParameterBinding> parameterBindings) {
        this.parameterBindings = parameterBindings;
    }

    public void addParameterBinding(final TrustmarkParameterBinding binding) {
        if (!parameterBindings.contains(binding)) {
            parameterBindings.add(binding);
        }
    }

    public int compareTo(final Trustmark trustmark) {

        requireNonNull(trustmark);

        return this.getIdentifier().compareTo(trustmark.getIdentifier());
    }
}
