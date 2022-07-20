package edu.gatech.gtri.trustmark.v1_0.issuanceCriteria;

import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.IssuanceCriteriaDataLiteral.IssuanceCriteriaDataLiteralAll;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.IssuanceCriteriaDataLiteral.IssuanceCriteriaDataLiteralNone;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.IssuanceCriteriaDataReference.IssuanceCriteriaDataReferenceIdentifierList;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.IssuanceCriteriaDataReference.IssuanceCriteriaDataReferenceIdentifierRange;
import org.gtri.fj.data.NonEmptyList;
import org.gtri.fj.function.F0;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class IssuanceCriteriaData {

    public abstract <T1> T1 match(
            final F1<IssuanceCriteriaDataLiteral, T1> fIssuanceCriteriaDataLiteral,
            final F1<IssuanceCriteriaDataReference, T1> fIssuanceCriteriaDataReference,
            final F1<IssuanceCriteriaDataNonTerminal, T1> fIssuanceCriteriaDataNonTerminal);

    public abstract static class IssuanceCriteriaDataLiteral extends IssuanceCriteriaData {

        public <T1> T1 match(
                final F1<IssuanceCriteriaDataLiteral, T1> fIssuanceCriteriaDataLiteral,
                final F1<IssuanceCriteriaDataReference, T1> fIssuanceCriteriaDataReference,
                final F1<IssuanceCriteriaDataNonTerminal, T1> fIssuanceCriteriaDataNonTerminal) {

            requireNonNull(fIssuanceCriteriaDataLiteral);
            requireNonNull(fIssuanceCriteriaDataReference);
            requireNonNull(fIssuanceCriteriaDataNonTerminal);

            return fIssuanceCriteriaDataLiteral.f(this);
        }

        public abstract <T1> T1 matchLiteral(
                final F0<T1> fIssuanceCriteriaDataLiteralAll,
                final F0<T1> fIssuanceCriteriaDataLiteralNone);

        public static final class IssuanceCriteriaDataLiteralAll extends IssuanceCriteriaDataLiteral {

            private static final IssuanceCriteriaDataLiteralAll ALL = new IssuanceCriteriaDataLiteralAll();

            private IssuanceCriteriaDataLiteralAll() {
            }

            public <T1> T1 matchLiteral(
                    final F0<T1> fIssuanceCriteriaDataLiteralAll,
                    final F0<T1> fIssuanceCriteriaDataLiteralNone) {

                requireNonNull(fIssuanceCriteriaDataLiteralAll);
                requireNonNull(fIssuanceCriteriaDataLiteralNone);

                return fIssuanceCriteriaDataLiteralAll.f();
            }

            @Override
            public String toString() {
                return "IssuanceCriteriaDataLiteralAll{}";
            }
        }

        public static final class IssuanceCriteriaDataLiteralNone extends IssuanceCriteriaDataLiteral {

            private static final IssuanceCriteriaDataLiteralNone NONE = new IssuanceCriteriaDataLiteralNone();

            private IssuanceCriteriaDataLiteralNone() {
            }

            public <T1> T1 matchLiteral(
                    final F0<T1> fIssuanceCriteriaDataLiteralAll,
                    final F0<T1> fIssuanceCriteriaDataLiteralNone) {

                requireNonNull(fIssuanceCriteriaDataLiteralAll);
                requireNonNull(fIssuanceCriteriaDataLiteralNone);

                return fIssuanceCriteriaDataLiteralNone.f();
            }

            @Override
            public String toString() {
                return "IssuanceCriteriaDataLiteralNone{}";
            }
        }
    }

    public abstract static class IssuanceCriteriaDataReference extends IssuanceCriteriaData {

        public <T1> T1 match(
                final F1<IssuanceCriteriaDataLiteral, T1> fIssuanceCriteriaDataLiteral,
                final F1<IssuanceCriteriaDataReference, T1> fIssuanceCriteriaDataReference,
                final F1<IssuanceCriteriaDataNonTerminal, T1> fIssuanceCriteriaDataNonTerminal) {

            requireNonNull(fIssuanceCriteriaDataLiteral);
            requireNonNull(fIssuanceCriteriaDataReference);
            requireNonNull(fIssuanceCriteriaDataNonTerminal);

            return fIssuanceCriteriaDataReference.f(this);
        }

        public abstract <T1> T1 matchReference(
                F2<String, String, T1> fIssuanceCriteriaDataReferenceIdentifierRange,
                F1<NonEmptyList<String>, T1> fIssuanceCriteriaDataReferenceIdentifierList);

        public static final class IssuanceCriteriaDataReferenceIdentifierRange extends IssuanceCriteriaDataReference {

            private final String identifierFrom;
            private final String identifierTo;

            private IssuanceCriteriaDataReferenceIdentifierRange(
                    final String identifierFrom,
                    final String identifierTo) {

                requireNonNull(identifierFrom);
                requireNonNull(identifierTo);

                this.identifierFrom = identifierFrom;
                this.identifierTo = identifierTo;
            }

            public String getIdentifierFrom() {
                return identifierFrom;
            }

            public String getIdentifierTo() {
                return identifierTo;
            }

            public <T1> T1 matchReference(
                    F2<String, String, T1> fIssuanceCriteriaDataReferenceIdentifierRange,
                    F1<NonEmptyList<String>, T1> fIssuanceCriteriaDataReferenceIdentifierList) {

                requireNonNull(fIssuanceCriteriaDataReferenceIdentifierRange);
                requireNonNull(fIssuanceCriteriaDataReferenceIdentifierList);

                return fIssuanceCriteriaDataReferenceIdentifierRange.f(identifierFrom, identifierTo);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final IssuanceCriteriaDataReferenceIdentifierRange that = (IssuanceCriteriaDataReferenceIdentifierRange) o;
                return identifierFrom.equals(that.identifierFrom) && identifierTo.equals(that.identifierTo);
            }

            @Override
            public int hashCode() {
                return Objects.hash(identifierFrom, identifierTo);
            }

            @Override
            public String toString() {
                return "IssuanceCriteriaDataReferenceIdentifierRange{" +
                        "identifierFrom='" + identifierFrom + '\'' +
                        ", identifierTo='" + identifierTo + '\'' +
                        '}';
            }
        }

        public static final class IssuanceCriteriaDataReferenceIdentifierList extends IssuanceCriteriaDataReference {

            private final NonEmptyList<String> identifierList;

            public IssuanceCriteriaDataReferenceIdentifierList(
                    final NonEmptyList<String> identifierList) {

                requireNonNull(identifierList);

                this.identifierList = identifierList;
            }

            public NonEmptyList<String> getIdentifierList() {
                return identifierList;
            }

            public <T1> T1 matchReference(
                    F2<String, String, T1> fIssuanceCriteriaDataReferenceIdentifierRange,
                    F1<NonEmptyList<String>, T1> fIssuanceCriteriaDataReferenceIdentifierList) {

                requireNonNull(fIssuanceCriteriaDataReferenceIdentifierRange);
                requireNonNull(fIssuanceCriteriaDataReferenceIdentifierList);

                return fIssuanceCriteriaDataReferenceIdentifierList.f(identifierList);
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final IssuanceCriteriaDataReferenceIdentifierList that = (IssuanceCriteriaDataReferenceIdentifierList) o;
                return identifierList.equals(that.identifierList);
            }

            @Override
            public int hashCode() {
                return Objects.hash(identifierList);
            }

            @Override
            public String toString() {
                return "IssuanceCriteriaDataReferenceIdentifierList{" +
                        "identifierList=" + identifierList +
                        '}';
            }
        }
    }

    public static final class IssuanceCriteriaDataNonTerminal extends IssuanceCriteriaData {

        public static final IssuanceCriteriaDataNonTerminal DATA_NON_TERMINAL = new IssuanceCriteriaDataNonTerminal();

        private IssuanceCriteriaDataNonTerminal() {
        }

        public <T1> T1 match(
                final F1<IssuanceCriteriaDataLiteral, T1> fIssuanceCriteriaDataLiteral,
                final F1<IssuanceCriteriaDataReference, T1> fIssuanceCriteriaDataReference,
                final F1<IssuanceCriteriaDataNonTerminal, T1> fIssuanceCriteriaDataNonTerminal) {

            requireNonNull(fIssuanceCriteriaDataLiteral);
            requireNonNull(fIssuanceCriteriaDataReference);
            requireNonNull(fIssuanceCriteriaDataNonTerminal);

            return fIssuanceCriteriaDataNonTerminal.f(this);
        }

        @Override
        public String toString() {
            return "IssuanceCriteriaDataNonTerminal{}";
        }
    }

    public static final IssuanceCriteriaDataLiteralAll dataLiteralAll() {

        return IssuanceCriteriaDataLiteralAll.ALL;
    }

    public static final IssuanceCriteriaDataLiteralNone dataLiteralNone() {

        return IssuanceCriteriaDataLiteralNone.NONE;
    }

    public static final IssuanceCriteriaDataReferenceIdentifierRange dataReferenceIdentifierRange(
            final String identifierFrom,
            final String identifierTo) {

        return new IssuanceCriteriaDataReferenceIdentifierRange(identifierFrom, identifierTo);
    }

    public static final IssuanceCriteriaDataReferenceIdentifierList dataReferenceIdentifierList(
            final NonEmptyList<String> identifierList) {

        return new IssuanceCriteriaDataReferenceIdentifierList(identifierList);
    }

    public static final IssuanceCriteriaDataNonTerminal dataNonTerminal() {

        return IssuanceCriteriaDataNonTerminal.DATA_NON_TERMINAL;
    }
}
