package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.MultiTrustmarkDefinitionBuilder;
import edu.gatech.gtri.trustmark.v1_0.io.MultiTrustmarkDefinitionBuilderFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by brad on 3/18/16.
 */
public class MultiTrustmarkDefinitionBuilderFactoryImpl implements MultiTrustmarkDefinitionBuilderFactory {

    @Override
    public MultiTrustmarkDefinitionBuilder createExcelOutput(File outputFile) throws IOException {
        return new ExcelMultiTdBuilder(outputFile);
    }

}
