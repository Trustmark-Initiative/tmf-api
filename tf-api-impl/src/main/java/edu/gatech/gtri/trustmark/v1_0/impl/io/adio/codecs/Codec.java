package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponent;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.*;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentXmlSerializer.NAMESPACE_TF;

/**
 * A coder/decoder that provides the capability to transform an
 * object of type T to and from various serialization formats.
 */
public abstract class Codec<T> implements TypeSpecificComponent<T> {
    
    ////// Instance Fields //////
    
    public final AbstractDocumentJsonSerializer<T> jsonSerializer = new AbstractDocumentJsonSerializer<>(this);
    public final AbstractDocumentXmlSerializer<T> xmlSerializer = new AbstractDocumentXmlSerializer<>(this);
    public final AbstractDocumentJsonDeserializer<T> jsonDeserializer = new AbstractDocumentJsonDeserializer<>(this);
    public final AbstractDocumentXmlDeserializer<T> xmlDeserializer = new AbstractDocumentXmlDeserializer<>(this);
    
    
    ////// Instance Methods - Abstract / Validation //////
    
    /**
     * Gets the type supported by this codec.
     */
    @Override
    public abstract Class<T> getSupportedType();
    
    /**
     * Gets a map from element name to qualified name for any elements that must be serialized to
     * or deserialized from any namespace that is not the default TMF namespace.
     * If there are no such values, this method may return an empty collection or simply null.
     */
    public abstract Map<String, QName> getNonTfQualifiedNamesByElementName();
    
    /**
     * Gets a map from element name to set of attribute names for any elements that have attributes.
     * If there are no such values, this method may return an empty collection or simply null.
     */
    public abstract SetValuedMap<String, String> getAttributeNamesByElementName();
    
    /**
     * Gets a map from object-sequence name to the type of the child elements that occur in that object-sequence.
     * If there are no such values, this method may return an empty collection or simply null.
     */
    public abstract BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap();
    
    /**
     * Gets a map from value-sequence name to the name of the child values that occur in that value-sequence.
     * If there are no such values, this method may return an empty collection or simply null.
     */
    public abstract BidiMap<String, String> getValueSequenceParentNameChildNameMap();
    
    /**
     * Gets the names of values which must be stored as CDATA, if any.
     * If there are no such values, this method may return an empty collection or simply null.
     */
    public abstract Set<String> getCdataValueNames();
    
    
    ////// Instance Methods - Abstract / Serialization //////
    
    /**
     * Gets the ID of an object of the supported class, if possible.
     * Otherwise, this should return null.
     */
    public abstract String getIdFor(T obj);
    
    /**
     * Serializes the document children of an object of the supporting class.
     */
    public abstract <W, Ex extends Exception> void serializeChildrenFor(
        T obj,
        AbstractDocumentOutputSerializer<T, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex;
    
    
    ////// Instance Methods - Abstract / Deserialization //////
    
    /**
     * Deserializes an object of the supported class from the root node of the input document.
     */
    public abstract <O> T deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<T, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException;
    
    
    ////// Instance Methods - Concrete / Validation //////
    
    public final QName getQualifiedName(String elementName) {
        Map<String, QName> nonTfQualifiedNamesByElementName = this.getNonTfQualifiedNamesByElementName();
        QName result = nonTfQualifiedNamesByElementName == null ? null : nonTfQualifiedNamesByElementName.get(elementName);
        if (result == null) { result = QName.get(elementName, NAMESPACE_TF); }
        return result;
    }
    
    public final boolean isValidAttributeKey(String elementName, String attributeName) {
        SetValuedMap<String, String> attributeNamesByElementName = this.getAttributeNamesByElementName();
        Set<String> attributeNames = attributeNamesByElementName == null ? null : attributeNamesByElementName.get(elementName);
        return attributeNames != null && attributeNames.contains(attributeName);
    }
    
    public final boolean isObjectSequenceName(String name) {
        BidiMap<String, Class<?>> objectSequenceParentNameChildTypeMap = this.getObjectSequenceParentNameChildTypeMap();
        return objectSequenceParentNameChildTypeMap != null && objectSequenceParentNameChildTypeMap.containsKey(name);
    }
    
    public final boolean isObjectSequenceChildType(Class<?> childType) {
        BidiMap<String, Class<?>> objectSequenceParentNameChildTypeMap = this.getObjectSequenceParentNameChildTypeMap();
        return objectSequenceParentNameChildTypeMap != null && objectSequenceParentNameChildTypeMap.containsValue(childType);
    }
    
    public final String getObjectSequenceChildNameFor(String parentName) {
        BidiMap<String, Class<?>> objectSequenceParentNameChildTypeMap = this.getObjectSequenceParentNameChildTypeMap();
        Class<?> childType = objectSequenceParentNameChildTypeMap == null ? null : objectSequenceParentNameChildTypeMap.get(parentName);
        Codec<?> childCodec = childType == null ? null : loadCodecFor(childType);
        return childCodec == null ? null : childCodec.getRootElementName();
    }
    
    public final String getObjectSequenceParentNameFor(Class<?> childType) {
        BidiMap<String, Class<?>> objectSequenceParentNameChildTypeMap = this.getObjectSequenceParentNameChildTypeMap();
        return objectSequenceParentNameChildTypeMap == null ? null : objectSequenceParentNameChildTypeMap.getKey(childType);
    }
    
    public final boolean isValueSequenceName(String name) {
        BidiMap<String, String> valueSequenceParentNameChildNameMap = this.getValueSequenceParentNameChildNameMap();
        return valueSequenceParentNameChildNameMap != null && valueSequenceParentNameChildNameMap.containsKey(name);
    }
    
    public final boolean isValueSequenceChildName(String childName) {
        BidiMap<String, String> valueSequenceParentNameChildNameMap = this.getValueSequenceParentNameChildNameMap();
        return valueSequenceParentNameChildNameMap != null && valueSequenceParentNameChildNameMap.containsValue(childName);
    }
    
    public final String getValueSequenceChildNameFor(String parentName) {
        BidiMap<String, String> valueSequenceParentNameChildNameMap = this.getValueSequenceParentNameChildNameMap();
        return valueSequenceParentNameChildNameMap == null ? null : valueSequenceParentNameChildNameMap.get(parentName);
    }
    
    public final String getValueSequenceParentNameFor(String childName) {
        BidiMap<String, String> valueSequenceParentNameChildNameMap = this.getValueSequenceParentNameChildNameMap();
        return valueSequenceParentNameChildNameMap == null ? null : valueSequenceParentNameChildNameMap.getKey(childName);
    }
    
    public final boolean isCdataValueName(String name) {
        Set<String> cdataValueNames = this.getCdataValueNames();
        return cdataValueNames != null && cdataValueNames.contains(name);
    }
    
    
    ////// Instance Methods - Concrete / Serialization //////
    
    /**
     * Gets the name of the root element.
     * The default implementation given for this method is typically sufficient.
     */
    public String getRootElementName() { return this.getSupportedType().getSimpleName(); }
    
    /**
     * Gets the attributes of an object of the supported class, if possible.
     * Otherwise, this should return null or an empty map.
     * The default implementation given for this method is typically sufficient.
     */
    public Map<String, String> getAttributesFor(T obj) { return null; }
    
    
    ////// Static Methods - Helpers //////
    
    /**
     * Loads the Codec for the given type, if supported.
     * Otherwise, throws a RuntimeException.
     */
    @SuppressWarnings("unchecked")
    public static <T> Codec<T> loadCodecFor(Class<T> type) {
        CodecManager codecManager = FactoryLoader.getInstance(CodecManager.class);
        Codec<T> result = codecManager.getComponent(type);
        if (result == null) {
            String message = String.format("Could not find Codec for type[%s].", type);
            throw new IllegalArgumentException(message);
        }
        return result;
    }
    
}
