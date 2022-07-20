package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrustInteroperabilityProfileResolverImpl extends AbstractTest {

    @Test
    public void testFactoryLoaderRead() throws Exception {
        TrustInteroperabilityProfileResolver resolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        assertNotNull(resolver);
    }

    @Test
    public void testResolve() throws ResolveException {
        final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);
        final TrustInteroperabilityProfile trustInteroperabilityProfile1 = trustInteroperabilityProfileResolver.resolve(URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
        final TrustInteroperabilityProfile trustInteroperabilityProfile2 = trustInteroperabilityProfileResolver.resolve(URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/?format=xml"));
        final TrustInteroperabilityProfile trustInteroperabilityProfile3 = trustInteroperabilityProfileResolver.resolve(URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/?format=html"));
        final TrustInteroperabilityProfile trustInteroperabilityProfile4 = trustInteroperabilityProfileResolver.resolve(URI.create("http://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/?format=html"));
        assertEquals(trustInteroperabilityProfile1.getIdentifier(), URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
        assertEquals(trustInteroperabilityProfile2.getIdentifier(), URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
        assertEquals(trustInteroperabilityProfile3.getIdentifier(), URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
        assertEquals(trustInteroperabilityProfile4.getIdentifier(), URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"));
    }

    @Test
    public void testResolve2() {
        final TrustInteroperabilityProfileResolver trustInteroperabilityProfileResolver = FactoryLoader.getInstance(TrustInteroperabilityProfileResolver.class);

        assertTrue(trustInteroperabilityProfileResolver.<Boolean>resolve(
                URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.27/"),
                (a, b) -> true,
                (a, b, c) -> false,
                (a, b, c, d, e) -> false,
                (a, b, c, d, e, f) -> false));

        assertTrue(trustInteroperabilityProfileResolver.<Boolean>resolve(
                URI.create("https://artifacts.trustmarkinitiative.org/lib/tips/fbca-cp-section-1_-introduction/2.28/"),
                (a, b) -> false,
                (a, b, c) -> true,
                (a, b, c, d, e) -> false,
                (a, b, c, d, e, f) -> false));

        assertTrue(trustInteroperabilityProfileResolver.<Boolean>resolve(
                URI.create("https://artifacts.trustmarkinitiative.org/unknown/tips/fbca-cp-section-1_-introduction/2.27/"),
                (a, b) -> false,
                (a, b, c) -> false,
                (a, b, c, d, e) -> true,
                (a, b, c, d, e, f) -> false));

        assertTrue(trustInteroperabilityProfileResolver.<Boolean>resolve(
                URI.create("https://artifacts.trustmarkinitiative.info/lib/tips/fbca-cp-section-1_-introduction/2.27/"),
                (a, b) -> false,
                (a, b, c) -> false,
                (a, b, c, d, e) -> false,
                (a, b, c, d, e, f) -> true));
    }
}
