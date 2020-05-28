package edu.gatech.gtri.trustmark.v1_0.conversion;

import java.io.File;
import java.io.IOException;

/**
 * Defines how a conversion takes place.
 * <br/><br/>
 * @author brad
 * @date 10/25/16
 */
public interface Converter {

    /**
     * Returns the model version that this converter handles, in case that is known.
     */
    public ModelVersion getInputVersion();

    /**
     * Returns the model version that this converter converts to.
     */
    public ModelVersion getOutputVersion();

    /**
     * In some situations, we just want to parse out an XML elements name once, and then use this method.
     */
    public boolean supports(String nsUri, String localname);

    /**
     * Returns true if the the system supports converting from the given {@link File} to any object in the supported
     * output data model.
     */
    public boolean supports(File file);

    /**
     * Performs the conversion on the given input file to the given output file.
     * @param input
     * @param output
     * @throws IOException
     * @throws ConversionException
     */
    public void convert(File input, File output) throws IOException, ConversionException;

}/* end Converter */