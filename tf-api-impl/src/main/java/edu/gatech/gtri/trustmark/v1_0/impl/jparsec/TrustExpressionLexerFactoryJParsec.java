package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenIdentifier;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisLeft;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisRight;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr;
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Token;
import org.jparsec.pattern.Pattern;
import org.jparsec.pattern.Patterns;

import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisLeft.KEYWORD_PARENTHESIS_LEFT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisRight.KEYWORD_PARENTHESIS_RIGHT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr.OPERATOR_OR;

public final class TrustExpressionLexerFactoryJParsec {

    private TrustExpressionLexerFactoryJParsec() {
    }

    private static final Pattern patternBaseChar =
            Patterns.or(
                    Patterns.range('\u0041', '\u005A'),
                    Patterns.range('\u0061', '\u007A'),
                    Patterns.range('\u00C0', '\u00D6'),
                    Patterns.range('\u00D8', '\u00F6'),
                    Patterns.range('\u00F8', '\u00FF'),
                    Patterns.range('\u0100', '\u0131'),
                    Patterns.range('\u0134', '\u013E'),
                    Patterns.range('\u0141', '\u0148'),
                    Patterns.range('\u014A', '\u017E'),
                    Patterns.range('\u0180', '\u01C3'),
                    Patterns.range('\u01CD', '\u01F0'),
                    Patterns.range('\u01F4', '\u01F5'),
                    Patterns.range('\u01FA', '\u0217'),
                    Patterns.range('\u0250', '\u02A8'),
                    Patterns.range('\u02BB', '\u02C1'),
                    Patterns.isChar('\u0386'),
                    Patterns.range('\u0388', '\u038A'),
                    Patterns.isChar('\u038C'),
                    Patterns.range('\u038E', '\u03A1'),
                    Patterns.range('\u03A3', '\u03CE'),
                    Patterns.range('\u03D0', '\u03D6'),
                    Patterns.isChar('\u03DA'),
                    Patterns.isChar('\u03DC'),
                    Patterns.isChar('\u03DE'),
                    Patterns.isChar('\u03E0'),
                    Patterns.range('\u03E2', '\u03F3'),
                    Patterns.range('\u0401', '\u040C'),
                    Patterns.range('\u040E', '\u044F'),
                    Patterns.range('\u0451', '\u045C'),
                    Patterns.range('\u045E', '\u0481'),
                    Patterns.range('\u0490', '\u04C4'),
                    Patterns.range('\u04C7', '\u04C8'),
                    Patterns.range('\u04CB', '\u04CC'),
                    Patterns.range('\u04D0', '\u04EB'),
                    Patterns.range('\u04EE', '\u04F5'),
                    Patterns.range('\u04F8', '\u04F9'),
                    Patterns.range('\u0531', '\u0556'),
                    Patterns.isChar('\u0559'),
                    Patterns.range('\u0561', '\u0586'),
                    Patterns.range('\u05D0', '\u05EA'),
                    Patterns.range('\u05F0', '\u05F2'),
                    Patterns.range('\u0621', '\u063A'),
                    Patterns.range('\u0641', '\u064A'),
                    Patterns.range('\u0671', '\u06B7'),
                    Patterns.range('\u06BA', '\u06BE'),
                    Patterns.range('\u06C0', '\u06CE'),
                    Patterns.range('\u06D0', '\u06D3'),
                    Patterns.isChar('\u06D5'),
                    Patterns.range('\u06E5', '\u06E6'),
                    Patterns.range('\u0905', '\u0939'),
                    Patterns.isChar('\u093D'),
                    Patterns.range('\u0958', '\u0961'),
                    Patterns.range('\u0985', '\u098C'),
                    Patterns.range('\u098F', '\u0990'),
                    Patterns.range('\u0993', '\u09A8'),
                    Patterns.range('\u09AA', '\u09B0'),
                    Patterns.isChar('\u09B2'),
                    Patterns.range('\u09B6', '\u09B9'),
                    Patterns.range('\u09DC', '\u09DD'),
                    Patterns.range('\u09DF', '\u09E1'),
                    Patterns.range('\u09F0', '\u09F1'),
                    Patterns.range('\u0A05', '\u0A0A'),
                    Patterns.range('\u0A0F', '\u0A10'),
                    Patterns.range('\u0A13', '\u0A28'),
                    Patterns.range('\u0A2A', '\u0A30'),
                    Patterns.range('\u0A32', '\u0A33'),
                    Patterns.range('\u0A35', '\u0A36'),
                    Patterns.range('\u0A38', '\u0A39'),
                    Patterns.range('\u0A59', '\u0A5C'),
                    Patterns.isChar('\u0A5E'),
                    Patterns.range('\u0A72', '\u0A74'),
                    Patterns.range('\u0A85', '\u0A8B'),
                    Patterns.isChar('\u0A8D'),
                    Patterns.range('\u0A8F', '\u0A91'),
                    Patterns.range('\u0A93', '\u0AA8'),
                    Patterns.range('\u0AAA', '\u0AB0'),
                    Patterns.range('\u0AB2', '\u0AB3'),
                    Patterns.range('\u0AB5', '\u0AB9'),
                    Patterns.isChar('\u0ABD'),
                    Patterns.isChar('\u0AE0'),
                    Patterns.range('\u0B05', '\u0B0C'),
                    Patterns.range('\u0B0F', '\u0B10'),
                    Patterns.range('\u0B13', '\u0B28'),
                    Patterns.range('\u0B2A', '\u0B30'),
                    Patterns.range('\u0B32', '\u0B33'),
                    Patterns.range('\u0B36', '\u0B39'),
                    Patterns.isChar('\u0B3D'),
                    Patterns.range('\u0B5C', '\u0B5D'),
                    Patterns.range('\u0B5F', '\u0B61'),
                    Patterns.range('\u0B85', '\u0B8A'),
                    Patterns.range('\u0B8E', '\u0B90'),
                    Patterns.range('\u0B92', '\u0B95'),
                    Patterns.range('\u0B99', '\u0B9A'),
                    Patterns.isChar('\u0B9C'),
                    Patterns.range('\u0B9E', '\u0B9F'),
                    Patterns.range('\u0BA3', '\u0BA4'),
                    Patterns.range('\u0BA8', '\u0BAA'),
                    Patterns.range('\u0BAE', '\u0BB5'),
                    Patterns.range('\u0BB7', '\u0BB9'),
                    Patterns.range('\u0C05', '\u0C0C'),
                    Patterns.range('\u0C0E', '\u0C10'),
                    Patterns.range('\u0C12', '\u0C28'),
                    Patterns.range('\u0C2A', '\u0C33'),
                    Patterns.range('\u0C35', '\u0C39'),
                    Patterns.range('\u0C60', '\u0C61'),
                    Patterns.range('\u0C85', '\u0C8C'),
                    Patterns.range('\u0C8E', '\u0C90'),
                    Patterns.range('\u0C92', '\u0CA8'),
                    Patterns.range('\u0CAA', '\u0CB3'),
                    Patterns.range('\u0CB5', '\u0CB9'),
                    Patterns.isChar('\u0CDE'),
                    Patterns.range('\u0CE0', '\u0CE1'),
                    Patterns.range('\u0D05', '\u0D0C'),
                    Patterns.range('\u0D0E', '\u0D10'),
                    Patterns.range('\u0D12', '\u0D28'),
                    Patterns.range('\u0D2A', '\u0D39'),
                    Patterns.range('\u0D60', '\u0D61'),
                    Patterns.range('\u0E01', '\u0E2E'),
                    Patterns.isChar('\u0E30'),
                    Patterns.range('\u0E32', '\u0E33'),
                    Patterns.range('\u0E40', '\u0E45'),
                    Patterns.range('\u0E81', '\u0E82'),
                    Patterns.isChar('\u0E84'),
                    Patterns.range('\u0E87', '\u0E88'),
                    Patterns.isChar('\u0E8A'),
                    Patterns.isChar('\u0E8D'),
                    Patterns.range('\u0E94', '\u0E97'),
                    Patterns.range('\u0E99', '\u0E9F'),
                    Patterns.range('\u0EA1', '\u0EA3'),
                    Patterns.isChar('\u0EA5'),
                    Patterns.isChar('\u0EA7'),
                    Patterns.range('\u0EAA', '\u0EAB'),
                    Patterns.range('\u0EAD', '\u0EAE'),
                    Patterns.isChar('\u0EB0'),
                    Patterns.range('\u0EB2', '\u0EB3'),
                    Patterns.isChar('\u0EBD'),
                    Patterns.range('\u0EC0', '\u0EC4'),
                    Patterns.range('\u0F40', '\u0F47'),
                    Patterns.range('\u0F49', '\u0F69'),
                    Patterns.range('\u10A0', '\u10C5'),
                    Patterns.range('\u10D0', '\u10F6'),
                    Patterns.isChar('\u1100'),
                    Patterns.range('\u1102', '\u1103'),
                    Patterns.range('\u1105', '\u1107'),
                    Patterns.isChar('\u1109'),
                    Patterns.range('\u110B', '\u110C'),
                    Patterns.range('\u110E', '\u1112'),
                    Patterns.isChar('\u113C'),
                    Patterns.isChar('\u113E'),
                    Patterns.isChar('\u1140'),
                    Patterns.isChar('\u114C'),
                    Patterns.isChar('\u114E'),
                    Patterns.isChar('\u1150'),
                    Patterns.range('\u1154', '\u1155'),
                    Patterns.isChar('\u1159'),
                    Patterns.range('\u115F', '\u1161'),
                    Patterns.isChar('\u1163'),
                    Patterns.isChar('\u1165'),
                    Patterns.isChar('\u1167'),
                    Patterns.isChar('\u1169'),
                    Patterns.range('\u116D', '\u116E'),
                    Patterns.range('\u1172', '\u1173'),
                    Patterns.isChar('\u1175'),
                    Patterns.isChar('\u119E'),
                    Patterns.isChar('\u11A8'),
                    Patterns.isChar('\u11AB'),
                    Patterns.range('\u11AE', '\u11AF'),
                    Patterns.range('\u11B7', '\u11B8'),
                    Patterns.isChar('\u11BA'),
                    Patterns.range('\u11BC', '\u11C2'),
                    Patterns.isChar('\u11EB'),
                    Patterns.isChar('\u11F0'),
                    Patterns.isChar('\u11F9'),
                    Patterns.range('\u1E00', '\u1E9B'),
                    Patterns.range('\u1EA0', '\u1EF9'),
                    Patterns.range('\u1F00', '\u1F15'),
                    Patterns.range('\u1F18', '\u1F1D'),
                    Patterns.range('\u1F20', '\u1F45'),
                    Patterns.range('\u1F48', '\u1F4D'),
                    Patterns.range('\u1F50', '\u1F57'),
                    Patterns.isChar('\u1F59'),
                    Patterns.isChar('\u1F5B'),
                    Patterns.isChar('\u1F5D'),
                    Patterns.range('\u1F5F', '\u1F7D'),
                    Patterns.range('\u1F80', '\u1FB4'),
                    Patterns.range('\u1FB6', '\u1FBC'),
                    Patterns.isChar('\u1FBE'),
                    Patterns.range('\u1FC2', '\u1FC4'),
                    Patterns.range('\u1FC6', '\u1FCC'),
                    Patterns.range('\u1FD0', '\u1FD3'),
                    Patterns.range('\u1FD6', '\u1FDB'),
                    Patterns.range('\u1FE0', '\u1FEC'),
                    Patterns.range('\u1FF2', '\u1FF4'),
                    Patterns.range('\u1FF6', '\u1FFC'),
                    Patterns.isChar('\u2126'),
                    Patterns.range('\u212A', '\u212B'),
                    Patterns.isChar('\u212E'),
                    Patterns.range('\u2180', '\u2182'),
                    Patterns.range('\u3041', '\u3094'),
                    Patterns.range('\u30A1', '\u30FA'),
                    Patterns.range('\u3105', '\u312C'),
                    Patterns.range('\uAC00', '\uD7A3'));

    private static final Pattern patternIdeographic =
            Patterns.or(
                    Patterns.range('\u4E00', '\u9FA5'),
                    Patterns.isChar('\u3007'),
                    Patterns.range('\u3021', '\u3029'));

    private static final Pattern patternLetter =
            Patterns.or(patternBaseChar, patternIdeographic);

    private static final Pattern patternDigit =
            Patterns.or(
                    Patterns.range('\u0030', '\u0039'),
                    Patterns.range('\u0660', '\u0669'),
                    Patterns.range('\u06F0', '\u06F9'),
                    Patterns.range('\u0966', '\u096F'),
                    Patterns.range('\u09E6', '\u09EF'),
                    Patterns.range('\u0A66', '\u0A6F'),
                    Patterns.range('\u0AE6', '\u0AEF'),
                    Patterns.range('\u0B66', '\u0B6F'),
                    Patterns.range('\u0BE7', '\u0BEF'),
                    Patterns.range('\u0C66', '\u0C6F'),
                    Patterns.range('\u0CE6', '\u0CEF'),
                    Patterns.range('\u0D66', '\u0D6F'),
                    Patterns.range('\u0E50', '\u0E59'),
                    Patterns.range('\u0ED0', '\u0ED9'),
                    Patterns.range('\u0F20', '\u0F29'));

    private static final Pattern patternCombiningChar =
            Patterns.or(
                    Patterns.range('\u0300', '\u0345'),
                    Patterns.range('\u0360', '\u0361'),
                    Patterns.range('\u0483', '\u0486'),
                    Patterns.range('\u0591', '\u05A1'),
                    Patterns.range('\u05A3', '\u05B9'),
                    Patterns.range('\u05BB', '\u05BD'),
                    Patterns.isChar('\u05BF'),
                    Patterns.range('\u05C1', '\u05C2'),
                    Patterns.isChar('\u05C4'),
                    Patterns.range('\u064B', '\u0652'),
                    Patterns.isChar('\u0670'),
                    Patterns.range('\u06D6', '\u06DC'),
                    Patterns.range('\u06DD', '\u06DF'),
                    Patterns.range('\u06E0', '\u06E4'),
                    Patterns.range('\u06E7', '\u06E8'),
                    Patterns.range('\u06EA', '\u06ED'),
                    Patterns.range('\u0901', '\u0903'),
                    Patterns.isChar('\u093C'),
                    Patterns.range('\u093E', '\u094C'),
                    Patterns.isChar('\u094D'),
                    Patterns.range('\u0951', '\u0954'),
                    Patterns.range('\u0962', '\u0963'),
                    Patterns.range('\u0981', '\u0983'),
                    Patterns.isChar('\u09BC'),
                    Patterns.isChar('\u09BE'),
                    Patterns.isChar('\u09BF'),
                    Patterns.range('\u09C0', '\u09C4'),
                    Patterns.range('\u09C7', '\u09C8'),
                    Patterns.range('\u09CB', '\u09CD'),
                    Patterns.isChar('\u09D7'),
                    Patterns.range('\u09E2', '\u09E3'),
                    Patterns.isChar('\u0A02'),
                    Patterns.isChar('\u0A3C'),
                    Patterns.isChar('\u0A3E'),
                    Patterns.isChar('\u0A3F'),
                    Patterns.range('\u0A40', '\u0A42'),
                    Patterns.range('\u0A47', '\u0A48'),
                    Patterns.range('\u0A4B', '\u0A4D'),
                    Patterns.range('\u0A70', '\u0A71'),
                    Patterns.range('\u0A81', '\u0A83'),
                    Patterns.isChar('\u0ABC'),
                    Patterns.range('\u0ABE', '\u0AC5'),
                    Patterns.range('\u0AC7', '\u0AC9'),
                    Patterns.range('\u0ACB', '\u0ACD'),
                    Patterns.range('\u0B01', '\u0B03'),
                    Patterns.isChar('\u0B3C'),
                    Patterns.range('\u0B3E', '\u0B43'),
                    Patterns.range('\u0B47', '\u0B48'),
                    Patterns.range('\u0B4B', '\u0B4D'),
                    Patterns.range('\u0B56', '\u0B57'),
                    Patterns.range('\u0B82', '\u0B83'),
                    Patterns.range('\u0BBE', '\u0BC2'),
                    Patterns.range('\u0BC6', '\u0BC8'),
                    Patterns.range('\u0BCA', '\u0BCD'),
                    Patterns.isChar('\u0BD7'),
                    Patterns.range('\u0C01', '\u0C03'),
                    Patterns.range('\u0C3E', '\u0C44'),
                    Patterns.range('\u0C46', '\u0C48'),
                    Patterns.range('\u0C4A', '\u0C4D'),
                    Patterns.range('\u0C55', '\u0C56'),
                    Patterns.range('\u0C82', '\u0C83'),
                    Patterns.range('\u0CBE', '\u0CC4'),
                    Patterns.range('\u0CC6', '\u0CC8'),
                    Patterns.range('\u0CCA', '\u0CCD'),
                    Patterns.range('\u0CD5', '\u0CD6'),
                    Patterns.range('\u0D02', '\u0D03'),
                    Patterns.range('\u0D3E', '\u0D43'),
                    Patterns.range('\u0D46', '\u0D48'),
                    Patterns.range('\u0D4A', '\u0D4D'),
                    Patterns.isChar('\u0D57'),
                    Patterns.isChar('\u0E31'),
                    Patterns.range('\u0E34', '\u0E3A'),
                    Patterns.range('\u0E47', '\u0E4E'),
                    Patterns.isChar('\u0EB1'),
                    Patterns.range('\u0EB4', '\u0EB9'),
                    Patterns.range('\u0EBB', '\u0EBC'),
                    Patterns.range('\u0EC8', '\u0ECD'),
                    Patterns.range('\u0F18', '\u0F19'),
                    Patterns.isChar('\u0F35'),
                    Patterns.isChar('\u0F37'),
                    Patterns.isChar('\u0F39'),
                    Patterns.isChar('\u0F3E'),
                    Patterns.isChar('\u0F3F'),
                    Patterns.range('\u0F71', '\u0F84'),
                    Patterns.range('\u0F86', '\u0F8B'),
                    Patterns.range('\u0F90', '\u0F95'),
                    Patterns.isChar('\u0F97'),
                    Patterns.range('\u0F99', '\u0FAD'),
                    Patterns.range('\u0FB1', '\u0FB7'),
                    Patterns.isChar('\u0FB9'),
                    Patterns.range('\u20D0', '\u20DC'),
                    Patterns.isChar('\u20E1'),
                    Patterns.range('\u302A', '\u302F'),
                    Patterns.isChar('\u3099'),
                    Patterns.isChar('\u309A'));

    private static final Pattern patternExtender =
            Patterns.or(
                    Patterns.isChar('\u00B7'),
                    Patterns.isChar('\u02D0'),
                    Patterns.isChar('\u02D1'),
                    Patterns.isChar('\u0387'),
                    Patterns.isChar('\u0640'),
                    Patterns.isChar('\u0E46'),
                    Patterns.isChar('\u0EC6'),
                    Patterns.isChar('\u3005'),
                    Patterns.range('\u3031', '\u3035'),
                    Patterns.range('\u309D', '\u309E'),
                    Patterns.range('\u30FC', '\u30FE'));

    private static final Pattern patternNCNameChar =
            Patterns.or(
                    patternLetter,
                    patternDigit,
                    Patterns.isChar('.'),
                    Patterns.isChar('-'),
                    Patterns.isChar('_'),
                    patternCombiningChar,
                    patternExtender);

    // see https://www.w3.org/TR/1999/REC-xml-names-19990114/#NT-NCName

    private static final Pattern patternNCName =
            Patterns.sequence(
                    Patterns.or(
                            patternLetter,
                            Patterns.isChar('_')),
                    patternNCNameChar.many());

    private static final Parser<LexerTokenIdentifier> parserIdentifier =
            patternNCName.toScanner("Scanner for " + LexerTokenIdentifier.class.getSimpleName()).source().map(TrustExpressionLexerToken::identifier);

    private static final Parser<LexerTokenOperatorOr> parserOr =
            Patterns.stringCaseInsensitive(OPERATOR_OR.getValue()).toScanner("Scanner for " + LexerTokenOperatorOr.class.getSimpleName()).source().map(string -> OPERATOR_OR);

    private static final Parser<LexerTokenOperatorAnd> parserAnd =
            Patterns.stringCaseInsensitive(OPERATOR_AND.getValue()).toScanner("Scanner for " + LexerTokenOperatorAnd.class.getSimpleName()).source().map(string -> OPERATOR_AND);

    private static final Parser<LexerTokenOperatorNot> parserNot =
            Patterns.stringCaseInsensitive(OPERATOR_NOT.getValue()).toScanner("Scanner for " + LexerTokenOperatorNot.class.getSimpleName()).source().map(string -> OPERATOR_NOT);

    private static final Parser<LexerTokenKeywordParenthesisLeft> parserParenthesisLeft =
            Patterns.string(KEYWORD_PARENTHESIS_LEFT.getValue()).toScanner("Scanner for " + LexerTokenKeywordParenthesisLeft.class.getSimpleName()).source().map(string -> KEYWORD_PARENTHESIS_LEFT);

    private static final Parser<LexerTokenKeywordParenthesisRight> parserParenthesisRight =
            Patterns.string(KEYWORD_PARENTHESIS_RIGHT.getValue()).toScanner("Scanner for " + LexerTokenKeywordParenthesisRight.class.getSimpleName()).source().map(string -> KEYWORD_PARENTHESIS_RIGHT);

    private static final Parser<Void> parserWhitespace =
            Patterns.isChar(Character::isWhitespace).many().toScanner("Scanner for " + "whitepsace");

    public static Parser<List<Token>> lexer() {
        return Parsers
                .longest(
                        parserOr,
                        parserAnd,
                        parserNot,
                        parserIdentifier,
                        parserParenthesisLeft,
                        parserParenthesisRight)
                .lexer(
                        parserWhitespace);
    }
}
