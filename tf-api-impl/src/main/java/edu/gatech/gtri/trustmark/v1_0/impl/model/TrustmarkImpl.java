package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkImpl implements Trustmark, Comparable<Trustmark>  {

    private String originalSource;
    private String originalSourceType;
    private URI identifier;
    private TrustmarkFrameworkIdentifiedObject trustmarkDefinitionReference;
    private Date issueDateTime;
    private Date expirationDateTime;
    private URL policyURL;
    private URL relyingPartyAgreementURL;
    private URL statusURL;
    private EntityImpl provider;
    private EntityImpl recipient;
    private Set<String> exceptionInfo;
    private ExtensionImpl definitionExtension;
    private ExtensionImpl providerExtension;
    private Set<TrustmarkParameterBinding> parameterBindings;

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
    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public TrustmarkFrameworkIdentifiedObject getTrustmarkDefinitionReference() {
        return trustmarkDefinitionReference;
    }

    public void setTrustmarkDefinitionReference(TrustmarkFrameworkIdentifiedObject trustmarkDefinitionReference) {
        this.trustmarkDefinitionReference = trustmarkDefinitionReference;
    }

    @Override
    public Date getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(Date issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    @Override
    public Date getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Date expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    @Override
    public URL getPolicyURL() {
        return policyURL;
    }

    public void setPolicyURL(URL policyURL) {
        this.policyURL = policyURL;
    }

    @Override
    public URL getRelyingPartyAgreementURL() {
        return relyingPartyAgreementURL;
    }

    public void setRelyingPartyAgreementURL(URL relyingPartyAgreementURL) {
        this.relyingPartyAgreementURL = relyingPartyAgreementURL;
    }

    @Override
    public URL getStatusURL() {
        return statusURL;
    }

    public void setStatusURL(URL statusURL) {
        this.statusURL = statusURL;
    }

    @Override
    public EntityImpl getProvider() {
        return provider;
    }

    public void setProvider(EntityImpl provider) {
        this.provider = provider;
    }

    @Override
    public EntityImpl getRecipient() {
        return recipient;
    }

    public void setRecipient(EntityImpl recipient) {
        this.recipient = recipient;
    }

    @Override
    public ExtensionImpl getDefinitionExtension() {
        return definitionExtension;
    }

    public void setDefinitionExtension(ExtensionImpl definitionExtension) {
        this.definitionExtension = definitionExtension;
    }

    @Override
    public ExtensionImpl getProviderExtension() {
        return providerExtension;
    }

    public void setProviderExtension(ExtensionImpl providerExtension) {
        this.providerExtension = providerExtension;
    }

    @Override
    public Set<String> getExceptionInfo() {
        if( this.exceptionInfo == null )
            this.exceptionInfo = new HashSet<>();
        return exceptionInfo;
    }

    public void setExceptionInfo(Set<String> exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public void addExceptionInfo(String exceptionInfo){
        this.getExceptionInfo().add(exceptionInfo);
    }

    @Override
    public Boolean hasExceptions(){
        return !this.getExceptionInfo().isEmpty();
    }

    @Override
    public Set<TrustmarkParameterBinding> getParameterBindings() {
        if( this.parameterBindings == null )
            this.parameterBindings = new HashSet<>();
        return this.parameterBindings;
    }

    public void setParameterBindings(Set<TrustmarkParameterBinding> parameterBindings) {
        this.parameterBindings = parameterBindings;
    }

    public void addParameterBinding(TrustmarkParameterBinding binding){
        if( !this.getParameterBindings().contains(binding) ){
            this.getParameterBindings().add(binding);
        }
    }

    @Override
    public TrustmarkParameterBinding getParameterBinding(String identifier) {
        TrustmarkParameterBinding binding = null;
        if( !this.getParameterBindings().isEmpty() ){
            for( TrustmarkParameterBinding cur : this.getParameterBindings() ){
                if( cur.getIdentifier().equalsIgnoreCase(identifier) ){
                    binding = cur;
                    break;
                }
            }
        }
        return binding;
    }

    public String toString(){
        return String.format("Trustmark[%s]", this.getIdentifier().toString());
    }

    public int compareTo(Trustmark that){
        if( that != null ){
            return this.getIdentifier().compareTo(that.getIdentifier());
        }
        return 0;
    }

}
