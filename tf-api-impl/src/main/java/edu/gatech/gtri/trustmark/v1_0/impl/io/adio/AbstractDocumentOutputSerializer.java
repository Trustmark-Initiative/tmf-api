package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;

import java.util.*;

/**
 * Created by Nicholas on 01/31/2017.
 */
public abstract class AbstractDocumentOutputSerializer<T, W, Ex extends Exception> {
    
    ////// Constants //////
    
    public static final String ATTRIBUTE_KEY_ID = "id";
    public static final String ATTRIBUTE_KEY_REF = "ref";
    
    
    ////// Instance Fields //////
    
    public final Codec<T> codec;
    
    
    ////// Constructor //////
    
    protected AbstractDocumentOutputSerializer(Codec<T> _codec) {
        this.codec = _codec;
    }
    
    
    ////// Instance Methods - Abstract //////
    
    public abstract <C> AbstractDocumentOutputSerializer<C, W, Ex> getAnalogousSerializer(Codec<C> otherCodec);
    
    protected abstract void serializeTopLevelObject(AbstractDocumentOutputContext<W> context, T obj) throws Ex;
    
    protected abstract void serializeObjectId(AbstractDocumentOutputContext<W> context, String id) throws Ex;
    protected abstract void serializeObjectRef(AbstractDocumentOutputContext<W> context, String ref) throws Ex;
    
    protected abstract void serializeEmptyObjectStart(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeEmptyObjectEnd(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    
    protected abstract void serializeObjectStart(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeObjectAttributes(AbstractDocumentOutputContext<W> context, Map<String, String> attributes) throws Ex;
    protected abstract void serializeBeforeObjectChild(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeAfterObjectChild(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeObjectEnd(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    
    protected abstract void serializeSequenceStart(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeBeforeSequenceChild(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeAfterSequenceChild(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    protected abstract void serializeSequenceEnd(AbstractDocumentOutputContext<W> context, String name) throws Ex;
    
    protected abstract void serializeValue(AbstractDocumentOutputContext<W> context, String name, boolean isCdata, String value) throws Ex;
    
    protected abstract void serializeInnerLevelNonCodecObject(AbstractDocumentOutputContext<W> context, String name, Object object) throws Ex;
    
    
    ////// Instance Methods - Concrete / Public //////
    
    public final void serializeRootObject(W writer, Object instance) throws Ex {
        Class<T> supportedType = this.codec.getSupportedType();
        if (instance == null || !supportedType.isInstance(instance)) {
            String message = String.format(
                "Invalid argument passed to %s! Expecting non-null instance of class[%s]!",
                this.getClass().getSimpleName(),
                supportedType.getName()
            );
            throw new IllegalArgumentException(message);
        }
        @SuppressWarnings("unchecked")
        T typedInstance = (T) instance;
        AbstractDocumentOutputContext<W> context = new AbstractDocumentOutputContext<>(writer);
        this.serializeTopLevelObject(context, typedInstance);
    }
    
    public final void serializeValueInObject(AbstractDocumentOutputContext<W> context, String name, Object value) throws Ex {
        this.serializeBeforeObjectChild(context, name);
        this.serializeValue(context, name, value);
        this.serializeAfterObjectChild(context, name);
    }
    
    public final void serializeValueInSequence(AbstractDocumentOutputContext<W> context, String name, Object value) throws Ex {
        this.serializeBeforeSequenceChild(context, name);
        this.serializeValue(context, name, value);
        this.serializeAfterSequenceChild(context, name);
    }
    
    public final <C> void serializeObjectInObject(AbstractDocumentOutputContext<W> context, Class<C> containedType, C containedObject)
        throws Ex
    {
        String containedName = this.getContainedObjectName(containedType);
        this.serializeBeforeObjectChild(context, containedName);
        this.serializeContainedInnerLevelObject(context, containedType, containedObject);
        this.serializeAfterObjectChild(context, containedName);
    }
    
    public final void serializeNonCodecObjectInObject(AbstractDocumentOutputContext<W> context, String name, Object object) throws Ex {
        this.serializeBeforeObjectChild(context, name);
        this.serializeInnerLevelNonCodecObject(context, name, object);
        this.serializeAfterObjectChild(context, name);
    }
    
    public final <C> void serializeObjectInSequence(AbstractDocumentOutputContext<W> context, Class<C> containedType, C containedObject)
        throws Ex
    {
        String containedName = this.getContainedObjectName(containedType);
        this.serializeBeforeSequenceChild(context, containedName);
        this.serializeContainedInnerLevelObject(context, containedType, containedObject);
        this.serializeAfterSequenceChild(context, containedName);
    }
    
    public final void serializeNonCodecObjectInSequence(AbstractDocumentOutputContext<W> context, String name, Object object) throws Ex {
        this.serializeBeforeSequenceChild(context, name);
        this.serializeInnerLevelNonCodecObject(context, name, object);
        this.serializeAfterSequenceChild(context, name);
    }
    
    public final <C> void serializeObjectSequenceInObject(
        AbstractDocumentOutputContext<W> context,
        Class<C> containedType,
        Collection<? extends C> containedObjectCollection
    )
        throws Ex
    {
        String collectionName = this.codec.getObjectSequenceParentNameFor(containedType);
        this.serializeBeforeObjectChild(context, collectionName);
        this.serializeSequenceStart(context, collectionName);
        if (containedObjectCollection != null) {
            for (C containedObject : containedObjectCollection) {
                this.serializeObjectInSequence(context, containedType, containedObject);
            }
        }
        this.serializeSequenceEnd(context, collectionName);
        this.serializeAfterObjectChild(context, collectionName);
    }
    
    public final <C> void serializeNonCodecObjectSequenceInObject(
        AbstractDocumentOutputContext<W> context,
        Class<C> containedType,
        Collection<? extends C> containedObjectCollection
    )
        throws Ex
    {
        String collectionName = this.codec.getObjectSequenceParentNameFor(containedType);
        String containedName = containedType.getSimpleName();
        this.serializeBeforeObjectChild(context, collectionName);
        this.serializeSequenceStart(context, collectionName);
        if (containedObjectCollection != null) {
            for (C containedObject : containedObjectCollection) {
                this.serializeNonCodecObjectInSequence(context, containedName, containedObject);
            }
        }
        this.serializeSequenceEnd(context, collectionName);
        this.serializeAfterObjectChild(context, collectionName);
    }
    
    public final void serializeValueSequenceInObject(
        AbstractDocumentOutputContext<W> context,
        String collectionName,
        Collection<?> containedValueCollection
    )
        throws Ex
    {
        this.serializeBeforeObjectChild(context, collectionName);
        this.serializeSequenceStart(context, collectionName);
        if (containedValueCollection != null) {
            String valueChildName = this.codec.getValueSequenceChildNameFor(collectionName);
            for (Object containedObject : containedValueCollection) {
                this.serializeValueInSequence(context, valueChildName, containedObject);
            }
        }
        this.serializeSequenceEnd(context, collectionName);
        this.serializeAfterObjectChild(context, collectionName);
        
    }
    
    public final void serializeMapLiteralObjectInObject(
        AbstractDocumentOutputContext<W> context,
        String name,
        Map<?,?> mapLiteral
    )
        throws Ex
    {
        this.serializeBeforeObjectChild(context, name);
        this.serializeObjectStart(context, name);
        for (Map.Entry<?,?> entry : mapLiteral.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                this.serializeMapLiteralObjectInObject(context, key, (Map<?,?>) value);
            }
            else {
                this.serializeValueInObject(context, key, value);
            }
        }
        this.serializeObjectEnd(context, name);
        this.serializeAfterObjectChild(context, name);
    }
    
    
    ////// Instance Methods - Concrete / Protected //////
    
    protected final <C> String getContainedObjectName(Class<C> containedObjectType) {
        Codec<C> containedCodec = Codec.loadCodecFor(containedObjectType);
        return containedCodec.getRootElementName();
    }
    
    protected final <C> void serializeContainedInnerLevelObject(AbstractDocumentOutputContext<W> context, Class<C> containedType, C containedObject)
        throws Ex
    {
        Codec<C> containedCodec = Codec.loadCodecFor(containedType);
        AbstractDocumentOutputSerializer<C, W, Ex> containedSerializer = this.getAnalogousSerializer(containedCodec);
        containedSerializer.serializeInnerLevelObject(context, containedObject);
    }
    
    protected final void serializeInnerLevelObject(AbstractDocumentOutputContext<W> context, T obj) throws Ex {
        String name = this.codec.getRootElementName();
        Set<String> encounteredIds = context.getEncounteredIdsForObjectName(name);
        String id = this.codec.getIdFor(obj);
        boolean isReference = encounteredIds.contains(id);
        if (isReference) {
            this.serializeEmptyObjectStart(context, name);
            this.serializeObjectRef(context, id);
            this.serializeEmptyObjectEnd(context, name);
        }
        else {
            this.serializeObjectStart(context, name);
            this.serializeNonRefObjectAttributesAndChildren(context, obj);
            this.serializeObjectEnd(context, name);
        }
    }
    
    protected final void serializeNonRefObjectAttributesAndChildren(AbstractDocumentOutputContext<W> context, T obj) throws Ex {
        String name = this.codec.getRootElementName();
        Set<String> encounteredIds = context.getEncounteredIdsForObjectName(name);
        String id = this.codec.getIdFor(obj);
        if (id != null) {
            encounteredIds.add(id);
            this.serializeObjectId(context, id);
        }
        Map<String, String> attributes = this.codec.getAttributesFor(obj);
        if (attributes != null) {
            this.serializeObjectAttributes(context, attributes);
        }
        this.codec.serializeChildrenFor(obj, this, context);
    }
    
    protected final void serializeValue(AbstractDocumentOutputContext<W> context, String name, Object value) throws Ex {
        boolean isCdata = this.codec.isCdataValueName(name);
        String stringValue = this.getStringValue(value);
        this.serializeValue(context, name, isCdata, stringValue);
    }
    
    protected final String getStringValue(Object value) {
        if (value == null) { return null; }
        if (value instanceof Date) { return XmlUtils.toDateTimeString((Date) value); }
        return value.toString();
    }
    
    
}
