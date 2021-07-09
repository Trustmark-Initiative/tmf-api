package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.List;

import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;

public final class TrustmarkUtility {
    private TrustmarkUtility() {
    }

    /**
     * Return true if a trustmark in the trustmark list satisfies the trustmark definition requirement.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmarkList                  the trustmark list
     * @return true if a trustmark in the trustmark list satisfies the trustmark definition requirement
     */
    public static boolean satisfyingTrustmarkExists(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        return satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList).isNotEmpty();
    }

    /**
     * Return the trustmarks in the trustmark list that satisfy the trustmark definition requirement.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmarkList                  the trustmark list
     * @return the trustmarks in the trustmark list that satisfy the trustmark definition requirement
     */
    public static List<Trustmark> satisfyingTrustmarkList(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        // the trustmark satisfies the trustmark definition requirement only if
        //   the trustmark definition requirement -> identifier equals the trustmark -> trustmark definition reference -> identifier
        //   the trustmark provider sat

        return trustmarkList.filter(trustmark ->
                trustmarkDefinitionRequirement.getIdentifier().equals(trustmark.getTrustmarkDefinitionReference().getIdentifier()) &&
                        satisfyingProviderExists(trustmarkDefinitionRequirement, trustmark));
    }

    /**
     * Return true if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmark                      the trustmark
     * @return true if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider.
     */
    public static boolean satisfyingProviderExists(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final Trustmark trustmark) {

        return satisfyingProvider(trustmarkDefinitionRequirement, trustmark).isNotEmpty();
    }

    /**
     * Return
     * <ul>
     *     <li>a list containing the trustmark provider if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider</li>
     *     <li>an empty list if the trustmark definition requirement does specify a provider and the trustmark does not match that provider</li>
     * </ul>
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmark                      the trustmark
     * @return <ul>
     *             <li>a list containing the trustmark provider if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider</li>
     *             <li>an empty list if the trustmark definition requirement does specify a provider and the trustmark does not match that provider</li>
     *         </ul>
     */
    public static List<Entity> satisfyingProvider(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final Trustmark trustmark) {

        final List<Entity> trustmarkDefinitionProviderReferenceList =
                fromNull(trustmarkDefinitionRequirement.getProviderReferences())
                        .map(List::iterableList)
                        .orSome(nil());

        return trustmarkDefinitionProviderReferenceList.isEmpty() ?
                list(trustmark.getProvider()) :
                trustmarkDefinitionProviderReferenceList
                        .filter(entity -> entity.getIdentifier().equals(trustmark.getProvider().getIdentifier()));

    }
}
