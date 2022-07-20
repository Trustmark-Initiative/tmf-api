package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Set;

import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.failureNonRevocationStatus;

public class TestTrustmarkVerifierFailure {

    @Test
    public void testMessageFor() {

        TrustmarkVerifierFailure.messageFor(failureNonRevocationStatus(new Trustmark() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getTypeName() {
                return null;
            }

            @Override
            public URI getIdentifier() {
                return URI.create("trustmark");
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Integer getNumber() {
                return null;
            }

            @Override
            public String getVersion() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public TrustmarkFrameworkIdentifiedObject getTrustmarkDefinitionReference() {
                return null;
            }

            @Override
            public Date getIssueDateTime() {
                return null;
            }

            @Override
            public Date getExpirationDateTime() {
                return null;
            }

            @Override
            public URL getPolicyURL() {
                return null;
            }

            @Override
            public URL getRelyingPartyAgreementURL() {
                return null;
            }

            @Override
            public URL getStatusURL() {
                try {
                    return new URL("https://trustmark-status-report-url");
                } catch (final MalformedURLException malformedURLException) {
                    throw new RuntimeException(malformedURLException);
                }
            }

            @Override
            public Entity getProvider() {
                return null;
            }

            @Override
            public Entity getRecipient() {
                return null;
            }

            @Override
            public Extension getDefinitionExtension() {
                return null;
            }

            @Override
            public Extension getProviderExtension() {
                return null;
            }

            @Override
            public boolean hasExceptions() {
                return false;
            }

            @Override
            public Set<String> getExceptionInfo() {
                return null;
            }

            @Override
            public Set<TrustmarkParameterBinding> getParameterBindings() {
                return null;
            }

            @Override
            public TrustmarkParameterBinding getParameterBinding(final String identifier) {
                return null;
            }

            @Override
            public String getOriginalSource() {
                return null;
            }

            @Override
            public String getOriginalSourceType() {
                return null;
            }
        }, new TrustmarkStatusReport() {
            @Override
            public URI getIdentifier() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public URI getTrustmarkReference() {
                return null;
            }

            @Override
            public TrustmarkStatusCode getStatus() {
                return TrustmarkStatusCode.REVOKED;
            }

            @Override
            public Date getStatusDateTime() {
                return null;
            }

            @Override
            public Set<URI> getSupersederTrustmarkReferences() {
                return null;
            }

            @Override
            public Extension getExtension() {
                return null;
            }

            @Override
            public String getNotes() {
                return null;
            }
        }));
    }
}
