package edu.gatech.gtri.trustmark.v1_0.web.validation;

import org.gtri.fj.Ord;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.HashMap;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.data.Validation;
import org.gtri.fj.function.F1;

import java.util.Collection;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.util.OrdUtility.nullableOrd;
import static edu.gatech.gtri.trustmark.v1_0.web.validation.ValidationMessage.validationMessageMustHavePermission;
import static org.gtri.fj.data.HashMap.arrayHashMap;
import static org.gtri.fj.data.HashMap.iterableHashMap;
import static org.gtri.fj.data.NonEmptyList.nel;
import static org.gtri.fj.data.Validation.fail;
import static org.gtri.fj.product.P.p;

public class ValidationResponseUtility {

    public static <FIELD extends Enum<FIELD>, SUCCESS> ValidationResponse validationResponse(
            final Validation<NonEmptyList<ValidationMessage<FIELD>>, SUCCESS> validation) {

        return Either.reduce(validation.toEither().bimap(
                failure -> new ValidationResponse(
                        validationMap(failure),
                        400),
                success -> new ValidationResponse(
                        success,
                        200)));
    }

    private static <FIELD extends Enum<FIELD>> Map<FIELD, java.util.List<Map<String, Object>>> validationMap(final NonEmptyList<ValidationMessage<FIELD>> validationMessageNonEmptyList) {

        return validationMessageNonEmptyList.toList()
                .groupBy(ValidationMessage::getField, nullableOrd(Ord.<FIELD>comparableOrd()))
                .map(list -> list.map(ValidationResponseUtility::validationMap))
                .map(list -> list.toJavaList())
                .toMutableMap();
    }

    private static <FIELD extends Enum<FIELD>> Map<String, Object> validationMap(final ValidationMessage<FIELD> validationMessage) {

        return validationMessage.<HashMap<String, Object>>match(
                        (field, indexNonEmptyList) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("indexNonEmptyList", indexNonEmptyList)),
                        (field, indexNonEmptyList) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("indexNonEmptyList", indexNonEmptyList)),
                        (field, size) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("size", size)),
                        (field, field1, field2) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("field1", field1.name()), p("field2", field2.name())),
                        (field, lengthMinimumInclusive, length) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("lengthMinimumInclusive", lengthMinimumInclusive), p("length", length)),
                        (field, lengthMaximumInclusive, length) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("lengthMaximumInclusive", lengthMaximumInclusive), p("length", length)),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field, pattern, description) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName()), p("pattern", pattern), p("description", description)),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field) -> arrayHashMap(p("type", validationMessage.getClass().getSimpleName())),
                        (field, validationMessageNonEmptyListIndexed) -> iterableHashMap(validationMessageNonEmptyListIndexed.map(p -> p.swap().map1(Object::toString).map2(ValidationResponseUtility::validationMap).map2(value -> value))))
                .toMap();
    }

}
