package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

import java.util.List;

/**
 * Instances of this interface know how to serialize lots of trustmark definition objects out in a single session.  For
 * example, writing them to a microsoft spreadsheet.
 * <br/><br/>
 * Created by brad on 3/17/16.
 */
public interface MultiTrustmarkDefinitionBuilder {

    /**
     * Allows this builder to initialize itself.
     */
    public void init();

    /**
     * Called after all additions have been made.  The return value is specific to the builder implementation, such as
     * the file data was output to or perhaps the directory files were written into.
     */
    public Object finish();

    /**
     * Adds a single TrustmarkDefinition to the output.
     */
    public void add( TrustmarkDefinition definition );

    /**
     * A convenience method for adding lots of TDs at once.
     */
    public void addAll(List<TrustmarkDefinition> tds);


}//end MultiTrustmarkDefinitionBuilder