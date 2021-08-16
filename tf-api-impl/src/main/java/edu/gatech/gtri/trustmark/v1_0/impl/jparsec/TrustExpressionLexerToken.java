package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import static java.util.Objects.requireNonNull;

public abstract class TrustExpressionLexerToken {

    private final String value;

    private TrustExpressionLexerToken(final String value) {

        requireNonNull(value);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final class LexerTokenOperatorOr extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorOr OPERATOR_OR = new LexerTokenOperatorOr("or");

        private LexerTokenOperatorOr(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorAnd extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorAnd OPERATOR_AND = new LexerTokenOperatorAnd("and");

        private LexerTokenOperatorAnd(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorNot extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorNot OPERATOR_NOT = new LexerTokenOperatorNot("not");

        private LexerTokenOperatorNot(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorEqual extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorEqual OPERATOR_EQUAL = new LexerTokenOperatorEqual("==");

        private LexerTokenOperatorEqual(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorNotEqual extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorNotEqual OPERATOR_NOT_EQUAL = new LexerTokenOperatorNotEqual("!=");

        private LexerTokenOperatorNotEqual(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorGreaterThan extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorGreaterThan OPERATOR_GREATER_THAN = new LexerTokenOperatorGreaterThan(">");

        private LexerTokenOperatorGreaterThan(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorLessThan extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorLessThan OPERATOR_LESS_THAN = new LexerTokenOperatorLessThan("<");

        private LexerTokenOperatorLessThan(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorGreaterThanOrEqual extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorGreaterThanOrEqual OPERATOR_GREATER_THAN_OR_EQUAL = new LexerTokenOperatorGreaterThanOrEqual(">=");

        private LexerTokenOperatorGreaterThanOrEqual(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorLessThanOrEqual extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorLessThanOrEqual OPERATOR_LESS_THAN_OR_EQUAL = new LexerTokenOperatorLessThanOrEqual("<=");

        private LexerTokenOperatorLessThanOrEqual(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorContains extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorContains OPERATOR_CONTAINS = new LexerTokenOperatorContains("contains");

        private LexerTokenOperatorContains(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorExists extends TrustExpressionLexerToken {

        public static final LexerTokenOperatorExists OPERATOR_EXISTS = new LexerTokenOperatorExists("exists");

        private LexerTokenOperatorExists(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorParenthesisLeft extends TrustExpressionLexerToken {

        public static final LexerTokenSeparatorParenthesisLeft SEPARATOR_PARENTHESIS_LEFT = new LexerTokenSeparatorParenthesisLeft("(");

        private LexerTokenSeparatorParenthesisLeft(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorParenthesisRight extends TrustExpressionLexerToken {

        public static final LexerTokenSeparatorParenthesisRight SEPARATOR_PARENTHESIS_RIGHT = new LexerTokenSeparatorParenthesisRight(")");

        private LexerTokenSeparatorParenthesisRight(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorDot extends TrustExpressionLexerToken {

        public static final LexerTokenSeparatorDot SEPARATOR_DOT = new LexerTokenSeparatorDot(".");

        private LexerTokenSeparatorDot(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorComma extends TrustExpressionLexerToken {

        public static final LexerTokenSeparatorComma SEPARATOR_COMMA = new LexerTokenSeparatorComma(",");

        private LexerTokenSeparatorComma(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralBooleanTrue extends TrustExpressionLexerToken {

        public static final LexerTokenLiteralBooleanTrue LITERAL_BOOLEAN_TRUE = new LexerTokenLiteralBooleanTrue("true");

        private LexerTokenLiteralBooleanTrue(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralBooleanFalse extends TrustExpressionLexerToken {

        public static final LexerTokenLiteralBooleanFalse LITERAL_BOOLEAN_FALSE = new LexerTokenLiteralBooleanFalse("false");

        private LexerTokenLiteralBooleanFalse(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenIdentifier extends TrustExpressionLexerToken {

        private LexerTokenIdentifier(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralDateTimeStamp extends TrustExpressionLexerToken {

        private LexerTokenLiteralDateTimeStamp(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralDecimal extends TrustExpressionLexerToken {

        private LexerTokenLiteralDecimal(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralString extends TrustExpressionLexerToken {

        private LexerTokenLiteralString(final String value) {
            super(value);
        }
    }

    public static LexerTokenIdentifier identifier(String value) {
        return new LexerTokenIdentifier(value);
    }

    public static LexerTokenLiteralDateTimeStamp literalDateTimeStamp(String value) {
        return new LexerTokenLiteralDateTimeStamp(value);
    }

    public static LexerTokenLiteralDecimal literalDecimal(String value) {
        return new LexerTokenLiteralDecimal(value);
    }

    public static LexerTokenLiteralString literalString(String value) {
        return new LexerTokenLiteralString(value);
    }
}
