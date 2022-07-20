package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import static java.util.Objects.requireNonNull;

public class IssuanceCriteriaLexerToken {

    private final String value;

    private IssuanceCriteriaLexerToken(final String value) {

        requireNonNull(value);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final class LexerTokenOperatorOr extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorOr OPERATOR_OR = new LexerTokenOperatorOr("or");

        private LexerTokenOperatorOr(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorAnd extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorAnd OPERATOR_AND = new LexerTokenOperatorAnd("and");

        private LexerTokenOperatorAnd(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorNot extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorNot OPERATOR_NOT = new LexerTokenOperatorNot("not");

        private LexerTokenOperatorNot(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorYes extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorYes OPERATOR_YES = new LexerTokenOperatorYes("yes");

        private LexerTokenOperatorYes(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorNo extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorNo OPERATOR_NO = new LexerTokenOperatorNo("no");

        private LexerTokenOperatorNo(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenOperatorNa extends IssuanceCriteriaLexerToken {

        public static final LexerTokenOperatorNa OPERATOR_NA = new LexerTokenOperatorNa("na");

        private LexerTokenOperatorNa(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorParenthesisLeft extends IssuanceCriteriaLexerToken {

        public static final LexerTokenSeparatorParenthesisLeft SEPARATOR_PARENTHESIS_LEFT = new LexerTokenSeparatorParenthesisLeft("(");

        private LexerTokenSeparatorParenthesisLeft(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorParenthesisRight extends IssuanceCriteriaLexerToken {

        public static final LexerTokenSeparatorParenthesisRight SEPARATOR_PARENTHESIS_RIGHT = new LexerTokenSeparatorParenthesisRight(")");

        private LexerTokenSeparatorParenthesisRight(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorEllipsis extends IssuanceCriteriaLexerToken {

        public static final LexerTokenSeparatorEllipsis SEPARATOR_ELLIPSIS = new LexerTokenSeparatorEllipsis("...");

        private LexerTokenSeparatorEllipsis(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenSeparatorComma extends IssuanceCriteriaLexerToken {

        public static final LexerTokenSeparatorComma SEPARATOR_COMMA = new LexerTokenSeparatorComma(",");

        private LexerTokenSeparatorComma(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralAll extends IssuanceCriteriaLexerToken {

        public static final LexerTokenLiteralAll LITERAL_ALL = new LexerTokenLiteralAll("ALL");

        private LexerTokenLiteralAll(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenLiteralNone extends IssuanceCriteriaLexerToken {

        public static final LexerTokenLiteralNone LITERAL_NONE = new LexerTokenLiteralNone("NONE");

        private LexerTokenLiteralNone(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenIdentifier extends IssuanceCriteriaLexerToken {

        private LexerTokenIdentifier(final String value) {
            super(value);
        }
    }

    public static LexerTokenIdentifier identifier(String value) {
        return new LexerTokenIdentifier(value);
    }
}
