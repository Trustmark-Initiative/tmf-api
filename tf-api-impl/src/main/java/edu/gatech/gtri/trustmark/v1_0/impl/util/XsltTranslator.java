package edu.gatech.gtri.trustmark.v1_0.impl.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Performs XSLT Translations in a consistent way.
 * <br/><br/>
 * Created by brad on 9/2/14.
 */
public class XsltTranslator {
    //==================================================================================================================
    //  Public Static Variables
    //==================================================================================================================
    /**
     * If a system property is set with this value, then the value is assumed to be a path where debug output can
     * be written.
     */
    public static final String DEBUG_OUTPUT_SYSTEM_PROPERTY = XsltTranslator.class.getName() + ".DEBUG_OUTPUT";

    //==================================================================================================================
    //  Public Static Methods
    //==================================================================================================================
    /**
     * performs the translation using the JAXP interface.
     */
    public static void translate(String xsltText, InputStream input, OutputStream output)
    throws TransformerConfigurationException, TransformerException, IOException {
        translate( xsltText, new InputStreamReader(input), new OutputStreamWriter(output, StandardCharsets.UTF_8));
    }//end translate()

    /**
     * performs the translation using the JAXP interface.
     */
    public static void translate(String xsltText, Reader reader, Writer writer)
    throws TransformerConfigurationException, TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new StringReader(xsltText));
        Transformer transformer = factory.newTransformer(xslt);
        Source xmlInput = new StreamSource(reader);
        Result htmlOutput = null;
        if( _hasDebugProperty() && _verifyDebugOutDir() ){
            StringWriter xmlOutTemp = new StringWriter();
            htmlOutput = new StreamResult(xmlOutTemp);
            transformer.transform(xmlInput, htmlOutput);
            String xmlOutString = xmlOutTemp.toString();
            _writeDebugFile(xmlOutString);
            _copy(xmlOutString, writer);
        }else{
            htmlOutput = new StreamResult(writer);
            transformer.transform(xmlInput, htmlOutput);
        }
    }//end translate()


    //==================================================================================================================
    //  Private Static Helper Methods
    //==================================================================================================================
    private static boolean _hasDebugProperty() {
        String value = System.getProperty(DEBUG_OUTPUT_SYSTEM_PROPERTY);
        if( value != null && value.trim().length() > 0 ){
            return true;
        }else{
            return false;
        }
    }

    private static boolean _verifyDebugOutDir() {
        String value = System.getProperty(DEBUG_OUTPUT_SYSTEM_PROPERTY);
        File debugDir = new File(value);
        if( !debugDir.exists() ){
            if( !debugDir.mkdirs() ) {
                System.err.println("**** ERROR Making XLST out debug directory for class: "+XsltTranslator.class.getName());
                return false;
            }else{
                return true;
            }
        }else{
            if( debugDir.isDirectory() ){
                return true;
            }else{
                System.err.println("**** ERROR XLST out debug directory path["+value+"] is not a directory. "+XsltTranslator.class.getName());
                return false;
            }
        }
    }

    private static void _writeDebugFile( String text ) throws IOException {
        String value = System.getProperty(DEBUG_OUTPUT_SYSTEM_PROPERTY);
        File debugDir = new File(value);
        File outputFile = new File( debugDir, System.currentTimeMillis() + ".html" );
        _copy(text, new FileWriter(outputFile));
    }

    private static void _copy( String text, Writer writer) throws IOException {
        writer.write(text);
        writer.flush();
    }

}//end XsltTranslator
