package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import org.antlr.v4.runtime.Token;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of a {@link TrustExpressionListener} which will gather all Identifiers declared by the trust
 * expression being parsed.  That's all it does, make those available for later.
 * <br/><br/>
 * @author brad
 * @date 3/8/17
 */
public class TrustExpressionVariableProducer extends TrustExpressionAdapter {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = Logger.getLogger(TrustExpressionVariableProducer.class);
    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private List<String> identifiers = new ArrayList<>();
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public List<String> getIdentifiers() {
        if( identifiers == null )
            identifiers = new ArrayList<>();
        return identifiers;
    }
    //==================================================================================================================
    //  ADDERS
    //==================================================================================================================
    public List<String> addIdentifier(String identifier){
        log.debug("Adding identifier: "+identifier);
        this.getIdentifiers().add(identifier);
        return this.getIdentifiers();
    }
    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  OVERRIDE METHODS
    //==================================================================================================================

    @Override
    public void exitFieldReference(TrustExpressionParser.FieldReferenceContext ctx) {
        super.exitFieldReference(ctx);

        log.debug("Entered exitFieldReference("+ctx.getText()+")");
        Token identifier = ctx.identifier;
        String idText = identifier.getText();
        log.info("Found field reference identifier: "+idText);
        this.addIdentifier(idText);
    }



}/* end TrustExpressionVariableProducer */