package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by brad on 3/17/16.
 */
public interface MultiTrustmarkDefinitionBuilderFactory {

    /**
     * This will create an output to the excel spreadsheet where each line represents a TrustmarkDefinition.
     * <br/><br/>
     * @param outputFile the output file (which will become an excel file)
     * @return a builder which operates on that file.
     */
    public MultiTrustmarkDefinitionBuilder createExcelOutput(File outputFile) throws IOException;


}//end MultiTrustmarkDefinitionBuilderFactory()