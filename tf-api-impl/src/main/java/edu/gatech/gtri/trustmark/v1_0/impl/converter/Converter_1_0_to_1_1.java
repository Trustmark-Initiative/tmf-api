package edu.gatech.gtri.trustmark.v1_0.impl.converter;

import edu.gatech.gtri.trustmark.v1_0.conversion.ConversionException;
import edu.gatech.gtri.trustmark.v1_0.conversion.Converter;
import edu.gatech.gtri.trustmark.v1_0.conversion.ModelVersion;
import edu.gatech.gtri.trustmark.v1_0.conversion.ModelVersionSimple;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for converting from version 1.0 to 1.1.
 * <br/><br/>
 * @author brad
 * @date 10/25/16
 */
public class Converter_1_0_to_1_1 implements Converter {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(Converter_1_0_to_1_1.class);
    public static final String NS_URI_10 = "https://trustmark.gtri.gatech.edu/specifications/trustmark-framework/1.0/schema/";
    public static final String NS_URI_11 = "https://trustmark.gtri.gatech.edu/specifications/trustmark-framework/1.1/schema/";
    public static final String[] SUPPORTED_ELEMENTS = new String[]{"TrustmarkDefinition", "Trustmark", "TrustInteroperabilityProfile", "TrustmarkStatusReport"};
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  INTERFACE METHODS
    //==================================================================================================================
    @Override
    public ModelVersion getInputVersion() {
        return new ModelVersionSimple("1.0");
    }
    @Override
    public ModelVersion getOutputVersion() {
        return new ModelVersionSimple("1.1");
    }

    @Override
    public boolean supports(String nsUri, String localname) {
        return NS_URI_10.equals(nsUri) && isSupportedElement(localname);
    }

    @Override
    public boolean supports(File file) {
        log.debug("Checking if we support file: "+file.getName());
        if( !file.getName().toLowerCase().endsWith(".xml") ){
            log.warn("Given file["+file.getName()+"] is not XML!  File extension does not end with .xml so we assume it is not XML.");
            return false; // We only support files we reasonably believe are XML.
        }

        try {
            boolean supports = false;
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            FileInputStream fileInputStream = new FileInputStream(file);
            XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(fileInputStream);
            boolean encounteredRoot = false;
            while( !encounteredRoot ) {
                XMLEvent e = xmlEventReader.nextEvent();
                if( e.getEventType() == XMLEvent.START_ELEMENT ){
                    encounteredRoot = true;
                    StartElement startElementEvent = (StartElement) e;
                    QName elementName = startElementEvent.getName();
                    if( elementName.getNamespaceURI().equals(NS_URI_10) ){
                        if( isSupportedElement(elementName.getLocalPart()) ){
                            log.debug("File["+file.getName()+"] is supported, as it is ["+elementName.getNamespaceURI()+"]: "+elementName.getLocalPart());
                            supports = true;
                        }else{
                            log.warn("File["+file.getName()+"] is not supported, as it's root element is ["+elementName.getLocalPart()+"] which is not supported.");
                            supports = false;
                        }
                    }else{
                        log.warn("File["+file.getName()+"] is not supported, as it's root namespace is ["+elementName.getNamespaceURI()+"] instead of ["+NS_URI_10+"]");
                        supports = false;
                    }
                }
            }
            xmlEventReader.close();
            fileInputStream.close();
            return supports;
        }catch(Throwable t){
            log.error("An unexpected error occured inspecting XML file["+file.getName()+"]", t);
            return false;
        }
    }

    @Override
    public void convert(File input, File output) throws IOException, ConversionException {
        log.debug("Converting TMF File["+input.getName()+"] from version 1.0 to 1.1...");
        String xmlContent = FileUtils.readFileToString(input);
        Matcher idMatcher = Pattern.compile("<tf:Identifier>(.*?)</tf:Identifier>", Pattern.MULTILINE+Pattern.DOTALL).matcher(xmlContent);
        idMatcher.find();
        String id = idMatcher.group(1).trim(); // If identifier not found, this is an error.

        List<String> supersedes = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        log.debug("Searching for keywords/supersedes...");
        Matcher commentMatcher = Pattern.compile(Pattern.quote("<!--")+"(.*?)"+Pattern.quote("-->"), Pattern.MULTILINE+Pattern.DOTALL).matcher(xmlContent);
        while( commentMatcher.find() ){
            String comment = commentMatcher.group(1);
            log.debug("Encountered Comment: "+comment);
            if( comment == null ) comment = "";
            comment = comment.trim();
            if( comment.length() > 21 && comment.substring(11).startsWith("Supersedes") ){
                String superseded = comment.substring(11 + "Supersedes".length()).trim();
                log.debug("Encountered Supersedes Comment: "+superseded);
                supersedes.add(superseded);
            }else if( comment.startsWith("KEYWORD: ") ){
                String keyword = comment.substring(8).trim();
                log.debug("Encountered Keyword Comment: "+keyword);
                keywords.add(keyword);
            }
        }
        if(keywords.size() == 0 && supersedes.size()==0){
            log.debug("no keywords or supersedes found!");
        }

        xmlContent = removeComments(xmlContent);
        xmlContent = replaceNsURI(xmlContent, NS_URI_10, NS_URI_11);
        xmlContent = addKeywordsSupersedes(xmlContent, keywords, supersedes);

        FileUtils.writeStringToFile(output, xmlContent);
    }

    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================
    private String addKeywordsSupersedes(String xml, List<String> keywords, List<String> supersedes){
        String keywordsXml = buildKeywordsSnippet(keywords);
        String supersessionsSnippet = buildSupersessionsSnippet(supersedes);
        String xmlToInsert = "\n"+supersessionsSnippet + "\n" + keywordsXml + "\n";

        if( xmlToInsert.trim().length() > 0 ) {
            String newXml = xml;
            if (xml.contains("tf:TrustInteroperabilityProfile")) {
                newXml = prependInText(xml, "<tf:References>", xmlToInsert);
            } else {
                newXml = prependInText(xml, "</tf:Metadata>", xmlToInsert);
            }
            return newXml;
        }else{
            log.debug("No keywords/supersedes info to add.");
            return xml;
        }
    }

    private String prependInText(String text, String positionString, String toInsert){
        int index = text.indexOf(positionString);
        String preString = text.substring(0, index);
        String postString = text.substring(index);
        return preString + toInsert + postString;
    }

    private String buildSupersessionsSnippet(List<String> supersedes) {
        StringBuilder xml = new StringBuilder();
        if( !supersedes.isEmpty() ){
            xml.append("    <tf:Supersessions>\n");
            for(String supersededUri : supersedes ) {
                xml.append("        <tf:Supersedes><tf:Identifier>"+supersededUri+"</tf:Identifier></tf:Supersedes>\n");
            }
            xml.append("    </tf:Supersessions>\n");
        }
        return xml.toString();
    }


    private String buildKeywordsSnippet(List<String> keywords) {
        StringBuilder xml = new StringBuilder();
        if( !keywords.isEmpty() ){
            xml.append("    <tf:Keywords>\n");
            for(String keyword : keywords ) {
                xml.append("        <tf:Keyword>"+keyword+"</tf:Keyword>\n"); // TODO May need to sanitize.
            }
            xml.append("    </tf:Keywords>\n");
        }
        return xml.toString();
    }


    public String removeComments(String xmlContent){
        return xmlContent.replaceAll( "(?s)<!--.*?-->", "" ); // this line removes ANY comments in the file.
    }

    public String replaceNsURI(String xmlContent, String oldUri, String newUri){
        log.debug("Request to replace NSURI bindings for ["+oldUri+"] with ["+newUri+"]...");
        Pattern nsBindingPattern = Pattern.compile("xmlns:(.*?)=\""+Pattern.quote(oldUri)+"\"");
        Stack<Map> replacementPositions = new Stack<>();
        Matcher nsBindingMatcher = nsBindingPattern.matcher(xmlContent);
        while(nsBindingMatcher.find()){
            int startIndex = nsBindingMatcher.start();
            String nsPrefix = nsBindingMatcher.group(1);
            log.debug("  Encountered NS Binding at "+startIndex+", with prefix="+nsPrefix);
            Map replaceMe = new HashMap();
            replaceMe.put("startIndex", startIndex);
            replaceMe.put("nsPrefix", nsPrefix);
            replacementPositions.push(replaceMe);
        }

        if( replacementPositions.isEmpty() )
            log.warn("Could not find any places to replace the NS URI["+oldUri+"]!");

        String newXml = xmlContent;
        while( !replacementPositions.isEmpty() ){
            Map m = replacementPositions.pop();
            int startReplacement = calculateStartReplace(m);
            log.debug("  Replacing ns binding at position "+startReplacement);
            String firstHalf = newXml.substring(0, startReplacement);
            String backHalf = newXml.substring(startReplacement+oldUri.length());
            newXml = firstHalf + newUri + backHalf;
        }

        return newXml;
    }

    private int calculateStartReplace(Map m){
        int start = (Integer) m.get("startIndex");
        String nsPrefix = (String) m.get("nsPrefix");
        String startString = "xmlns:"+nsPrefix+"=\"";
        return start + startString.length();
    }

    private boolean isSupportedElement(String elementName){
        boolean found = false;
        for( int i = 0; i < SUPPORTED_ELEMENTS.length; i++ ){
            if( SUPPORTED_ELEMENTS[i].equals(elementName) ){
                found = true;
                break;
            }
        }
        return found;
    }


}/* end Converter_1_0_to_1_1 */
