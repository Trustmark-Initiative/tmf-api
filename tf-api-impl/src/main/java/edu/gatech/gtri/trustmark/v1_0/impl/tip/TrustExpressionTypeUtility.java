package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionTypeOwner;
import edu.gatech.gtri.trustmark.v1_0.tip.evaluator.TrustExpressionEvaluatorData;
import edu.gatech.gtri.trustmark.v1_0.tip.parser.TrustExpressionParserData;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.product.P;
import org.gtri.fj.product.P2;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionFailure.failureTypeMismatch;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeBoolean.TYPE_BOOLEAN;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDateTimeStamp.TYPE_DATE_TIME_STAMP;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeDecimal.TYPE_DECIMAL;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeString.TYPE_STRING;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionType.TrustExpressionTypeStringList.TYPE_STRING_LIST;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.accumulate;
import static org.gtri.fj.data.Validation.condition;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.data.Validation.success;
import static org.gtri.fj.product.P.p;

public class TrustExpressionTypeUtility {
    private TrustExpressionTypeUtility() {
    }

    public static Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionParserData, TrustExpressionParserData>> mustSatisfyAndForParser(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(
                trustExpressionLeft,
                nel(TYPE_BOOLEAN),
                trustExpressionRight,
                nel(TYPE_BOOLEAN),
                trustInteroperabilityProfileNonEmptyList);
    }

    public static Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionEvaluatorData, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>> mustSatisfyAndForEvaluator(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> trustExpressionEvaluatorDataLeftValidation =
                expressionMustBeSuccess(trustExpressionLeft, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionLeft).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, nel(TYPE_BOOLEAN), trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedLeft));

        final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> trustExpressionEvaluatorDataRightValidation =
                expressionMustBeSuccess(trustExpressionRight, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionRight).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, nel(TYPE_BOOLEAN), trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedRight));

        return trustExpressionEvaluatorDataLeftValidation
                .map(trustExpressionEvaluatorDataLeft -> p(trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRightValidation))
                .f().bind(nelLeft -> trustExpressionEvaluatorDataRightValidation
                        .map(trustExpressionEvaluatorDataRight -> p(trustExpressionEvaluatorDataRight, trustExpressionEvaluatorDataLeftValidation))
                        .f().map(nelRight -> nelLeft.append(nelRight)));
    }

    public static Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionParserData, TrustExpressionParserData>> mustSatisfyOrForParser(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionParserData>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(
                trustExpressionLeft,
                nel(TYPE_BOOLEAN),
                trustExpressionRight,
                nel(TYPE_BOOLEAN),
                trustInteroperabilityProfileNonEmptyList);
    }

    public static Validation<NonEmptyList<TrustExpressionFailure>, P2<TrustExpressionEvaluatorData, Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>>> mustSatisfyOrForEvaluator(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> trustExpressionEvaluatorDataLeftValidation =
                expressionMustBeSuccess(trustExpressionLeft, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionLeft).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, nel(TYPE_BOOLEAN), trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedLeft));

        final Validation<NonEmptyList<TrustExpressionFailure>, TrustExpressionEvaluatorData> trustExpressionEvaluatorDataRightValidation =
                expressionMustBeSuccess(trustExpressionRight, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionRight).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, nel(TYPE_BOOLEAN), trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedRight));

        return trustExpressionEvaluatorDataLeftValidation
                .map(trustExpressionEvaluatorDataLeft -> p(trustExpressionEvaluatorDataLeft, trustExpressionEvaluatorDataRightValidation))
                .f().bind(nelLeft -> trustExpressionEvaluatorDataRightValidation
                        .map(trustExpressionEvaluatorDataRight -> p(trustExpressionEvaluatorDataRight, trustExpressionEvaluatorDataLeftValidation))
                        .f().map(nelRight -> nelLeft.append(nelRight)));
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyLessThan(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatchAndTypeMustBeOrderable(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyLessThanOrEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatchAndTypeMustBeOrderable(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyGreaterThan(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatchAndTypeMustBeOrderable(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyGreaterThanOrEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatchAndTypeMustBeOrderable(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyNotEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatch(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyEqual(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustMatch(trustExpressionLeft, trustExpressionRight, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> mustSatisfyContains(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(
                trustExpressionLeft,
                nel(TYPE_STRING_LIST),
                trustExpressionRight,
                nel(TYPE_STRING),
                trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> mustSatisfyNoop(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccess(trustExpression, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpression);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> mustSatisfyNot(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(trustExpression, TYPE_BOOLEAN, trustInteroperabilityProfileNonEmptyList);
    }

    public static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> mustSatisfyExists(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccess(trustExpression, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpression);

    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> expressionMustBeSuccessAndTypeMustMatchAndTypeMustBeOrderable(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(
                trustExpressionLeft,
                nel(TYPE_BOOLEAN, TYPE_DATE_TIME_STAMP, TYPE_DECIMAL, TYPE_STRING),
                trustExpressionRight,
                nel(TYPE_BOOLEAN, TYPE_DATE_TIME_STAMP, TYPE_DECIMAL, TYPE_STRING),
                trustInteroperabilityProfileNonEmptyList)
                .bind(p -> typeMustMatch(p._1(), p._2(), trustInteroperabilityProfileNonEmptyList));
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> expressionMustBeSuccessAndTypeMustMatch(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return accumulate(
                expressionMustBeSuccess(trustExpressionLeft, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionLeft),
                expressionMustBeSuccess(trustExpressionRight, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionRight),
                P::p)
                .bind(p -> typeMustMatch(p._1(), p._2(), trustInteroperabilityProfileNonEmptyList));
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> expressionMustBeSuccessAndTypeMustBe(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionLeft,
            final NonEmptyList<TrustExpressionType> trustExpressionTypeNonEmptyListLeft,
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpressionRight,
            final NonEmptyList<TrustExpressionType> trustExpressionTypeNonEmptyListRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return accumulate(
                expressionMustBeSuccess(trustExpressionLeft, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionLeft).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, trustExpressionTypeNonEmptyListLeft, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedLeft)),
                expressionMustBeSuccess(trustExpressionRight, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpressionRight).bind(trustExpressionTypeOwner ->
                        typeMustBe(trustExpressionTypeOwner, trustExpressionTypeNonEmptyListRight, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpectedRight)),
                P::p);
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> expressionMustBeSuccessAndTypeMustBe(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final TrustExpressionType trustExpressionType,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccessAndTypeMustBe(trustExpression, nel(trustExpressionType), trustInteroperabilityProfileNonEmptyList);
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> expressionMustBeSuccessAndTypeMustBe(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final NonEmptyList<TrustExpressionType> trustExpressionTypeNonEmptyList,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return expressionMustBeSuccess(trustExpression, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureExpression).bind(trustExpressionTypeOwner ->
                typeMustBe(trustExpressionTypeOwner, trustExpressionTypeNonEmptyList, trustInteroperabilityProfileNonEmptyList, TrustExpressionFailure::failureTypeUnexpected));
    }

    private static <T1> Validation<NonEmptyList<TrustExpressionFailure>, T1> expressionMustBeSuccess(
            final TrustExpression<Validation<NonEmptyList<TrustExpressionFailure>, T1>> trustExpression,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final F2<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionFailure>, TrustExpressionFailure> trustExpressionFailureF) {

        return trustExpression.getData()
                .f().map(nel -> nel(trustExpressionFailureF.f(trustInteroperabilityProfileNonEmptyList, nel)));
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> typeMustBe(
            final T1 trustExpressionTypeOwner,
            final TrustExpressionType trustExpressionType,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, TrustExpressionFailure> trustExpressionFailureF) {

        return typeMustBe(trustExpressionTypeOwner, nel(trustExpressionType), trustInteroperabilityProfileNonEmptyList, trustExpressionFailureF);
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, T1> typeMustBe(
            final T1 trustExpressionTypeOwner,
            final NonEmptyList<TrustExpressionType> trustExpressionTypeNonEmptyList,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList,
            final F3<NonEmptyList<TrustInteroperabilityProfile>, NonEmptyList<TrustExpressionType>, TrustExpressionType, TrustExpressionFailure> trustExpressionFailureF) {

        return condition(
                trustExpressionTypeNonEmptyList.toList().exists(trustExpressionType -> trustExpressionType == trustExpressionTypeOwner.getTrustExpressionType()),
                nel(trustExpressionFailureF.f(trustInteroperabilityProfileNonEmptyList, trustExpressionTypeNonEmptyList, trustExpressionTypeOwner.getTrustExpressionType())),
                trustExpressionTypeOwner);
    }

    private static <T1 extends TrustExpressionTypeOwner> Validation<NonEmptyList<TrustExpressionFailure>, P2<T1, T1>> typeMustMatch(
            final T1 trustExpressionLeft,
            final T1 trustExpressionRight,
            final NonEmptyList<TrustInteroperabilityProfile> trustInteroperabilityProfileNonEmptyList) {

        return trustExpressionLeft.matchType(
                trustExpressionRight,
                () -> success(p(trustExpressionLeft, trustExpressionRight)),
                () -> fail(nel(failureTypeMismatch(trustInteroperabilityProfileNonEmptyList, trustExpressionLeft.getTrustExpressionType(), trustExpressionRight.getTrustExpressionType()))));
    }


}
