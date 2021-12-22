package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by brad on 4/28/16.
 */
public class IssuanceCriteriaErrorListener implements ANTLRErrorListener {

    private static final Logger log = LoggerFactory.getLogger(IssuanceCriteriaErrorListener.class);

    private List<String> syntaxErrors = new ArrayList<>();

    public Boolean hasSyntaxErrors() {
        return !syntaxErrors.isEmpty();
    }
    public List<String> getSyntaxErrors(){return syntaxErrors;}

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        if( offendingSymbol == null )
            offendingSymbol = "<NULL>";
        log.error(String.format("Encountered syntax error: offendingSymbol=[%s], position=[line=%d,column=%d], msg=[%s], error=[%s]", offendingSymbol, line, charPositionInLine, msg, e));
        syntaxErrors.add(msg);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        log.error(String.format("Encountered ambiguity: position=[start=%d,stop=%d], exact=[%s]", startIndex, stopIndex, exact));

    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        log.error(String.format("reportAttemptingFullContext: position=[start=%d,stop=%d], exact=[%s]", startIndex, stopIndex));
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        log.error(String.format("reportContextSensitivity: position=[start=%d,stop=%d], exact=[%s]", startIndex, stopIndex));
    }
}
