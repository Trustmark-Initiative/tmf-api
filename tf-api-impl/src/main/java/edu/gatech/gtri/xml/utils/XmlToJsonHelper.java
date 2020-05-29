package edu.gatech.gtri.xml.utils;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.json.JSONObject;

import java.util.*;

/**
 * Performs some custom rules to convert XML to JSON.
 * <br/><br/>
 * @user brad
 * @date 10/7/16
 */
public class XmlToJsonHelper {
    //====================================================================================================================
    //  STATIC VARIABLES
    //====================================================================================================================
    private static final Logger log = Logger.getLogger(XmlToJsonHelper.class);

    //====================================================================================================================
    //  PUBLIC METHODS
    //====================================================================================================================
    /**
     * Performs a conversion from Dom4j to JSON using some nasty rules I invented.
     */
    public static String toJson(Element element) throws Exception {
        return toJson(element, new TMF_XmlToJsonConversionHelper());
    }
    public static String toJson(Element element, XmlToJsonConversionHelper xmlToJsonConversionHelper) throws Exception {
        if( !isOneOfSupportedRootTypes(element) ){
            log.error("The calling code has passed {"+element.getNamespace().toString()+"}:"+element.getName());
            throw new UnsupportedOperationException("Cannot convert non TMF Root Element: "+element.getName());
        }

        log.debug("Building initial map...");
        Map map = new HashMap();
        _applyAttributesToMap(xmlToJsonConversionHelper, element, map);
        _handleElement(xmlToJsonConversionHelper, element, map);

        log.debug("Prior to plurals fix: \n"+new JSONObject(map).toString(2));

        log.debug("Fixing plural errors (where parent and child have the same name)...");
        fixPlurals(xmlToJsonConversionHelper, map, null, null); // The first one has no parent or key

        log.debug("Fixing what should-be plurals...");
        fixShouldBePlurals(xmlToJsonConversionHelper, map);

        log.debug("Successfully generated Map from XML, converting to JSON using org.json libraries...");
        return new JSONObject(map).toString(2);
    }//end toJson()

    //====================================================================================================================
    //  PRIVATE METHODS
    //====================================================================================================================
    /**
     * Starts the recursive process of fixing would be plurals based on the pluralList(s) as given by the conversion
     * helper.
     */
    private static void fixShouldBePlurals(XmlToJsonConversionHelper xmlToJsonConversionHelper, Map map){
        Set<List<String>> pluralsSet = xmlToJsonConversionHelper.getShouldBePluralsSet();
        if( pluralsSet != null && !pluralsSet.isEmpty() ){
            for( List<String> pluralList : pluralsSet ){
                fixShouldBePlural(xmlToJsonConversionHelper, map, pluralList, 0);
            }
        }
    }//end fixShouldBePlurals()

    /**
     * The recursive function to fix a plural.  If not at the last position in the document, a recursive step is made
     * (note, collections are recursed on *each* item).  If the last position is given then the removal/insertion occurs
     * as expected.
     */
    private static void fixShouldBePlural(XmlToJsonConversionHelper xmlToJsonConversionHelper, Map map, List<String> pluralList, int position){
        if( pluralList != null && !pluralList.isEmpty() ){
            if( position < 0 || position >= pluralList.size())
                throw new ArrayIndexOutOfBoundsException("Given invalid index "+position+" while processing plurals list.");
            String key = pluralList.get(position);
            boolean last = position == (pluralList.size() - 1);
            Object value = map.get(key);
            if( value == null ){
                return;
            }

            if( last ){
                List newValue = null;
                if( value instanceof List ){ // The value is already a list, so we just stick it back in under plural name.
                    newValue = (List) value;
                }else{ // This value is either a primitive or a value, so just wrap it in a list and stick it back in the map under a plural name.
                    newValue = new ArrayList();
                    newValue.add(value);
                }
                String newkey = xmlToJsonConversionHelper.toPluralValue(key);
                map.remove(key);
                log.debug("  Fixing "+listToPathString(pluralList)+", moving to "+newkey);
                map.put(newkey, newValue);
            }else{
                if( value instanceof Map ){
                    Map subMap = (Map) value;
                    fixShouldBePlural(xmlToJsonConversionHelper, subMap, pluralList, position + 1);
                }else if( value instanceof Collection ) {
                    Collection valueCollection = (Collection) value;
                    for (Object obj : valueCollection) {
                        if (obj instanceof Map) {
                            fixShouldBePlural(xmlToJsonConversionHelper, (Map) obj, pluralList, position + 1);
                        } else {
                            throw new RuntimeException("Asked to go to " + key + ", but it is not a Map value!  Cannot descend to fix should-be plurals.");
                        }
                    }
                }else{
                    throw new RuntimeException("Asked to go to "+key+", but it is not a Map value!  Cannot descend to fix should-be plurals.");
                }
            }
        }
    }//end fixShouldBePlural

    private static String listToPathString(List<String> list){
        StringBuilder builder = new StringBuilder();
        if( list != null && list.size() > 0 ) {
            for( int i = 0; i < list.size(); i++ ){
                builder.append(list.get(i));
                if( i < (list.size() - 1) ){
                    builder.append(".");
                }
            }
        }else{
            builder.append("<empty>");
        }
        return builder.toString();
    }

    /**
     * A recursive repair algorithm which operates in-place on the first argument Map.  It looks for parent/child
     * plural/singular patterns and repairs that.  So AssessmentSteps -> AssessmentStep array is just set to AssessmentSteps.
     * Note that additional children are moved up and prepended with the singlar name.  So AssessmentSteps -> Preface
     * will become AssessmentStepPreface in the parent map.
     */
    private static void fixPlurals(XmlToJsonConversionHelper xmlToJsonConversionHelper, Map source, Map parent, Object mapKeyInParent){
        String childSingular = hasChildSingular(xmlToJsonConversionHelper, source, (String) mapKeyInParent);
        if( childSingular != null ) {
            List values = null;
            log.debug("  Encountered child singular("+childSingular+") of plural parent("+mapKeyInParent+"), fixing...");
            if( source.get(childSingular) instanceof List ){
                values = (List) source.get(childSingular);
            }else{
                values = new ArrayList();
                values.add(source.get(childSingular));
            }
            source.remove(childSingular);
            parent.put( xmlToJsonConversionHelper.toPluralValue(childSingular), values);

            // For all other keys, we append the singular to their name.
            Set<String> keys = source.keySet();
            List<String> mapKeys = new ArrayList<>();
            for( String key : keys ) {
                if( !key.equals(childSingular) ) {
                    Object value = source.get(key);
                    parent.put(childSingular+key, value);
                }
            }
        }
        while( hasPluralRemaining(xmlToJsonConversionHelper, source, (String) mapKeyInParent) ) {
            List<String> mapKeys = getKeysOfType(source, Map.class);
            List<String> listKeys = getKeysOfType(source, List.class);
            for (String mapKey : mapKeys) {
                fixPlurals(xmlToJsonConversionHelper, (Map) source.get(mapKey), source, mapKey);
            }
            for (String listKey : listKeys) {
                if (((List) source.get(listKey)).get(0) instanceof Map) {
                    List<Map> mapsList = (List) source.get(listKey);
                    for (Map map : mapsList) {
                        fixPlurals(xmlToJsonConversionHelper, map, null, "<list:" + listKey + ">");
                    }
                }
            }
        }
    }//end fixPlurals()

    /**
     * Searches all key/value pairs in the Map.  If the value of a key is assignable to the given class "c", then
     * the key is added to a List of key values and returned.  If not, the key is ignored.  You would use this method
     * to say "Give me all keys from a Map where the value of the key is of class List", etc
     */
    private static List<String> getKeysOfType(Map map, Class c){
        List<String> matchingKeys = new ArrayList<>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            if( c.isAssignableFrom(value.getClass()) ){
                matchingKeys.add(key);
            }
        }
        return matchingKeys;
    }

    /**
     * Analyzes the Map to find any plurals remaining.  True if some exist, false otherwise.  A plural is defined as
     * a structure like this:
     *  {
     *      "Hats" : {
     *          "Hat": ...
     *      }
     *  }
     *
     *  Note that this method "out-sources" plurality checks to the method "hasChildSingular()".
     */
    private static boolean hasPluralRemaining(XmlToJsonConversionHelper xmlToJsonConversionHelper, Map source, String name){
        if( hasChildSingular(xmlToJsonConversionHelper, source, name) != null ){
            return true;
        }else{
            List<String> mapKeys = getKeysOfType(source, Map.class);
            List<String> listKeys = getKeysOfType(source, List.class);
            for (String mapKey : mapKeys) {
                if( hasPluralRemaining(xmlToJsonConversionHelper, (Map) source.get(mapKey), mapKey) )
                    return true;
            }
            for (String listKey : listKeys) {
                if (((List) source.get(listKey)).get(0) instanceof Map) {
                    List<Map> mapsList = (List) source.get(listKey);
                    for (Map map : mapsList) {
                        if( hasPluralRemaining(xmlToJsonConversionHelper, map, null) )
                            return true;
                    }
                }
            }
            return false;
        }
    }//end hasPluralRemaining()

    /**
     * Determines if the given Map, which would have the given name as a key in a parent Map, contains a singular
     * form of Key as one of it's children.  So, "Terms" would be given as name, and we would be search map for a key "Term".
     * If we found it, we would return "Term".  For example:
     *  {
     *      "Hats" : {
     *          "Hat": ...
     *      }
     *  }
     */
    private static String hasChildSingular(XmlToJsonConversionHelper xmlToJsonConversionHelper, Map map, String name){
        if( name == null )
            return null;
        Set<String> keys = map.keySet();
        for( String key : keys ){
            if( xmlToJsonConversionHelper.isPlural(name, key) ){
                return key;
            }
        }
        return null;
    }

    /**
     * Recursively builds Maps and appends them to the currentMap.
     */
    private static void _toJsonRecurseHelper(XmlToJsonConversionHelper xmlToJsonConversionHelper, Element element, Map currentMap) {
        _applyAttributesToMap(xmlToJsonConversionHelper, element, currentMap);
        _handleElement(xmlToJsonConversionHelper, element, currentMap);
    }//end _toJsonRecurseHelper()

    /**
     * Does the nasty work of handling an Element.
     */
    private static void _handleElement(XmlToJsonConversionHelper xmlToJsonConversionHelper, Element element, Map map) {

        List<Element> childElements = element.elements();
        for( Element rootChild : childElements ){
            if( xmlToJsonConversionHelper.shouldHandle(rootChild) ) {
                if( rootChild.elements().isEmpty() && !isRefNode(rootChild) && !hasRelevantAttributes(rootChild)){
                    xmlToJsonConversionHelper.setValueInMap(map, xmlToJsonConversionHelper.getType(rootChild), rootChild.getName(), (String) rootChild.selectObject("string(.)"));
                }else{
                    HashMap childMap = new HashMap();
                    if( rootChild.elements().isEmpty() ){
//                        log.debug("Found value entry with attributes("+rootChild.attributeCount()+"): {"+rootChild.getNamespaceURI()+"}:"+rootChild.getName());
                        _applyAttributesToMap(xmlToJsonConversionHelper, rootChild, childMap);
                        xmlToJsonConversionHelper.setValueInMap(childMap, xmlToJsonConversionHelper.getType(rootChild), "value", (String) rootChild.selectObject("string(.)"));
                    }else{
                        _toJsonRecurseHelper(xmlToJsonConversionHelper, rootChild, childMap);
                    }

                    if( map.containsKey(rootChild.getName()) ){
                        List list = null;
                        if( map.get(rootChild.getName()) instanceof List ){
                            list = (List) map.get(rootChild.getName());
                        }else{
                            list = new ArrayList();
                            list.add(map.get(rootChild.getName()));
                        }
                        list.add(childMap);
                        map.put(rootChild.getName(), list);
                    }else{
                        map.put(rootChild.getName(), childMap);
                    }
                }
            }else{
                log.debug("Skipping element: "+rootChild.getName());
            }
        }
    }

    /**
     * Given a map representing the element in JSON, this method will append all of the Attributes from the
     * element to the map.  If the element is a Reference node (ie, it has xsi:nil = true and an tf:ref attribute)
     * then instead of adding the attributes a new $ref = #... is added and the process exits.  For example, this:
     * &lt;tf:Source tf:ref="sourceId" xsi:nil="true" /&gt;
     * Would be converted to this:
     * { "$ref" : "#sourceId" }
     */
    private static void _applyAttributesToMap(XmlToJsonConversionHelper xmlToJsonConversionHelper, Element element, Map map){
        if( element.attributeCount() > 0 ){
            if( isRefNode(element) ){
                map.put("$ref", "#"+element.attributeValue("ref"));
                return;
            }

//            log.debug("Setting attributes("+element.attributeCount()+") for element {"+element.getNamespaceURI()+"}:"+element.getName());
            for( int i = 0; i < element.attributeCount(); i++ ){
                Attribute attribute = element.attribute(i);
                if( isExcludedAttributeName(attribute) ){
                    continue;
                }
                if( map.containsKey(attribute.getName()) ) {
                    // XML Allows for multiple attributes with same name, go figure.
                    // TODO We can handle this more gracefully if it is a problem.
                    log.error("Encountered attribute with duplicate name: "+attribute.getName());
                    throw new RuntimeException("Encountered attribute with duplicate name: "+attribute.getName());
                }else{
                    xmlToJsonConversionHelper.setValueInMap(map, xmlToJsonConversionHelper.getType(attribute), "$"+attribute.getName(), attribute.getValue());
                }
            }
        }else{
//            log.debug("No attributes("+element.attributeCount()+") for element {"+element.getNamespaceURI()+"}:"+element.getName());
        }
    }

    /**
     * Returns true if the given element (which we know has no child elements already) has any relevant attributes.
     */
    private static boolean hasRelevantAttributes(Element element){
        return element.attributeCount() > 0;
    }


    /**
     * Returns true iff the given Element is a supported root element in the Trustmark Framework 1.1 spec.
     */
    private static boolean isOneOfSupportedRootTypes(Element element){
        return isTmfNS(element) && (
                (element.getName().equals("TrustmarkDefinition")) ||
                (element.getName().equals("Trustmark")) ||
                (element.getName().equals("TrustInteroperabilityProfile")) ||
                (element.getName().equals("TrustmarkStatusReport"))
        );
    }

    /**
     * Returns true if the given element has a namespace corresponding to the TMF 1.1 Namespace.
     */
    private static boolean isTmfNS(Element element){
        return element.getNamespace().getURI().equals(TrustmarkFrameworkConstants.NAMESPACE_URI);
    }

    /**
     * Returns true if the attribute should be excluded from being copied over from the source to the JSON.
     */
    private static boolean isExcludedAttributeName(Attribute attribute){
        return attribute.getName().equals("schemaLocation") ||
                attribute.getName().equals("nil");
    }

    /**
     * Returns true if the given element has an attribute with the value given.
     */
    private static boolean hasAttributeEqualTo(Element e, String attributeName, String attributeValue){
        boolean has = false;
        for( int i = 0; i < e.attributeCount(); i++ ){
            String value = e.attribute(i).getValue().trim();
            if( e.attribute(i).getName().equals(attributeName) && value.equalsIgnoreCase(attributeValue) ){
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * Returns true if the given element has the attribute listed, regardless of the value.
     */
    private static boolean hasAttribute(Element e, String attributeName){
        boolean has = false;
        for( int i = 0; i < e.attributeCount(); i++ ){
            if( e.attribute(i).getName().equals(attributeName) ){
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * Returns true if the given element is simply a reference to something else in the document (ie, it has xsi:nil = true
     * and a tf:ref attribute).
     */
    private static boolean isRefNode(Element element){
        return hasAttributeEqualTo(element, "nil", "true") && hasAttribute(element, "ref");
    }

}/* end XmlToJsonHelper */