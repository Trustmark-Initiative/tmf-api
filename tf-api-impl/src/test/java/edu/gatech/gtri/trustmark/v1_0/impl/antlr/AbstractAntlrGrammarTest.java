package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/7/17
 */
public abstract class AbstractAntlrGrammarTest extends AbstractTest {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================

    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================

    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================

    //==================================================================================================================
    //  SETTERS
    //==================================================================================================================

    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================
    protected static JSONObject readJsonFile(File file) throws Exception {
        StringBuilder jsonTextBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while( (line=reader.readLine()) != null ){
            jsonTextBuilder.append(line).append("\n");
        }
        String jsonText = jsonTextBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonText);
        return jsonObject;
    }


    /**
     * Handles building paths using File.separator in an easy way.
     */
    protected static String buildPath(String ... pathComponents){
        StringBuilder builder = new StringBuilder();
        for( int i = 0; i < pathComponents.length; i++ ){
            builder.append(pathComponents[i]);
            if( i < (pathComponents.length - 1)){
                builder.append(File.separator);
            }
        }
        return builder.toString();
    }
    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

}/* end AbstractAntlrGrammarTest */