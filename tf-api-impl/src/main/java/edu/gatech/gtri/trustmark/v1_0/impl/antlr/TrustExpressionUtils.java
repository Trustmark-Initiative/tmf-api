package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionHasUndeclaredIdException;
import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionSyntaxException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/7/17
 */
public class TrustExpressionUtils {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger logger = LogManager.getLogger(TrustExpressionUtils.class);
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    public static void validate(String trustExpression) throws TrustExpressionSyntaxException {
        logger.debug("Testing expression: " + trustExpression);
        TrustExpressionANTLRErrorListener errorListener = new TrustExpressionANTLRErrorListener();
        TrustExpressionParser parser = buildTEParser(trustExpression, null, errorListener);
        parser.trustExpression(); // Causes the parser to start parsing.

        throwFirstError(errorListener);

        logger.info("Valid Trust Expression: "+trustExpression);
    }

    public static void validateWithBindings(String trustExpression, List<String> bindingVars) throws TrustExpressionSyntaxException, TrustExpressionHasUndeclaredIdException {
        logger.debug("Testing expression: " + trustExpression);
        TrustExpressionANTLRErrorListener errorListener = new TrustExpressionANTLRErrorListener();
        TrustExpressionVariableProducer variableProducer = new TrustExpressionVariableProducer();
        TrustExpressionParser parser = buildTEParser(trustExpression, variableProducer, errorListener);
        parser.trustExpression(); // Causes the parser to start parsing.

        throwFirstError(errorListener);

        logger.debug("Validated string, checking bindings...");
        List<String> idsFound = variableProducer.getIdentifiers();
        logger.debug("Found the following identifiers: "+idsFound);

        List<String> inIdsFoundNotInBindingVars = new ArrayList<>();
        for( String idFound : idsFound ){
            if( !bindingVars.contains(idFound) ){
                inIdsFoundNotInBindingVars.add(idFound);
            }
        }

        List<String> inBindingVarsNotInIdsFound = new ArrayList<>();
        for( String bindingVar : bindingVars ){
            if( !idsFound.contains(bindingVar) ){
                inBindingVarsNotInIdsFound.add(bindingVar);
            }
        }

        if( inIdsFoundNotInBindingVars.size() > 0  ){
            logger.error("The following variables are used in the trust expression, but not given in defined vars list: ");
            for(String id : inIdsFoundNotInBindingVars ){
                logger.error("  id=["+id+"]");
            }
            throw new TrustExpressionHasUndeclaredIdException(trustExpression, inIdsFoundNotInBindingVars, bindingVars);
        }


        // TODO Do we even warn about the others?

    }


    private static void throwFirstError(TrustExpressionANTLRErrorListener errorListener ) throws TrustExpressionSyntaxException {
        if( errorListener.getSyntaxErrors().size() > 0 ){
            if( errorListener.getSyntaxErrors().size() > 1 ){
                logger.debug("Suppressing "+(errorListener.getSyntaxErrors().size() - 1) +" syntax errors: ");
                for( int i = 1; i < errorListener.getSyntaxErrors().size(); i++ ){
                    logger.debug("    " + errorListener.getSyntaxErrors().get(i).getMessage());
                }
            }
            throw errorListener.getSyntaxErrors().get(0); // We just throw the first.
        }
    }

    private static TrustExpressionParser buildTEParser( String trustExpression, TrustExpressionListener listener, TrustExpressionANTLRErrorListener errorListener) {
        logger.debug("Testing expression: " + trustExpression);
        ANTLRInputStream input = new ANTLRInputStream(trustExpression);
        TrustExpressionLexer lexer = new TrustExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrustExpressionParser parser = new TrustExpressionParser(tokens);
        if( errorListener != null ) {
            lexer.addErrorListener(errorListener);
            parser.addErrorListener(errorListener);
        }
        if( listener != null ){
            parser.addParseListener(listener);
        }
        return parser;
    }//end buildTEParser



}/* end TrustExpressionUtils */