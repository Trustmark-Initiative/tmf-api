package edu.gatech.gtri.trustmark.v1_0.util;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;

import java.io.File;
import java.util.Collection;

/**
 * Contains generalized utilities for working with TrustmarkDefinitions.  This too random to warrant their own interface.
 * <br/><br/>
 * Created by brad on 4/12/16.
 */
public interface TrustmarkDefinitionUtils {


    /**
     * Returns true if the system believes the file to be a TrustmarkDefinition. Note that it doesn't read the whole file,
     * so this is only a "guess".  The file may not resolve correctly.
     */
    public Boolean isTrustmarkDefinition(File file);

    /**
     * Performs a number of validation checks on a TrustmarkDefinition to ensure that it is valid.  This is not a check
     * on whether or not the serialized version (ie, JSON or XML) is valid, this is to make sure the in memory version
     * is good.  We have other checks on the various serialization or deserialization mechanisms, to ensure they are
     * valid.  If errors or warnings in the TrustmarkDefinition are found, then that information is returned via a Collection
     * of problems (no error is raised).  Note that a non-zero collection may indicate not only errors, but warnings
     * as well [like a compiler].
     * <br/><br/>
     * @param trustmarkDefinition {@link TrustmarkDefinition} to validate.
     */
    public Collection<ValidationResult> validate(TrustmarkDefinition trustmarkDefinition);

    /**
     * Calculates the equality of the two given TDs.  If equal, then an empty collection is returned (indicating there
     * are NO differences).  Otherwise, the differences found are enumerated.
     */
    public Collection<TrustmarkDefinitionDiffResult> diff(TrustmarkDefinition td1, TrustmarkDefinition td2);

    /**
     * Calculates the equality of the given TD against it's remote source.  Once the remote TD is resolved, then diff()
     * is simply called.
     */
    public Collection<TrustmarkDefinitionDiffResult> diffAgainstSource(TrustmarkDefinition td1) throws ResolveException;

    /**
     * Checks whether or not the issuance criteria is satisfied for the given TrustmarkDefinition against the
     * given results.
     */
    public Boolean checkIssuanceCriteria(TrustmarkDefinition td, Collection<AssessmentStepResult> results) throws IssuanceCriteriaEvaluationException;

}/* end TrustmarkDefinitionUtils */