package edu.gatech.gtri.trustmark.v1_0.impl.util.diff;

import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustInteroperabilityProfileDiffType;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Compares the Issuer and creates differences based on that.
 * <br/><br/>
 * Created by brad on 12/6/16.
 */
public class IssuerCompareTIPDiff extends AbstractTIPDiff {

    private static final Logger log = Logger.getLogger(IssuerCompareTIPDiff.class);

    @Override
    public Collection<TrustInteroperabilityProfileDiffResult> doDiff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2) {
        ArrayList<TrustInteroperabilityProfileDiffResult> results = new ArrayList<>();

        Entity e1 = tip1.getIssuer();
        Entity e2 = tip2.getIssuer();

        checkField(results, e1.getIdentifier().toString(), e2.getIdentifier().toString(), "issuer.identifier", false, TrustInteroperabilityProfileDiffType.FIELD_DOES_NOT_MATCH, DiffSeverity.MAJOR);
        checkField(results, e1.getName(), e2.getName(), "issuer.name", false, TrustInteroperabilityProfileDiffType.FIELD_DOES_NOT_MATCH, DiffSeverity.MINOR);

        String contacts1 = stringify(e1.getContacts());
        String contacts2 = stringify(e2.getContacts());
        checkField(results, contacts1, contacts2, "issuer.contacts", false, TrustInteroperabilityProfileDiffType.FIELD_DOES_NOT_MATCH, DiffSeverity.MINOR);


        return results;
    }


}
