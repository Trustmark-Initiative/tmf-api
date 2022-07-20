package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkResolver;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifierFailure.messageFor;
import static org.gtri.fj.data.List.arrayList;

public class TestTrustmarkVerifierImpl {

    @Disabled
    @Test
    public void test() throws ResolveException, URISyntaxException {

        final TrustmarkStatusReportResolver trustmarkStatusReportResolver = FactoryLoader.getInstance(TrustmarkStatusReportResolver.class);
        final Trustmark trustmarkOuter = FactoryLoader.getInstance(TrustmarkResolver.class).resolve(new URI("https://tat.trustmarkinitiative.org/test-tat/public/trustmarks/AF8AD8E6-93EC-4EA0-AA44-5139A1266C19"));
        final TrustmarkVerifier trustmarkVerifier = FactoryLoader.getInstance(TrustmarkVerifierFactory.class).createVerifier(
                arrayList(new URI("https://tat.trustmarkinitiative.org/")),
                arrayList(new URI("https://www.txdps.state.tx.us/")));

        trustmarkVerifier.verify(trustmarkOuter)
                .f().forEach(nel -> nel.forEach(trustmarkVerificationFailure -> System.out.println(messageFor(trustmarkVerificationFailure))));
    }
}
