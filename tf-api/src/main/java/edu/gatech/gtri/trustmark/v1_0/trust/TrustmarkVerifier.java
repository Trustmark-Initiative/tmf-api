package edu.gatech.gtri.trustmark.v1_0.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

public interface TrustmarkVerifier {

    Validation<NonEmptyList<TrustmarkVerifierFailure>, Trustmark> verify(
            final Trustmark trustmark);
}
