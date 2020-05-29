package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustVerificationException;
import edu.gatech.gtri.trustmark.v1_0.trust.TrustmarkVerifier;
import org.apache.log4j.Logger;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkVerifierImpl implements TrustmarkVerifier {

    private static final Logger log = Logger.getLogger(TrustmarkVerifierImpl.class);

    @Override
    public void verifyTrustmarkTrustworthiness(Trustmark trustmark, String context, Boolean verifyStatus, Boolean verifyScope) throws TrustVerificationException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    @Override
    public void verifyTrustmarkTrustworthiness(Trustmark trustmark, Object trustPolicy) throws TrustVerificationException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }


}
