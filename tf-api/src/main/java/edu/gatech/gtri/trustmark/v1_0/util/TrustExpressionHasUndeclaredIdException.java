package edu.gatech.gtri.trustmark.v1_0.util;

import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/8/17
 */
public class TrustExpressionHasUndeclaredIdException extends Exception {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustExpressionHasUndeclaredIdException(String trustExpression, List<String> idsNotDeclared, List<String> declaredVars){
        super("Could not find IDs: "+idsNotDeclared+" in trust profile's declared variables.");
        this.trustExpression = trustExpression;
        this.idsNotDeclared = idsNotDeclared;
        this.declaredVars = declaredVars;
    }
    //==================================================================================================================
    //  INSTANCE VARS
    //==================================================================================================================
    private String trustExpression;
    private List<String> idsNotDeclared;
    private List<String> declaredVars;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public String getTrustExpression() {
        return trustExpression;
    }
    public List<String> getIdsNotDeclared() {
        return idsNotDeclared;
    }
    public List<String> getDeclaredVars() {
        return declaredVars;
    }
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

}/* end TrustExpressionHasUndeclaredIdException */