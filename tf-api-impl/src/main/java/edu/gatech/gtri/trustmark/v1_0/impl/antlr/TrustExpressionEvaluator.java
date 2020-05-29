package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * This implementation of the {@link TrustExpressionAdapter} will actually perform the complete evaluation of the Trust
 * Expression against with the trustmark binding data to provide a true/false answer.
 * <br/><br/>
 * @author brad
 * @date 3/6/17
 */
public class TrustExpressionEvaluator extends TrustExpressionAdapter {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(TrustExpressionEvaluator.class);
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustExpressionEvaluator(String trustExpression, List<Map> trustmarkBindingData){
        log.debug("Creating a new TrustExpressionEvaluator["+trustExpression+"]...");
        this.trustExpression = trustExpression;
        this.trustmarkBindingData = trustmarkBindingData;
    }
    //==================================================================================================================
    //  INSTANCE VARS
    //==================================================================================================================
    private String trustExpression;
    private List<Map> trustmarkBindingData;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public String getTrustExpression() {
        return trustExpression;
    }
    public List<Map> getTrustmarkBindingData() {
        return trustmarkBindingData;
    }
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    /**
     * Scans the pre-configured list of trustmark data and returns the one with the given id.
     */
    private Map getTrustmark(String id){
        Map data = null;
        if( this.trustmarkBindingData != null && this.trustmarkBindingData.size() > 0 ){
            for( Map m : this.trustmarkBindingData ){
                if( ((String) m.get("id")).equalsIgnoreCase(id) ){
                    data = m;
                    break;
                }
            }
        }
        return data;
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

}/* end TrustExpressionEvaluator */