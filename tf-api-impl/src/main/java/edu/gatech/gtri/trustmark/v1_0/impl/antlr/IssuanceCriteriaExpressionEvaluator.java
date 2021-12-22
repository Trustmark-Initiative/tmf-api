package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * This class must be initialized with the Assessment Step data, and as the parser runs it will evaluate the expression
 * as true or false.
 * <br/><br/>
 * Created by brad on 4/28/16.
 */
public class IssuanceCriteriaExpressionEvaluator extends IssuanceCriteriaListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(IssuanceCriteriaExpressionEvaluator.class);


    private Boolean satisfied = false;
    private boolean hasError = false;
    private String error = null;
    private List<AssessmentStepResult> results;

    public Boolean getSatisfied() {
        return satisfied;
    }

    public boolean isHasError() {
        return hasError;
    }

    public String getError() {
        return error;
    }

    public List<AssessmentStepResult> getResults() {
        return results;
    }

    public IssuanceCriteriaExpressionEvaluator(String expression, List<AssessmentStepResult> results){
        this.results = results;
        Collections.sort(this.results, new Comparator<AssessmentStepResult>() {
            @Override
            public int compare(AssessmentStepResult o1, AssessmentStepResult o2) {
                return o1.getAssessmentStepNumber().compareTo(o2.getAssessmentStepNumber());
            }
        });
        log.debug("Given "+results.size()+" results: ");
        for( AssessmentStepResult result : results ){
            log.debug("  RESULT: id="+result.getAssessmentStepId()+", number="+result.getAssessmentStepNumber()+", result="+result.getResult());
        }
    }


    private Map<IssuanceCriteriaParser.PredicateContext, Boolean> predicateEvaluations = new HashMap<>();
    private Map<IssuanceCriteriaParser.ExpressionContext, Boolean> expressionEvaluations = new HashMap<>();


    @Override
    public void exitIssuanceCriteriaExpression(IssuanceCriteriaParser.IssuanceCriteriaExpressionContext ctx) {
        satisfied = expressionEvaluations.get(ctx.expression());
        if( satisfied == null ){
            this.hasError = true;
            this.error = "Expected root expression to have a boolean value, but it was null";
        }else{
            log.debug("Expression evaluated to "+this.satisfied);
        }
    }

    @Override
    public void exitExpression(IssuanceCriteriaParser.ExpressionContext ctx) {
        if( ctx.AND_OR() != null ) {
            Boolean leftSide = null;
            Boolean rightSide = null;
            for( IssuanceCriteriaParser.ExpressionContext expr : ctx.expression() ){
                if( expressionEvaluations.get(expr) != null && leftSide == null){
                    leftSide = expressionEvaluations.get(expr);
                }else if( expressionEvaluations.get(expr) != null && leftSide != null ){
                    rightSide = expressionEvaluations.get(expr);
                }else{
                    log.debug("   Expression["+expr+"] has no value yet!");
                }
            }
            if( leftSide != null && rightSide != null ) {
                if( ctx.AND_OR().toString().equalsIgnoreCase("AND") ){
                    log.debug("AND Expression["+leftSide+" && "+rightSide+"] evaluates to "+(leftSide && rightSide));
                    expressionEvaluations.put(ctx, leftSide && rightSide);
                }else{
                    log.debug("OR Expression["+leftSide+" || "+rightSide+"] evaluates to "+(leftSide || rightSide));
                    expressionEvaluations.put(ctx, leftSide || rightSide);
                }
            }
        }else if( ctx.predicate() != null ){
            expressionEvaluations.put(ctx, predicateEvaluations.get(ctx.predicate()));
        }else if( ctx.LPAREN() != null && ctx.RPAREN() != null ) {
            expressionEvaluations.put(ctx, expressionEvaluations.get(ctx.expression(0)));
        }else if( ctx.NOT() != null ){
            boolean embeddedExprValue = expressionEvaluations.get(ctx.expression(0));
            log.debug("   NOT("+embeddedExprValue+") = "+(!embeddedExprValue));
            expressionEvaluations.put(ctx, !embeddedExprValue);
        }else{
            log.debug("exitExpression()");
        }
    }

    @Override
    public void exitPredicate(IssuanceCriteriaParser.PredicateContext ctx) {
        if( hasError ) return; // If we have already encountered an error, then don't bother doing anything.

        Boolean evaluationResult = false;

        AssessmentStepResultType desiredLogicOp = null;
        if( ctx.LOGIC_OP().toString().equalsIgnoreCase("yes") ){
            desiredLogicOp = AssessmentStepResultType.YES;
        }else if( ctx.LOGIC_OP().toString().equalsIgnoreCase("no") ){
            desiredLogicOp = AssessmentStepResultType.NO;
        }else if( ctx.LOGIC_OP().toString().equalsIgnoreCase("na") ){
            desiredLogicOp = AssessmentStepResultType.NA;
        }

        List<String> stepIdsToCheck = new ArrayList<>();
        if( ctx.STEP_ID() != null ){
            String id = ctx.STEP_ID().toString().trim();
            if( id.equalsIgnoreCase("ALL") ){
                for( AssessmentStepResult result : this.results ){
                    stepIdsToCheck.add(result.getAssessmentStepId());
                }
            }else if(id.equalsIgnoreCase("NONE") ){
                // In this special case, we actually verify that NONE of the rules matches the value
                for( AssessmentStepResult result : this.results ){
                    if( result.getResult() == desiredLogicOp ){
                        predicateEvaluations.put(ctx, false);
                        return;
                    }
                }

                predicateEvaluations.put(ctx, true);
                return;
            }else {
                stepIdsToCheck.add(id);
            }
        }else if( ctx.STEP_ID_SET() != null ){
            String[] idsSet = ctx.STEP_ID_SET().toString().split(Pattern.quote(","));
            for( String id : idsSet ){
                stepIdsToCheck.add(id.trim());
            }
        }else if( ctx.STEP_ID_SEQUENCE() != null ){
            String[] seqParts = ctx.STEP_ID_SEQUENCE().toString().split(Pattern.quote("..."));
            String startId = seqParts[0].trim();
            String endId = seqParts[1].trim();

            int startIndex = getIndexOfStepId(startId);
            int endIndex = getIndexOfStepId(endId);

            if( startIndex == -1 ){
                this.hasError = true;
                this.error = "Could not find step '"+startId+"'";
                return;
            }else if( endIndex == -1 ){
                this.hasError = true;
                this.error = "Could not find step '"+endIndex+"'";
                return;
            }else if( startIndex > endIndex ){
                this.hasError = true;
                this.error = "Invalid Step Sequence "+startId+"..."+endId+", the order seems reversed.";
                return;
            }

            for( int i = startIndex; i < endIndex; i++ ){
                stepIdsToCheck.add(this.results.get(i).getAssessmentStepId());
            }

        }

        evaluationResult = evaluatePredicateData(stepIdsToCheck, desiredLogicOp);

        log.debug(printPredicate(ctx, evaluationResult));
        predicateEvaluations.put(ctx, evaluationResult);
    }

    /**
     * If the value of all stepIds is equal to the desired logic op, then true is returned.  Else, false is returned.
     */
    private Boolean evaluatePredicateData( List<String> stepIds, AssessmentStepResultType desiredLogicOp ){
        if( desiredLogicOp == null || stepIds == null || stepIds.isEmpty() )
            return false;

        boolean evaluationResult = true;
        for( String stepId : stepIds ){
            if( getIndexOfStepId(stepId) == -1 ){
                hasError = true;
                error = "The result data does not have step id '"+stepId+"'";
                return false;
            }

            AssessmentStepResultType result = findStepResult(stepId);
            evaluationResult = evaluationResult && desiredLogicOp == result;
        }
        return evaluationResult;
    }

    private int getIndexOfStepId(String id){
        // First, we quickly run through and see if there is a direct match on id.  That's the primary thing.
        for( int i = 0; i < results.size(); i++ ) {
            AssessmentStepResult result = results.get(i);
            if (result.getAssessmentStepId().equalsIgnoreCase(id)) {
                return i;
            }
        }

        // Next, see if we can parse it as an integer, return UNKNOWN if we can't.
        Integer asNumber = null;
        try{
            asNumber = Integer.parseInt(id);
        }catch(Throwable t){
            return -1;
        }

        for( int i = 0; i < results.size(); i++ ) {
            AssessmentStepResult result = results.get(i);
            if (result.getAssessmentStepNumber().equals(asNumber)) {
                return i;
            }
        }

        // Finally, we can't find it so return -1.
        return -1;

    }


    private AssessmentStepResultType findStepResult(String id){
        if( id == null || id.trim().length() == 0 )
            return AssessmentStepResultType.UNKNOWN;
        int index = getIndexOfStepId(id);
        return this.results.get(index).getResult();
    }


    private String printPredicate(IssuanceCriteriaParser.PredicateContext ctx, Boolean eval) {
        if( ctx.STEP_ID() != null )
            return ctx.LOGIC_OP()+"("+ctx.STEP_ID()+") = "+eval;

        if( ctx.STEP_ID_SET() != null )
            return ctx.LOGIC_OP()+"("+ctx.STEP_ID_SET()+") = "+eval;

        if( ctx.STEP_ID_SEQUENCE() != null )
            return ctx.LOGIC_OP()+"("+ctx.STEP_ID_SEQUENCE()+") = "+eval;

        return "<ERROR IN PRED CONTEXT>";
    }

}
