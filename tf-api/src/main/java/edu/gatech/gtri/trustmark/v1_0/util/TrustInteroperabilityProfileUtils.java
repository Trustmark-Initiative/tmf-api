package edu.gatech.gtri.trustmark.v1_0.util;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Provides high level methods for doing useful utility things with TIPs.
 * <br/><br/>
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
     * <br/><br/>
     * @param uri The Address of a TIP
     * @return a {@link TipTreeNode}
     * @throws RemoteException for any network or resolution errors
     * @throws ResolveException if anything is not a valid trustmark framework object.
     */
    public TipTreeNode buildTipTree(URI uri) throws RemoteException, ResolveException;

    /**
     * Validates a TIP, returning any problems found with it.  Note that this does not validate any serialized form
     * (ie, JSON or XML) but rather focuses on the in-memory object once it is parsed.  Also note that a non-zero
     * result may not be fatal, as warnings can be returned (like a compiler).
     * <br/><br/>
     * @param tip
     * @return
     */
    public Collection<ValidationResult> validate(TrustInteroperabilityProfile tip);


    /**
     * Performs a diff on two TIP objects.
     * <br/><br/>
     * @param tip1
     * @param tip2
     * @return
     */
    public Collection diff(TrustInteroperabilityProfile tip1, TrustInteroperabilityProfile tip2);


    /**
     * Given a trust expression from a TIP as a String value, this method will check it against the ANTLR 4 Grammar
     * defining trust expressions for syntax exceptions.
     * <br/><br/>
     * @param trustExpression
     * @throws TrustExpressionSyntaxException for any errors or issues with the trust expression.
     */
    public void validate(String trustExpression) throws TrustExpressionSyntaxException;


    /**
     * Calls {@link TrustInteroperabilityProfileUtils#validate(String)} first, then checks that all identifiers defined
     * by the trust expression are present in the list of given binding variable names.  If not found, an exception is
     * raised.
     * <br/><br/>
     * @param trustExpression a String representation of the Trust Expression.
     * @param bindingVars a list of strings, corresponding to the defined references inside the TIP being tested.
     * @throws TrustExpressionSyntaxException for any errors or issues with the trust expression.
     * @throws TrustExpressionHasUndeclaredIdException when the trust expression uses a variable that is not defined.
     */
    public void validateWithBindings(String trustExpression, List<String> bindingVars) throws TrustExpressionSyntaxException, TrustExpressionHasUndeclaredIdException;

}/* end TrustInteroperabilityProfileUtils */