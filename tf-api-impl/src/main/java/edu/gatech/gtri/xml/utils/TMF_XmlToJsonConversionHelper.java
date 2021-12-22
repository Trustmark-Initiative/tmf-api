package edu.gatech.gtri.xml.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 10/10/16
 */
public class TMF_XmlToJsonConversionHelper implements XmlToJsonConversionHelper {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(TMF_XmlToJsonConversionHelper.class);
    private static final String JOIN_KEY_SEPARATOR = "___|___";
    public static Set<List<String>> PLURAL_PATHS;
    public static Map<String, String> SINGULAR_TO_PLURAL_MAP = new HashMap<>();
    public static List<String> PLURAL_COMBINTIONS;
    public static Map<String, Class> XML_TYPE_MAPPINGS;
    public static String[] SCHEMA_FILES = new String[]{
            "/META-INF/xml-schema/tf.xsd"
    };
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    static {
        PLURAL_PATHS = new HashSet<>();
        PLURAL_PATHS.add(toList("ConformanceCriteria", "Citation"));
        PLURAL_PATHS.add(toList("AssessmentSteps", "Artifact"));
        PLURAL_PATHS.add(toList("AssessmentSteps", "ConformanceCriterion"));
        PLURAL_PATHS.add(toList("References", "TrustmarkDefinitionRequirement"));
        PLURAL_PATHS.add(toList("References", "TrustInteroperabilityProfileReference"));
        PLURAL_PATHS.add(toList("ExceptionInfo"));

        SINGULAR_TO_PLURAL_MAP.put("ConformanceCriterion", "ConformanceCriteria");
        SINGULAR_TO_PLURAL_MAP.put("ExceptionInfo", "ExceptionInfo");

        updatePluralCombinations(SINGULAR_TO_PLURAL_MAP);

        try {
            cacheTyping();
        }catch(Throwable t){
            // Just set sensible defaults.
            throw new RuntimeException("Cannot cache types!", t);
        }
    }

    private static void updatePluralCombinations(Map<String, String> plurals){
        PLURAL_COMBINTIONS = new ArrayList<>();

        Set<String> singularValues = plurals.keySet();
        if( singularValues != null && !singularValues.isEmpty() ){
            for( String singular : singularValues ){
                String plural = plurals.get(singular);
                String joinedKey = singular.toLowerCase() + JOIN_KEY_SEPARATOR + plural.toLowerCase();
                PLURAL_COMBINTIONS.add(joinedKey);
            }
        }

    }

    private static List<String> toList(String ... entries){
        List<String> list = new ArrayList<>();
        if( entries != null && entries.length > 0 ){
            for( int i = 0; i < entries.length; i++ ){
                list.add(entries[i]);
            }
        }
        return list;
    }


    /**
     * Reads the TMF XML Schema and sets types for everything.
     */
    private static void cacheTyping() throws Exception {
        XML_TYPE_MAPPINGS = new HashMap<>();

        for( String schemaFile : SCHEMA_FILES ) {
            String schemaContent = inputStreamToString(schemaFile);
            if (schemaContent == null || schemaContent.trim().length() == 0)
                throw new RuntimeException("Cannot read TF.xsd file!");

            Document doc = new SAXReader().read(new StringReader(schemaContent));
            Element schema = doc.getRootElement();
            schema.addNamespace("xs", "http://www.w3.org/2001/XMLSchema");
            appendTypesFromSchema(schema);

        }
    }


    /**
     * Searches the schema for element and/or attribute declarations and puts them in type mapping.
     */
    private static void appendTypesFromSchema(Element schema) {
        String targetNs = (String) schema.selectObject("string(./@targetNamespace)");
        log.debug("Loading from schema: "+targetNs);
        List<Node> elementsWithNameAndType = schema.selectNodes("//xs:*[string-length(./@name) > 0][string-length(./@type) > 0][local-name() = 'element' or local-name() = 'attribute']");
        for( Node element : elementsWithNameAndType ) {
            String name = (String) element.selectObject("string(@name)");
            String xmlType = (String) element.selectObject("string(@type)");
            String qualName = "{"+targetNs+"}" + name;
            Class javaType = xmlTypeToJavaType(xmlType);
            // In theory, the name is contingent on the parent.  But for the TMF spec, it doesn't matter.
            if( !XML_TYPE_MAPPINGS.containsKey(qualName) && !javaType.equals(String.class)) {
                log.debug("Adding element type mapping "+qualName+" => "+javaType.getName());
                XML_TYPE_MAPPINGS.put(qualName, javaType);
            }
        }

    }//end appendTypesFromSchemaString()


    private static Class xmlTypeToJavaType( String xmlType ){
        Class javaType = null;
        if( xmlType.equalsIgnoreCase("xs:number") ) {
            javaType = Double.class;
        }else if( xmlType.equalsIgnoreCase("xs:integer") || xmlType.equalsIgnoreCase("xs:int") || xmlType.equalsIgnoreCase("xs:positiveInteger") ){
            javaType = Integer.class;
        }else if( xmlType.equalsIgnoreCase("xs:boolean") ){
            javaType = Boolean.class;
        }else if( xmlType.equalsIgnoreCase("xs:date") || xmlType.equalsIgnoreCase("xs:dateTime") ){
            javaType = Date.class;
        }else{
            javaType = String.class; // We default to string.
        }
        return javaType;
    }

    private static String inputStreamToString( String resource ) throws IOException, URISyntaxException {
        List<String> lines =
            Files.readAllLines(
                Paths.get(TMF_XmlToJsonConversionHelper.class.getResource(resource).toURI()), Charset.defaultCharset());
        StringBuilder builder = new StringBuilder();
        if( lines != null && lines.size() > 0 ) {
            for (String line : lines) {
                builder.append(line).append("\n");
            }
        }
        return builder.toString();
    }
    //==================================================================================================================
    //  INTERFACES
    //==================================================================================================================


    @Override
    public boolean shouldHandle(Element e) {
//        log.debug("Checking if we should handle {"+e.getNamespaceURI()+"}:"+e.getName());
        if( e.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") )
            return false;

        return true;
    }

    @Override
    public String toPluralValue(String singularTerm) {
        String pluralForm = SINGULAR_TO_PLURAL_MAP.get(singularTerm);
        if( pluralForm == null)
            return singularTerm + "s";
        return pluralForm;
    }

    @Override
    public Boolean isPlural(String parentName, String childName) {
        if( parentName == null )
            return false;

        String joinedKey = childName.toLowerCase() + JOIN_KEY_SEPARATOR + parentName.toLowerCase();
        if( PLURAL_COMBINTIONS.contains(joinedKey) ){
            return true;
        }
        return parentName.equalsIgnoreCase(childName+"s"); // does it end with an s?
    }

    @Override
    public Set<List<String>> getShouldBePluralsSet() {
        return PLURAL_PATHS;
    }


    @Override
    public Class getType(Element element) {
        return getType(element.getNamespaceURI(), element.getName());
    }

    @Override
    public Class getType(Attribute attribute) {
        return getType(attribute.getNamespaceURI(), attribute.getName());
    }


    /**
     * Responsible for setting the value in the given Map using the type inference provided.  Corresponds to the
     * getType() methods.
     */
    @Override
    public void setValueInMap(Map map, Class type, String key, String valueAsString) {
        Object value = doConversion(type, valueAsString);

        if( map.containsKey(key) ){
            List valueList = null;
            if( map.get(key) instanceof List ){
                valueList = (List) map.get(key);
            }else{
                valueList = new ArrayList();
                valueList.add(map.get(key));
            }
            valueList.add(value);
            map.put(key, valueList);
        }else {
            map.put(key, value);
        }
    }

    //==================================================================================================================
    //  HELPER METHODS
    //==================================================================================================================
    private Object doConversion(Class toType, String valueAsString){
        Object value = null;

        if( toType.equals(Boolean.class) ) {
            value = Boolean.parseBoolean(valueAsString.trim());
        }else if( toType.equals(Integer.class) ) {
            value = Integer.parseInt(valueAsString.trim());
        }else if( toType.equals(Date.class) ){
            // This renders the value the same as XML Date time, which is a tricky subset of ISO 8601.
            value = valueAsString.trim();
        }else{
            value = valueAsString.trim();
        }

        return value;
    }

    private Class getType(String namespace, String name){
        String qualName = "{"+namespace+"}"+name;
        if( XML_TYPE_MAPPINGS.containsKey(qualName) ){
            return XML_TYPE_MAPPINGS.get(qualName);
        }else{
            return String.class;
        }
    }

}/* end TMF_XmlToJsonConversionHelper */
