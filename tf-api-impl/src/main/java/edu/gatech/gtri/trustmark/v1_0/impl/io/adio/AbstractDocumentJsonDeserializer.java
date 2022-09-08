package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.ATTRIBUTE_KEY_JSON_ID;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.ATTRIBUTE_KEY_JSON_REF;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.ATTRIBUTE_VALUE_JSON_REF_PREFIX;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readEntity;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readTerm;

/**
 * Created by Nicholas on 02/01/2017.
 */
public class AbstractDocumentJsonDeserializer<T> extends AbstractDocumentInputDeserializer<T, JSONObject, JSONArray, Object> {

    ////// Constructor //////

    public AbstractDocumentJsonDeserializer(Codec<T> _codec) {
        super(_codec);
    }

    ////// Instance Methods //////

    @Override
    protected <C> AbstractDocumentInputDeserializer<C, JSONObject, JSONArray, Object> getAnalogousDeserializer(Codec<C> otherCodec) {
        return otherCodec.jsonDeserializer;
    }

    @Override
    protected String getSourceType() {
        return MediaType.APPLICATION_JSON.getMediaType();
    }

    @Override
    protected Map.Entry<IdRefType, String> getReferenceInfoForObjectNode(JSONObject rootNode) throws
            AbstractDocumentDeserializationException {
        return getReferenceInfoForJsonObject(rootNode);
    }

    public static Map.Entry<IdRefType, String> getReferenceInfoForJsonObject(JSONObject rootNode) throws
            AbstractDocumentDeserializationException {
        IdRefType idRefType = IdRefType.NONE;
        String idRef = null;
        String idValue = rootNode.optString(ATTRIBUTE_KEY_JSON_ID, null);
        String refValue = rootNode.optString(ATTRIBUTE_KEY_JSON_REF, null);
        if (idValue != null) {
            idRefType = IdRefType.ID;
            idRef = idValue;
        } else if (refValue != null) {
            if (!refValue.startsWith(ATTRIBUTE_VALUE_JSON_REF_PREFIX)) {
                String message = String.format(
                        "JSON object reference value must start with '%s'. Found '%s' instead.",
                        ATTRIBUTE_VALUE_JSON_REF_PREFIX,
                        refValue
                );
                throw new AbstractDocumentDeserializationException(message);
            }
            idRefType = IdRefType.REF;
            idRef = refValue.replaceFirst(ATTRIBUTE_VALUE_JSON_REF_PREFIX, "");
        }
        return new AbstractMap.SimpleEntry<>(idRefType, idRef);
    }

    @Override
    protected List<Object> getChildNodesInSequenceNode(JSONArray sequenceNode)
            throws AbstractDocumentDeserializationException {
        List<Object> children = new ArrayList<>();
        for (Object child : sequenceNode) {
            children.add(child);
        }
        return children;
    }

    @Override
    protected String getStringFromValueNode(Object valueNode) throws AbstractDocumentDeserializationException {
        return JSONObject.NULL.equals(valueNode) ? null : valueNode.toString();
    }

    @Override
    protected boolean isObjectNode(Object node) {
        return node instanceof JSONObject;
    }

    @Override
    protected boolean isSequenceNode(Object node) {
        return node instanceof JSONArray;
    }

    @Override
    protected boolean isValueNode(Object node) {
        return !this.isObjectNode(node) && !this.isSequenceNode(node);
    }

    @Override
    protected List<Object> getMatchingNodesInRootObject(JSONObject rootNode, int limit, String[] pathSegments)
            throws AbstractDocumentDeserializationException {
        if (pathSegments == null) {
            pathSegments = new String[0];
        }
        String rootNodeName = this.codec.getRootElementName();
        List<String> pathSegmentList = Arrays.asList(pathSegments);
        List<Object> results = new ArrayList<>();
        this.addMatchingDescendants(results, rootNode, rootNodeName, limit, 0, pathSegmentList);
        return results;
    }

    private void addMatchingDescendants(List<Object> results, Object node, String nodeName, int limit, int segmentIndex, List<String> pathSegments) {
        if (segmentIndex >= pathSegments.size() && (limit < 0 || results.size() < limit)) {
            // here we are at the end of the search
            results.add(node);
            return;
        }

        String currentName = pathSegments.get(segmentIndex);
        if (currentName == null) {
            return;
        }

        int nextSegmentIndex = segmentIndex + 1;
        if (this.isObjectNode(node)) {
            JSONObject objectNode = (JSONObject) node;
            for (Iterator<String> i = objectNode.keys(); i.hasNext(); ) {
                String childName = i.next();
                if (currentName.equals(childName)) {
                    Object child = objectNode.get(childName);
                    if (JSONObject.NULL.equals(child)) {
                        continue;
                    }
                    this.addMatchingDescendants(results, child, childName, limit, nextSegmentIndex, pathSegments);
                }
            }
        } else if (this.isSequenceNode(node)) {
            JSONArray sequenceNode = (JSONArray) node;
            boolean isValueSequence = this.codec.isValueSequenceName(nodeName);
            String childName = isValueSequence
                    ? this.codec.getValueSequenceChildNameFor(nodeName)
                    : this.codec.getObjectSequenceChildNameFor(nodeName);
            if (currentName.equals(childName)) {
                for (Object child : sequenceNode) {
                    if (JSONObject.NULL.equals(child)) {
                        continue;
                    }
                    this.addMatchingDescendants(results, child, childName, limit, nextSegmentIndex, pathSegments);
                }
            }
        }
    }

    @Override
    protected Entity getEntityFromObjectNode(JSONObject entityNode) throws ParseException {
        return readEntity(entityNode);
    }

    @Override
    protected Term getTermFromObjectNode(JSONObject termNode) throws ParseException {
        return readTerm(termNode);
    }

    @Override
    protected TrustInteroperabilityProfile getTipFromObjectNode(JSONObject tipNode) throws ParseException {
        String tipJson = tipNode.toString();
        return new TrustInteroperabilityProfileJsonDeserializer(true).deserialize(tipJson);
    }

}
