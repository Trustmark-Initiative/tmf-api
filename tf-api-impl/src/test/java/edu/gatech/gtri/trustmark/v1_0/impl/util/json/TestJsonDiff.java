package edu.gatech.gtri.trustmark.v1_0.impl.util.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.AbstractJsonDiff;
import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.RootNodeJsonDiffField;
import edu.gatech.gtri.trustmark.v1_0.util.diff.DiffSeverity;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResult;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffResultCollection;
import edu.gatech.gtri.trustmark.v1_0.util.diff.json.JsonDiffType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import edu.gatech.gtri.trustmark.v1_0.impl.util.diff.json.field.JsonDiffField;

/**
 * Created by Nicholas on 02/13/2017.
 */
public class TestJsonDiff extends AbstractTest {
    
    
    
    //==================================================================================================================
    //  Tests
    //==================================================================================================================
    @Test
    public void test_diffValueExact() throws Exception {
        JSONObject expected = obj(
            prop("exactField1", "valueA"),
            prop("exactField2", "valueB")
        );
        
        JSONObject actual = obj(
            prop("exactField1", "valueB"),
            prop("exactField2", "valueB")
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueExact("exactField1"),
            JsonDiffField.valueExact("exactField2")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffValueMinor() throws Exception {
        JSONObject expected = obj(
            prop("minorField1", "valueA"),
            prop("minorField2", "valueB")
        );
        
        JSONObject actual = obj(
            prop("minorField1", "valueX"),
            prop("minorField2", "valueB")
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueMinor("minorField1"),
            JsonDiffField.valueMinor("minorField2")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffValueDistance() throws Exception {
        JSONObject expected = obj(
            prop("distanceField1", "valueA"),
            prop("distanceField2", "valueB"),
            prop("distanceField3", "valueC")
        );
        
        JSONObject actual = obj(
            prop("distanceField1", "anotherValueX"),
            prop("distanceField2", "valueY"),
            prop("distanceField3", "valueC")
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueDistance("distanceField1"),
            JsonDiffField.valueDistance("distanceField2"),
            JsonDiffField.valueDistance("distanceField3")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffNestedObjectValues() throws Exception {
        JSONObject expected = obj(
            prop("nestedParent1", obj(
                prop("nestedField1a", "valueA"),
                prop("nestedField1b", "valueB")
            )),
            prop("nestedParent2", obj(
                prop("nestedField2c", "valueC"),
                prop("nestedField2d", "valueD")
            )),
            prop("nestedParent3", obj(
                prop("nestedParent3e", obj(
                    prop("nestedField3e1", "valueE1"),
                    prop("nestedField3e2", "valueE2")
                ))
            ))
        );
        
        JSONObject actual = obj(
            prop("nestedParent1", obj(
                prop("nestedField1a", "valueA"),
                prop("nestedField1b", "valueY")
            )),
            prop("nestedParent2", obj(
                prop("nestedField2c", "valueC"),
                prop("nestedField2d", "valueW")
            )),
            prop("nestedParent3", obj(
                prop("nestedParent3e", obj(
                    prop("nestedField3e1", "valueE1"),
                    prop("nestedField3e2", "valueEX")
                ))
            ))
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueExact("nestedParent1.nestedField1a"),
            JsonDiffField.valueExact("nestedParent1.nestedField1b"),
            JsonDiffField.valueExact("nestedParent2.nestedField2c"),
            JsonDiffField.valueExact("nestedParent2.nestedField2d"),
            JsonDiffField.valueExact("nestedParent3.nestedParent3e.nestedField3e1"),
            JsonDiffField.valueExact("nestedParent3.nestedParent3e.nestedField3e2")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffNestedObjectValuesWithNotComparedFields() throws Exception {
        JSONObject expected = obj(
            prop("nestedParentWithNC1", obj(
                prop("nestedFieldWithNC1a", "valueA"),
                prop("nestedFieldWithNC1b", "valueB"),
                prop("nestedFieldWithNC1c1", "valueC1"),
                prop("nestedFieldWithNC1c2", "valueC2a")
            )),
            prop("nestedParentWithNC2", obj(
                prop("nestedFieldWithNC2d", "valueD"),
                prop("nestedFieldWithNC2e", "valueE"),
                prop("nestedFieldWithNC2f1", "valueF1")
            )),
            prop("nestedParentWithNC3", obj(
                prop("nestedParentWithNC3g", obj(
                    prop("nestedFieldWithNC3g1", "valueG1"),
                    prop("nestedFieldWithNC3g2", "valueG2"),
                    prop("nestedFieldWithNC3g3", "valueG3")
                )),
                prop("nestedFieldWithNC3h", "valueH")
            )),
            prop("fieldWithNC4", "valueNC4")
        );
        
        JSONObject actual = obj(
            prop("nestedParentWithNC1", obj(
                prop("nestedFieldWithNC1a", "valueA"),
                prop("nestedFieldWithNC1b", "valueY"),
                prop("nestedFieldWithNC1c1", "valueC1"),
                prop("nestedFieldWithNC1c2", "valueC2b")
            )),
            prop("nestedParentWithNC2", obj(
                prop("nestedFieldWithNC2d", "valueD"),
                prop("nestedFieldWithNC2e", "valueV"),
                prop("nestedFieldWithNC2f2", "valueF2")
            )),
            prop("nestedParentWithNC3", obj(
                prop("nestedParentWithNC3g", obj(
                    prop("nestedFieldWithNC3g1", "valueG1"),
                    prop("nestedFieldWithNC3g2", "valueX2"),
                    prop("nestedFieldWithNC3g3", "valueG3")
                )),
                prop("nestedFieldWithNC3h", "valueH")
            )),
            prop("fieldWithNC4", "valueNC4")
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.valueExact("nestedParentWithNC1.nestedFieldWithNC1a"),
            JsonDiffField.valueExact("nestedParentWithNC1.nestedFieldWithNC1b"),
            JsonDiffField.valueExact("nestedParentWithNC2.nestedFieldWithNC2d"),
            JsonDiffField.valueExact("nestedParentWithNC2.nestedFieldWithNC2e"),
            JsonDiffField.valueExact("nestedParentWithNC3.nestedParentWithNC3g.nestedFieldWithNC3g1"),
            JsonDiffField.valueExact("nestedParentWithNC3.nestedParentWithNC3g.nestedFieldWithNC3g2")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 7);
    }
    
    @Test
    public void test_diffSimpleCollection() throws Exception {
        JSONObject expected = obj(
            prop("simpleCollection1", arr("A", "B", "C")),
            prop("simpleCollection2", arr("X", "Y")),
            prop("simpleCollection3", arr("P", "Q", "S")),
            prop("simpleCollection4", arr("1", "2", "3")),
            prop("simpleCollection5", arr("4", "5", "6"))
        );
        
        JSONObject actual = obj(
            prop("simpleCollection1", arr("A", "B")),
            prop("simpleCollection2", arr("X", "Y", "Z")),
            prop("simpleCollection3", arr("P", "Q", "R")),
            prop("simpleCollection4", arr("3", "1", "2")),
            prop("simpleCollection5", arr("4", "5", "6"))
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.exactSimpleCollection("simpleCollection1"),
            JsonDiffField.exactSimpleCollection("simpleCollection2"),
            JsonDiffField.exactSimpleCollection("simpleCollection3"),
            JsonDiffField.exactSimpleCollection("simpleCollection4"),
            JsonDiffField.exactSimpleCollection("simpleCollection5")
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }

    @Test
    public void test_diffFieldsCollectionWithoutChildren() throws Exception {
        JSONObject expected = obj(
            prop("fieldsCollection1", arr(
                obj(prop("match1", "A"), prop("match2", "B")),
                obj(prop("match1", "C"), prop("match2", "D"))
            )),
            prop("fieldsCollection2", arr(
                obj(prop("match1", "E"), prop("match2", "F"))
            )),
            prop("fieldsCollection3", arr(
                obj(prop("match1", "P"), prop("match2", "Q")),
                obj(prop("match1", "R"), prop("match2", "S1"))
            )),
            prop("fieldsCollection4", arr(
                obj(prop("match1", "1"), prop("match2", "2")),
                obj(prop("match1", "3"), prop("match2", "4"))
            )),
            prop("fieldsCollection5", arr(
                obj(prop("match1", "5"), prop("match2", "5")),
                obj(prop("match1", "7"), prop("match2", "8"))
            ))
        );
        
        JSONObject actual = obj(
            prop("fieldsCollection1", arr(
                obj(prop("match1", "A"), prop("match2", "B"))
            )),
            prop("fieldsCollection2", arr(
                obj(prop("match1", "E"), prop("match2", "F")),
                obj(prop("match1", "G"), prop("match2", "H"))
            )),
            prop("fieldsCollection3", arr(
                obj(prop("match1", "P"), prop("match2", "Q")),
                obj(prop("match1", "R"), prop("match2", "S2"))
            )),
            prop("fieldsCollection4", arr(
                obj(prop("match1", "3"), prop("match2", "4")),
                obj(prop("match1", "1"), prop("match2", "2"))
            )),
            prop("fieldsCollection5", arr(
                obj(prop("match1", "5"), prop("match2", "5")),
                obj(prop("match1", "7"), prop("match2", "8"))
            ))
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.fieldsCollection("fieldsCollection1", "match1", "match2").withoutChildren(),
            JsonDiffField.fieldsCollection("fieldsCollection2", "match1", "match2").withoutChildren(),
            JsonDiffField.fieldsCollection("fieldsCollection3", "match1", "match2").withoutChildren(),
            JsonDiffField.fieldsCollection("fieldsCollection4", "match1", "match2").withoutChildren(),
            JsonDiffField.fieldsCollection("fieldsCollection5", "match1", "match2").withoutChildren()
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }

    @Test
    public void test_diffFieldsCollectionWithChildren() throws Exception {
        JSONObject expected = obj(
            prop("fieldsCollWithChildren1", arr(
                obj(prop("match1", "A"), prop("match2", "B"), prop("field1", "F01")),
                obj(prop("match1", "C"), prop("match2", "D"), prop("field1", "F02")),
                obj(prop("match1", "X"), prop("match2", "Y"), prop("field1", "F03"))
            )),
            prop("fieldsCollWithChildren2", arr(
                obj(prop("match1", "E"), prop("match2", "F"), prop("field1", "F04")),
                obj(prop("match1", "IJ"), prop("match2", "KL"), prop("field1", "F06"))
            )),
            prop("fieldsCollWithChildren3", arr(
                obj(prop("match1", "P"), prop("match2", "Q"), prop("field1", "F07")),
                obj(prop("match1", "R"), prop("match2", "S1"), prop("field1", "F08")),
                obj(prop("match1", "T"), prop("match2", "U"), prop("field1", "F09"))
            )),
            prop("fieldsCollWithChildren4", arr(
                obj(prop("match1", "1"), prop("match2", "2"), prop("field1", "F10")),
                obj(prop("match1", "3"), prop("match2", "4"), prop("field1", "F11")),
                obj(prop("match1", "5"), prop("match2", "6"), prop("field1", "F12"))
            )),
            prop("fieldsCollWithChildren5", arr(
                obj(prop("match1", "7"), prop("match2", "8"), prop("field1", "F13")),
                obj(prop("match1", "9"), prop("match2", "10"), prop("field1", "F14")),
                obj(prop("match1", "11"), prop("match2", "12"), prop("field1", "F15"))
            ))
        );
    
        JSONObject actual = obj(
            prop("fieldsCollWithChildren1", arr(
                obj(prop("match1", "A"), prop("match2", "B"), prop("field1", "F01")),
                obj(prop("match1", "X"), prop("match2", "Y"), prop("field1", "Z03"))
            )),
            prop("fieldsCollWithChildren2", arr(
                obj(prop("match1", "E"), prop("match2", "F"), prop("field1", "F04")),
                obj(prop("match1", "G"), prop("match2", "H"), prop("field1", "F05")),
                obj(prop("match1", "IJ"), prop("match2", "KL"), prop("field1", "Z06"))
            )),
            prop("fieldsCollWithChildren3", arr(
                obj(prop("match1", "P"), prop("match2", "Q"), prop("field1", "F07")),
                obj(prop("match1", "R"), prop("match2", "S2"), prop("field1", "F08")),
                obj(prop("match1", "T"), prop("match2", "U"), prop("field1", "Z09"))
            )),
            prop("fieldsCollWithChildren4", arr(
                obj(prop("match1", "5"), prop("match2", "6"), prop("field1", "F12")),
                obj(prop("match1", "1"), prop("match2", "2"), prop("field1", "F10")),
                obj(prop("match1", "3"), prop("match2", "4"), prop("field1", "F11"))
            )),
            prop("fieldsCollWithChildren5", arr(
                obj(prop("match1", "7"), prop("match2", "8"), prop("field1", "F13")),
                obj(prop("match1", "9"), prop("match2", "10"), prop("field1", "F14")),
                obj(prop("match1", "11"), prop("match2", "12"), prop("field1", "F15"))
            ))
        );
    
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.fieldsCollection("fieldsCollWithChildren1", "match1", "match2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.fieldsCollection("fieldsCollWithChildren2", "match1", "match2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.fieldsCollection("fieldsCollWithChildren3", "match1", "match2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.fieldsCollection("fieldsCollWithChildren4", "match1", "match2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.fieldsCollection("fieldsCollWithChildren5", "match1", "match2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            )
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 2);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffIdCollectionWithoutChildren() throws Exception {
        JSONObject expected = obj(
            prop("idCollection1", arr(
                obj(prop("id", "A")),
                obj(prop("id", "B"))
            )),
            prop("idCollection2", arr(
                obj(prop("id", "C"))
            )),
            prop("idCollection3", arr(
                obj(prop("id", "P")),
                obj(prop("id", "Q1"))
            )),
            prop("idCollection4", arr(
                obj(prop("id", "1")),
                obj(prop("id", "2"))
            )),
            prop("idCollection5", arr(
                obj(prop("id", "3")),
                obj(prop("id", "4"))
            )),
            prop("idCollection6", arr(
                obj(prop("$ref", "#2")),
                obj(prop("$ref", "#4"))
            )),
            prop("idCollection7", arr(
                obj(prop("id", "5")),
                obj(prop("id", "6a")),
                obj(prop("id", "7"))
            )),
            prop("idCollection8", arr(
                obj(prop("$ref", "#6a")),
                obj(prop("$ref", "#7")),
                obj(prop("id", "8"))
            ))
        );
        
        JSONObject actual = obj(
            prop("idCollection1", arr(
                obj(prop("id", "A"))
            )),
            prop("idCollection2", arr(
                obj(prop("id", "C")),
                obj(prop("id", "D"))
            )),
            prop("idCollection3", arr(
                obj(prop("id", "P")),
                obj(prop("id", "Q2"))
            )),
            prop("idCollection4", arr(
                obj(prop("id", "2")),
                obj(prop("id", "1"))
            )),
            prop("idCollection5", arr(
                obj(prop("id", "3")),
                obj(prop("id", "4"))
            )),
            prop("idCollection6", arr(
                obj(prop("$ref", "#2")),
                obj(prop("$ref", "#4"))
            )),
            prop("idCollection7", arr(
                obj(prop("id", "5")),
                obj(prop("id", "6b")),
                obj(prop("id", "7"))
            )),
            prop("idCollection8", arr(
                obj(prop("id", "6a")),
                obj(prop("$ref", "#7")),
                obj(prop("id", "8"))
            ))
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.idCollection("idCollection1", "idChild1").withoutChildren(),
            JsonDiffField.idCollection("idCollection2", "idChild2").withoutChildren(),
            JsonDiffField.idCollection("idCollection3", "idChild3").withoutChildren(),
            JsonDiffField.idCollection("idCollection4", "idChild4Plus").withoutChildren(),
            JsonDiffField.idCollection("idCollection5", "idChild4Plus").withoutChildren(),
            JsonDiffField.idCollection("idCollection6", "idChild4Plus").withoutChildren(),
            JsonDiffField.idCollection("idCollection7", "idChild4Plus").withoutChildren(),
            JsonDiffField.idCollection("idCollection8", "idChild4Plus").withoutChildren()
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }

    @Test
    public void test_diffIdCollectionWithChildren() throws Exception {
        JSONObject expected = obj(
            prop("idCollWithChildren1", arr(
                obj(prop("id", "A"), prop("field1", "F01")),
                obj(prop("id", "B"), prop("field1", "F02")),
                obj(prop("id", "C"), prop("field1", "F03"))
            )),
            prop("idCollWithChildren2", arr(
                obj(prop("id", "D"), prop("field1", "F04")),
                obj(prop("id", "F"), prop("field1", "F06"))
            )),
            prop("idCollWithChildren3", arr(
                obj(prop("id", "P"), prop("field1", "F07")),
                obj(prop("id", "Q1"), prop("field1", "F08")),
                obj(prop("id", "R"), prop("field1", "F09"))
            )),
            prop("idCollWithChildren4", arr(
                obj(prop("id", "1"), prop("field1", "F10")),
                obj(prop("id", "2"), prop("field1", "F11")),
                obj(prop("id", "3"), prop("field1", "F12"))
            )),
            prop("idCollWithChildren5", arr(
                obj(prop("id", "4"), prop("field1", "F13")),
                obj(prop("id", "5"), prop("field1", "F14")),
                obj(prop("id", "6"), prop("field1", "F15"))
            )),
            prop("idCollWithChildren6", arr(
                obj(prop("$ref", "#2")),
                obj(prop("$ref", "#4")),
                obj(prop("$ref", "#6"))
            )),
            prop("idCollWithChildren7", arr(
                obj(prop("id", "7")),
                obj(prop("id", "8a")),
                obj(prop("id", "9"))
            )),
            prop("idCollWithChildren8", arr(
                obj(prop("$ref", "#8a")),
                obj(prop("$ref", "#9")),
                obj(prop("id", "10"))
            ))
        );
        
        JSONObject actual = obj(
            prop("idCollWithChildren1", arr(
                obj(prop("id", "A"), prop("field1", "F01")),
                obj(prop("id", "C"), prop("field1", "Z03"))
            )),
            prop("idCollWithChildren2", arr(
                obj(prop("id", "D"), prop("field1", "F04")),
                obj(prop("id", "E"), prop("field1", "F05")),
                obj(prop("id", "F"), prop("field1", "Z06"))
            )),
            prop("idCollWithChildren3", arr(
                obj(prop("id", "P"), prop("field1", "F07")),
                obj(prop("id", "Q2"), prop("field1", "F08")),
                obj(prop("id", "R"), prop("field1", "Z09"))
            )),
            prop("idCollWithChildren4", arr(
                obj(prop("id", "3"), prop("field1", "F12")),
                obj(prop("id", "1"), prop("field1", "F10")),
                obj(prop("id", "2"), prop("field1", "F11"))
            )),
            prop("idCollWithChildren5", arr(
                obj(prop("id", "4"), prop("field1", "F13")),
                obj(prop("id", "5"), prop("field1", "F14")),
                obj(prop("id", "6"), prop("field1", "F15"))
            )),
            prop("idCollWithChildren6", arr(
                obj(prop("$ref", "#2")),
                obj(prop("$ref", "#4")),
                obj(prop("$ref", "#6"))
            )),
            prop("idCollWithChildren7", arr(
                obj(prop("id", "7")),
                obj(prop("id", "8b")),
                obj(prop("id", "9"))
            )),
            prop("idCollWithChildren8", arr(
                obj(prop("id", "8a")),
                obj(prop("$ref", "#9")),
                obj(prop("id", "10"))
            ))
        );
        
        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.idCollection("idCollWithChildren1", "idChild1").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren2", "idChild2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren3", "idChild3").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren4", "idChild4Plus").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren5", "idChild4Plus").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren6", "idChild4Plus").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren7", "idChild4Plus").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            ),
            JsonDiffField.idCollection("idCollWithChildren8", "idChild4Plus").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1")
            )
        ));
        
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 3);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    @Test
    public void test_diffRecursiveCollection() throws Exception {
        JSONObject expected = obj(
            prop("parentIdCollection1", arr(
                obj(
                    prop("id", "A."),
                    prop("field1", "F-A"),
                    prop("recursiveCollection1", arr(
                        obj(
                            prop("id", "A.1."),
                            prop("field1", "F-A1"),
                            prop("recursiveCollection1", arr())
                        ),
                        obj(
                            prop("id", "A.2a."),
                            prop("field1", "F-A2a"),
                            prop("recursiveCollection1", arr())
                        ),
                        obj(
                            prop("id", "A.3."),
                            prop("field1", "F-A3"),
                            prop("recursiveCollection1", arr())
                        )
                    ))
                ),
                obj(
                    prop("id", "B."),
                    prop("recursiveCollection1", arr(
                        obj(
                            prop("$ref", "#A.2a.")
                        ),
                        obj(
                            prop("$ref", "#A.3.")
                        ),
                        obj(
                            prop("id", "B.1."),
                            prop("field1", "F-B1"),
                            prop("recursiveCollection1", arr())
                        )
                    ))
                )
            )),
            prop("parentIdCollection2", arr(
                obj(
                    prop("id", "C."),
                    prop("recursiveCollection2", arr(
                        obj(
                            prop("$ref", "#C.")
                        )
                    ))
                )
            ))
        );

        JSONObject actual = obj(
            prop("parentIdCollection1", arr(
                obj(
                    prop("id", "A."),
                    prop("field1", "F-A"),
                    prop("recursiveCollection1", arr(
                        obj(
                            prop("id", "A.1."),
                            prop("field1", "F-A1"),
                            prop("recursiveCollection1", arr())
                        ),
                        obj(
                            prop("id", "A.2b."),
                            prop("field1", "F-A2b"),
                            prop("recursiveCollection1", arr())
                        ),
                        obj(
                            prop("id", "A.3."),
                            prop("field1", "F-A3"),
                            prop("recursiveCollection1", arr())
                        )
                    ))
                ),
                obj(
                    prop("id", "B."),
                    prop("recursiveCollection1", arr(
                        obj(
                            prop("id", "A.2a."),
                            prop("field1", "F-A2a"),
                            prop("recursiveCollection1", arr())
                        ),
                        obj(
                            prop("$ref", "#A.3.")
                        ),
                        obj(
                            prop("id", "B.1."),
                            prop("field1", "F-B1"),
                            prop("recursiveCollection1", arr())
                        )
                    ))
                )
            )),
            prop("parentIdCollection2", arr(
                obj(
                    prop("id", "C."),
                    prop("recursiveCollection2", arr(
                        obj(
                            prop("$ref", "#C.")
                        )
                    ))
                )
            ))
        );

        JsonDiffResultCollection resultCollection = this.getJsonDiffResults(expected, actual, JsonDiffField.rootNode(
            DiffSeverity.MAJOR,
            JsonDiffField.idCollection("parentIdCollection1", "idChild1").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.valueExact("field1"),
                JsonDiffField.recursiveCollection("recursiveCollection1")
            ),
            JsonDiffField.idCollection("parentIdCollection2", "idChild2").withChildren(
                DiffSeverity.MAJOR,
                JsonDiffField.recursiveCollection("recursiveCollection2")
            )
        ));

        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MINOR, JsonDiffType.MISMATCHED, 0);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.MISSING, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.UNEXPECTED, 1);
        this.assertJsonDiffCount(resultCollection, DiffSeverity.MAJOR, JsonDiffType.NOT_COMPARED, 0);
    }
    
    
    //==================================================================================================================
    //  Test Helper Methods
    //==================================================================================================================
    
    private static JSONArray arr(Object... items) {
        return new JSONArray(items);
    }
    
    private static class JsonProp extends AbstractMap.SimpleEntry<String, Object> {
        public JsonProp(String k, Object v) { super(k, v); }
    }
    
    private static JsonProp prop(String k, Object v) {
        return new JsonProp(k, v);
    }
    
    private static JSONObject obj(JsonProp... props) {
        JSONObject result = new JSONObject();
        for (JsonProp prop : props) {
            result.put(prop.getKey(), prop.getValue());
        }
        return result;
    }
    
    private JsonDiffResultCollection getJsonDiffResults(
        JSONObject expected,
        JSONObject actual,
        RootNodeJsonDiffField rootNodeJsonDiffField
    ) {
        JsonDiffResultCollection resultCollection = AbstractJsonDiff.doJsonDiff(
            "expected",
            expected,
            "actual",
            actual,
            rootNodeJsonDiffField
        );
        assertThat(resultCollection, notNullValue());
        
        Collection<JsonDiffResult> majorDiffIssues = resultCollection.getResultsForSeverity(DiffSeverity.MAJOR);
        assertThat(majorDiffIssues, notNullValue());
        
        Collection<JsonDiffResult> minorDiffIssues = resultCollection.getResultsForSeverity(DiffSeverity.MINOR);
        assertThat(minorDiffIssues, notNullValue());
        
        for (JsonDiffResult diffResult : majorDiffIssues) {
            logger.warn(diffResult.getDescription());
        }
        
        for (JsonDiffResult diffResult : minorDiffIssues) {
            logger.debug(diffResult.getDescription());
        }
        return resultCollection;
    }
    
}
