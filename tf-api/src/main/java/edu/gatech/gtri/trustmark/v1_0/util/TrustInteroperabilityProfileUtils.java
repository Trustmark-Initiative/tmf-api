package edu.gatech.gtri.trustmark.v1_0.util;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;

import java.io.File;
import java.net.URI;
import java.util.Collection;

/**
 * Provides high level methods for doing useful utility things with TIPs.
 *
 * @user brad
 * @date 12/6/16
 */
public interface TrustInteroperabilityProfileUtils {

    /**
     * Returns true if the system believes the file to be a TrustInteroperabilityProfile. Note that it doesn't read the whole file,
     * so this is only a "guess".  The file may not resolve correctly.
     */
    public Boolean isTrustInteroperabilityProfile(File file);

    /**
     * Given a TIP URL, this method will work with the {@link edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader} to
     * complete expand the TIP into a TipTreeNode.  The method is recursively called for all Sub Tips (so caution -
     * you will want to cache the output of this so as to avoid re-calling this every time).
     *
     * @param uri The Address of a TIP
     * @return a {@link TipTreeNode}
     * @throws RemoteException  for any network or resolution errors
     * @throws ResolveException if anything is not a valid trustmark framework object.
     */
    public TipTreeNode buildTipTree(URI uri) throws RemoteException, ResolveException;

    /**
     * Validates a TIP, returning any problems found with it.  Note that this does not validate any serialized form
     * (ie, JSON or XML) but rather focuses on the in-memory object once it is parsed.  Also note that a non-zero
     * result may not be fatal, as warnings can be returned (like a compiler).
     *
     * @param tip
     * @return
     */
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip);

    /**
     * Performs a diff on two TIP objects.
     *
     * @param tip1
     * @param tip2
     * @return
     */
    public Collection diff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2);
}
