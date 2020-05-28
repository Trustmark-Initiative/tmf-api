package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.Parser;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Nicholas on 02/01/2017.
 */
public abstract class AbstractDocumentInputDeserializer<T, O, S, V> {
    
    ////// Constants //////
    public static final String PATH_DELIMITER = "/";
    public static final Pattern PATH_DELIMITER_PATTERN = Pattern.compile(Pattern.quote(PATH_DELIMITER));
    
    
    ////// Instance Fields //////
    
    public final Codec<T> codec;
    
    
    ////// Constructor //////
    
    protected AbstractDocumentInputDeserializer(Codec<T> _codec) {
        this.codec = _codec;
    }
    
    
    ////// Instance Methods - Abstract //////
    
    protected abstract <C> AbstractDocumentInputDeserializer<C, O, S, V> getAnalogousDeserializer(Codec<C> otherCodec);
    protected abstract String getSourceType();
    
    protected abstract Map.Entry<IdRefType, String> getReferenceInfoForObjectNode(O rootNode) throws ParseException;
    protected abstract List<Object> getChildNodesInSequenceNode(S sequenceNode) throws ParseException;
    protected abstract String getStringFromValueNode(V valueNode) throws ParseException;
    protected abstract boolean isObjectNode(Object node);
    protected abstract boolean isSequenceNode(Object node);
    protected abstract boolean isValueNode(Object node);
    protected abstract List<Object> getMatchingNodesInRootObject(O rootNode, int limit, String[] pathSegments) throws ParseException;
    
    protected abstract Entity getEntityFromObjectNode(O entityNode) throws ParseException;
    protected abstract Term getTermFromObjectNode(O termNode) throws ParseException;
    protected abstract TrustInteroperabilityProfile getTipFromObjectNode(O tipNode) throws ParseException;
    
    
    ////// Instance Methods - Concrete / Public //////
    
    public final T deserializeRootObjectNode(O rootNode, String rawString) throws ParseException
    {
        return this.deserializeRootObjectNode(rootNode, rawString, new AbstractDocumentInputContext());
    }
    
    public final String deserializeStringValueInObject(O rootNode, boolean required, String path) throws ParseException
    {
        V valueNode = this.getValueNodeInObject(rootNode, required, path);
        return valueNode == null ? null : this.getStringOrNullFromValueNode(valueNode);
    }
    
    public final URI deserializeUriValueInObject(O rootNode, boolean required, String path) throws ParseException
    {
        String stringValue = this.deserializeStringValueInObject(rootNode, required, path);
        try { return stringValue == null ? null : new URI(stringValue); }
        catch (Exception ex) { throw this.getStandardParseException(ex, URI.class.getSimpleName(), path, stringValue); }
    }
    
    public final Number deserializeNumberValueInObject(O rootNode, boolean required, String path) throws ParseException
    {
        String stringValue = this.deserializeStringValueInObject(rootNode, required, path);
        try { return stringValue == null ? null : Double.parseDouble(stringValue); }
        catch (Exception ex) { throw this.getStandardParseException(ex, Double.class.getSimpleName(), path, stringValue); }
    }
    
    public final Date deserializeDateValueInObject(O rootNode, boolean required, String path) throws ParseException
    {
        String stringValue = this.deserializeStringValueInObject(rootNode, required, path);
        try { return stringValue == null ? null : DatatypeConverter.parseDateTime(stringValue).getTime(); }
        catch (Exception ex) { throw this.getStandardParseException(ex, Date.class.getSimpleName(), path, stringValue); }
    }
    
    public final <E extends Enum<E>> E deserializeEnumValueInObject(O rootNode, boolean required, Class<E> enumType, String path) throws ParseException
    {
        String stringValue = this.deserializeStringValueInObject(rootNode, required, path);
        try { return stringValue == null ? null : Enum.valueOf(enumType, stringValue); }
        catch (Exception ex) { throw this.getStandardParseException(ex, enumType.getSimpleName(), path, stringValue); }
    }
    
    public final <C> C deserializeObjectInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required,
        Class<C> containedType
    )
        throws ParseException
    {
        return this.deserializeObjectInObject(rootNode, context, required, null, containedType);
    }
    
    public final <C> C deserializeObjectInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required,
        String parentPath,
        Class<C> containedType
    )
        throws ParseException
    {
        Codec<C> containedCodec = Codec.loadCodecFor(containedType);
        AbstractDocumentInputDeserializer<C, O, S, V> containedDeserializer = this.getAnalogousDeserializer(containedCodec);
        String containedName = containedCodec.getRootElementName();
        String path = this.getPathForParentPathAndChildName(parentPath, containedName);
        O containedObjectNode = this.getObjectNodeInObject(rootNode, required, path);
        return containedDeserializer.deserializeRootObjectNode(containedObjectNode, null, context);
    }
    
    public final Entity deserializeEntityInObject(O rootNode, boolean required, String path) throws ParseException {
        O entityNode = this.getObjectNodeInObject(rootNode, true, path);
        return this.getEntityFromObjectNode(entityNode);
    }
    
    public final TrustInteroperabilityProfile deserializeTipInObject(O rootNode, boolean required, String path) throws ParseException {
        O tdRequirementNode = this.getObjectNodeInObject(rootNode, required, path);
        return this.getTipFromObjectNode(tdRequirementNode);
    }
    
    public final <C> List<C> deserializeObjectSequenceInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required,
        Class<C> containedType
    )
        throws ParseException
    {
        return this.deserializeObjectSequenceInObject(rootNode, context, required, null, containedType);
    }
    
    public final <C> List<C> deserializeObjectSequenceInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required,
        String parentPath,
        Class<C> containedType
    )
        throws ParseException
    {
        String sequenceParentName = this.codec.getObjectSequenceParentNameFor(containedType);
        Codec<C> containedCodec = Codec.loadCodecFor(containedType);
        AbstractDocumentInputDeserializer<C, O, S, V> containedDeserializer = this.getAnalogousDeserializer(containedCodec);
        String path = this.getPathForParentPathAndChildName(parentPath, sequenceParentName);
        S sequenceNode = this.getSequenceNodeInObject(rootNode, required, path);
        List<C> result = new ArrayList<>();
        List<O> childNodes = this.getObjectNodesInObjectSequence(sequenceNode);
        for (O childNode : childNodes) {
            C childResult = containedDeserializer.deserializeRootObjectNode(childNode, null, context);
            result.add(childResult);
        }
        return result;
    }
    
    public final List<Term> deserializeTermSequenceInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required
    )
        throws ParseException
    {
        return this.deserializeTermSequenceInObject(rootNode, context, required, null);
    }
    
    public final List<Term> deserializeTermSequenceInObject(
        O rootNode,
        AbstractDocumentInputContext context,
        boolean required,
        String parentPath
    )
        throws ParseException
    {
        String sequenceParentName = this.codec.getObjectSequenceParentNameFor(Term.class);
        String path = this.getPathForParentPathAndChildName(parentPath, sequenceParentName);
        S sequenceNode = this.getSequenceNodeInObject(rootNode, required, path);
        List<Term> result = new ArrayList<>();
        List<O> childNodes = this.getObjectNodesInObjectSequence(sequenceNode);
        for (O childNode : childNodes) {
            Term childResult = this.getTermFromObjectNode(childNode);
            result.add(childResult);
        }
        return result;
    }
    
    public final List<String> deserializeStringValueSequenceInObject(
        O rootNode,
        boolean required,
        String pathToSequence
    )
        throws ParseException
    {
        return this.deserializeValueSequenceInObject(rootNode, required, pathToSequence, Parser.IDENTITY);
    }
    
    public final <P> List<P> deserializeValueSequenceInObject(
        O rootNode,
        boolean required,
        String pathToSequence,
        Parser<P> parser
    )
        throws ParseException
    {
        S sequenceNode = this.getSequenceNodeInObject(rootNode, required, pathToSequence);
        List<P> result = new ArrayList<>();
        List<V> childNodes = this.getValueNodesInValueSequence(sequenceNode);
        for (V childNode : childNodes) {
            String childResultString = this.getStringOrNullFromValueNode(childNode);
            P childResult = parser.parseOrNull(childResultString);
            result.add(childResult);
        }
        return result;
    }
    
    
    ////// Instance Methods - Concrete / Protected //////
    
    protected final String getStringOrNullFromValueNode(V valueNode) throws ParseException {
        String nodeText = this.getStringFromValueNode(valueNode);
        return (nodeText == null || nodeText.length() == 0) ? null : nodeText;
    }
    
    protected final T deserializeRootObjectNode(O objectNode, String rawString, AbstractDocumentInputContext context)
        throws ParseException
    {
        Class<T> type = this.codec.getSupportedType();
        Map<String, T> encounteredObjectsById = context.getEncounteredObjectsMapForType(type);
        Map.Entry<IdRefType, String> referenceInfo = this.getReferenceInfoForObjectNode(objectNode);
        IdRefType idRefType = referenceInfo.getKey();
        String idRef = referenceInfo.getValue();
        T result = encounteredObjectsById.get(idRef);
        if (idRefType == IdRefType.REF) {
            if (result == null) {
                String message = String.format("Could not find referenced object: ref = [%s]", idRef);
                throw new AbstractDocumentDeserializationException(message);
            }
        }
        else {
            boolean isIdentified = idRefType == IdRefType.ID;
            if (isIdentified && result != null) {
                String message = String.format("Identified object is duplicated: id = [%s]", idRef);
                throw new AbstractDocumentDeserializationException(message);
            }
            String sourceType = rawString == null ? null : this.getSourceType();
            result = this.codec.deserializeFullObject(objectNode, idRef, rawString, sourceType, this, context);
            if (isIdentified) {
                encounteredObjectsById.put(idRef, result);
            }
        }
        return result;
    }
    
    protected final AbstractDocumentDeserializationException getStandardParseException(
        Exception ex,
        String expectedTypeName,
        String path,
        String stringValue
    )
    {
        String rootNodeName = this.codec.getRootElementName();
        String stringValueQuoted = stringValue == null ? "null" : ('"' + stringValue + '"');
        String message = String.format("Unable to parse %s value in %s at path[%s]. Value was: %s", expectedTypeName, rootNodeName, path, stringValueQuoted);
        return new AbstractDocumentDeserializationException(message, ex);
    }
    
    protected final String getPathForParentPathAndChildName(String parentPath, String childName) throws ParseException
    {
        return parentPath == null ? childName : (parentPath + PATH_DELIMITER + childName);
    }
    
    protected final String[] getSegmentsForPath(String path) throws ParseException {
        return path == null ? new String[0] : PATH_DELIMITER_PATTERN.split(path);
    }
    
    protected final void ensureNodeInNodeIfRequired(
        boolean required,
        Object node,
        String nodeTypeNounPhrase,
        String path
    )
        throws ParseException
    {
        String rootNodeName = this.codec.getRootElementName();
        if (required && node == null) {
            String message = String.format("Expected %s in %s at path '%s', but there were no matches.", nodeTypeNounPhrase, rootNodeName, path);
            message += " Make sure there were no incorrectly specified names or namespaces.";
            throw new AbstractDocumentDeserializationException(message);
        }
    }
    
    @SuppressWarnings("unchecked")
    protected final <N> N getFirstMatchingNodeInObject(O rootNode, NodeMatcher matcher, String path) throws ParseException
    {
        String[] pathSegments = this.getSegmentsForPath(path);
        List<Object> resultNodes = this.getMatchingNodesInRootObject(rootNode, 1, pathSegments);
        if (resultNodes != null) {
            for (Object node : resultNodes) {
                if (matcher.matches(this, node)) {
                    return (N)node;
                }
            }
        }
        return null;
    }
    
    protected final O getObjectNodeInObject(O rootNode, boolean required, String path) throws ParseException
    {
        O result = this.getFirstMatchingNodeInObject(rootNode, NodeMatcher.OBJECT, path);
        this.ensureNodeInNodeIfRequired(required, result, "an object node", path);
        return result;
    }
    
    protected final S getSequenceNodeInObject(O rootNode, boolean required, String path) throws ParseException
    {
        S result = this.getFirstMatchingNodeInObject(rootNode, NodeMatcher.SEQUENCE, path);
        this.ensureNodeInNodeIfRequired(required, result, "a sequence node", path);
        return result;
    }
    
    protected final V getValueNodeInObject(O rootNode, boolean required, String path) throws ParseException
    {
        V result = this.getFirstMatchingNodeInObject(rootNode, NodeMatcher.VALUE, path);
        this.ensureNodeInNodeIfRequired(required, result, "a value node", path);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    protected final <N> List<N> getNodesInSequence(S sequenceNode, NodeMatcher matcher) throws ParseException
    {
        List<N> results = new ArrayList<>();
        if (sequenceNode != null) {
            List<Object> childNodes = this.getChildNodesInSequenceNode(sequenceNode);
            if (childNodes != null) {
                for (Object childNode : childNodes) {
                    if (!matcher.matches(this, childNode)) { continue; }
                    N childResult = (N) childNode;
                    results.add(childResult);
                }
            }
        }
        return results;
    }
    
    protected final List<O> getObjectNodesInObjectSequence(S sequenceNode) throws ParseException
    {
        return this.getNodesInSequence(sequenceNode, NodeMatcher.OBJECT);
    }
    
    protected final List<V> getValueNodesInValueSequence(S sequenceNode) throws ParseException
    {
        return this.getNodesInSequence(sequenceNode, NodeMatcher.VALUE);
    }
    
    
    ////// Static Inner Classes //////
    protected static abstract class NodeMatcher {
        //// Instance Methods ////
        public abstract boolean matches(AbstractDocumentInputDeserializer<?,?,?,?> deserializer, Object node) throws ParseException;
        
        //// Constants ////
        public static final NodeMatcher OBJECT = new NodeMatcher() {
            @Override public boolean matches(AbstractDocumentInputDeserializer<?, ?, ?, ?> deserializer, Object node) throws ParseException
            {
                return node != null && deserializer.isObjectNode(node);
            }
        };
        public static final NodeMatcher SEQUENCE = new NodeMatcher() {
            @Override public boolean matches(AbstractDocumentInputDeserializer<?, ?, ?, ?> deserializer, Object node) throws ParseException
            {
                return node != null && deserializer.isSequenceNode(node);
            }
        };
        public static final NodeMatcher VALUE = new NodeMatcher() {
            @Override public boolean matches(AbstractDocumentInputDeserializer<?, ?, ?, ?> deserializer, Object node) throws ParseException
            {
                return node != null && deserializer.isValueNode(node);
            }
        };
    }
    
}
