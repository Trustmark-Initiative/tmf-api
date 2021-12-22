package edu.gatech.gtri.trustmark.v1_0.tip.evaluator;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import org.gtri.fj.data.List;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;

public interface TrustmarkDefinitionRequirementEvaluator {

    TrustmarkDefinitionRequirementEvaluation evaluate(
            final String trustInteroperabilityProfileUriString,
            final List<String> trustmarkUriStringList);

    TrustmarkDefinitionRequirementEvaluation evaluate(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionOuter,
            final List<Trustmark> trustmarkList);

    TrustmarkDefinitionRequirementEvaluation evaluate(
            final List<TrustmarkDefinitionRequirement> trustmarkDefinitionRequirementList,
            final List<Trustmark> trustmarkList);
}
