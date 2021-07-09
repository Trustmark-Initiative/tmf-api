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

    public static final class LexerTokenIdentifier extends TrustExpressionLexerToken {

        private LexerTokenIdentifier(final String value) {
            super(value);
        }
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

    public static final class LexerTokenKeywordParenthesisLeft extends TrustExpressionLexerToken {

        public static final LexerTokenKeywordParenthesisLeft KEYWORD_PARENTHESIS_LEFT = new LexerTokenKeywordParenthesisLeft("(");

        private LexerTokenKeywordParenthesisLeft(final String value) {
            super(value);
        }
    }

    public static final class LexerTokenKeywordParenthesisRight extends TrustExpressionLexerToken {

        public static final LexerTokenKeywordParenthesisRight KEYWORD_PARENTHESIS_RIGHT = new LexerTokenKeywordParenthesisRight(")");

        private LexerTokenKeywordParenthesisRight(final String value) {
            super(value);
        }
    }

    public static LexerTokenIdentifier identifier(String value) {
        return new LexerTokenIdentifier(value);
    }
}
