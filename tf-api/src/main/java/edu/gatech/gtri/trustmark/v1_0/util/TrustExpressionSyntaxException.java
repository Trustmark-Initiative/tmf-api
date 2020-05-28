package edu.gatech.gtri.trustmark.v1_0.util;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/7/17
 */
public class TrustExpressionSyntaxException extends Exception {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustExpressionSyntaxException(Object recognizer,
                                          Object offendingSymbol,
                                          int line,
                                          int charPositionInLine,
                                          String msg,
                                          Throwable recognitionException){
        super(msg, recognitionException);
        this.line = line;
        this.column = charPositionInLine;
        this.offendingSymbol = offendingSymbol;
    }
    //==================================================================================================================
    //  Instance Variable
    //==================================================================================================================
    private int line;
    private int column;
    private Object offendingSymbol;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }
    public Object getOffendingSymbol() {
        return offendingSymbol;
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

}/* end TrustExpressionSyntaxException */