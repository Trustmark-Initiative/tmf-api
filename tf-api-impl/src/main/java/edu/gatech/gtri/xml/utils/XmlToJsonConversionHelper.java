package edu.gatech.gtri.xml.utils;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An interface for providing data to the Xml To Json conversion process.  {@see XmlToJsonHelper}.
 * <br/><br/>
 * @author brad
 * @date 10/10/16
 */
public interface XmlToJsonConversionHelper {

    /**
     * Returns true if the given element should be converted to JSON, false otherwise.  If false, then the given element
     * will be ignored by the conversion.
     * <br/><br/>
     * @param e
     * @return
     */
    public boolean shouldHandle(Element e);

    /**
     * Given a word, this method will return the "plural" form of that word.  This is used if you get this structure:
     * {
     *     "Hats" : {
     *         "Hat" : ...
     *     }
     * }
     *
     * Given "Hat", this method should return "Hats".
     */
    public String toPluralValue( String singularTerm );


    /**
     * Return true if the parent name is the plural form of the child's name.  For example:
     *   isPlural("Hats", "Hat") => TRUE
     *   isPlural("Hat", "Hats") => FALSE
     *   isPlural("ConformanceCriteria", "ConformanceCriterion") => TRUE
     *   ... Etc ...
     *
     *   The purpose is to allow you to customize the plural words used in your XML that needs to be "Fixed" in your
     *   resulting JSON values.
     *
     *   Note that the parent name may be null, indicating there is no parent (false is expected to be returned in this
     *   instance).
     */
    public Boolean isPlural(String parentName, String childName);

    /**
     * After the JSON map has been initially created, there will be some List/Object mismatches.  For example:
     * [{
     *   "Hat": {}
     * },
     * {
     *   "Hat": [{...}, {...}]
     * }]
     *
     * This method will return a list of paths in the JSON which should <b>ALWAYS</b> be converted to a list and made
     * plural. So in the above example, the conversion should be:
     * [{
     *   "Hats": [{}]
     * },
     * {
     *   "Hats": [{...}, {...}]
     * }]
     * <br/>
     * The return value is a Set of Lists of Strings.  Each List of Strings represents a path through the JSON to identify
     * a particular element which should be made plural if it exists.  For example:
     *   "ConformanceCriteria", "Citation"
     *   "AssessmentSteps", "Artifact"
     *   "AssessmentSteps", "ConformanceCriterion"
     *
     * Note the method in this interface (toPluralValue) will be used to make the value plural.
     */
    public Set<List<String>> getShouldBePluralsSet();

    /**
     * Given an Element from the source XML, can determine the corresponding java type for it.
     */
    public Class getType(Element element);

    /**
     * Given an Attribute from the source XML, can determine the corresponding java type for it.
     */
    public Class getType(Attribute attribute);

    /**
     * Called when we need to set a value in the map.  This method allows the implementer to determine how types are
     * mapped.  Dont' forget, String is the default type.
     */
    public void setValueInMap(Map map, Class type, String key, String valueAsString);

}/* end XmlToJsonConversionHelper */
