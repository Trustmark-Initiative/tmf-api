package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionSyntaxException;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/6/17
 */
public class TrustExpressionANTLRErrorListener implements ANTLRErrorListener {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(TrustExpressionANTLRErrorListener.class);
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private List<TrustExpressionSyntaxException> syntaxErrors = new ArrayList<>();
    private List<Map> ambiguities = new ArrayList<>();
    private List<Map> attemptingFullContext = new ArrayList<>();
    private List<Map> contextSensitivity = new ArrayList<>();
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public List<TrustExpressionSyntaxException> getSyntaxErrors() {
        return syntaxErrors;
    }

    public List<Map> getAmbiguities() {
        return ambiguities;
    }

    public List<Map> getAttemptingFullContext() {
        return attemptingFullContext;
    }

    public List<Map> getContextSensitivity() {
        return contextSensitivity;
    }

    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    private Map toMap(Object ... objects){
        Map map = new HashMap();
        for( int i = 0; i < objects.length; i += 2 ){
            map.put(objects[i], objects[i+1]);
        }
        return map;
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        log.warn("Syntax Error: "+msg);
        syntaxErrors.add(new TrustExpressionSyntaxException(recognizer, offendingSymbol, line, charPositionInLine, msg, e));
    }

    @Override
    public void reportAmbiguity(Parser recognizer,
                                DFA dfa,
                                int startIndex,
                                int stopIndex,
                                boolean exact,
                                BitSet ambigAlts,
                                ATNConfigSet configs) {
        log.warn("Ambiguity!");
        ambiguities.add(toMap("recognizer", recognizer, "dfa", dfa,
                "startIndex", startIndex, "stopIndex", stopIndex, "exact", exact,
                "ambigAlts", ambigAlts, "configs", configs));
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer,
                                            DFA dfa,
                                            int startIndex,
                                            int stopIndex,
                                            BitSet conflictingAlts,
                                            ATNConfigSet configs) {

        log.warn("AttemptingFullContext!");
        attemptingFullContext.add(toMap("recognizer", recognizer, "dfa", dfa,
                "startIndex", startIndex, "stopIndex", stopIndex,
                "conflictingAlts", conflictingAlts, "configs", configs));

    }

    @Override
    public void reportContextSensitivity(Parser recognizer,
                                         DFA dfa,
                                         int startIndex,
                                         int stopIndex,
                                         int prediction,
                                         ATNConfigSet configs) {

        log.warn("Context Sensitivity!");
        contextSensitivity.add(toMap("recognizer", recognizer, "dfa", dfa,
                "startIndex", startIndex, "stopIndex", stopIndex, "prediction", prediction,
                "configs", configs));
    }
}/* end TrustExpressionANTLRErrorListener */