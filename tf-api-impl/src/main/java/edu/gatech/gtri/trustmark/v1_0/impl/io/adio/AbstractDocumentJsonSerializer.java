package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.TrustmarkFramework;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.AbstractJsonProducer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.*;

/**
 * Created by Nicholas on 01/31/2017.
 */
public class AbstractDocumentJsonSerializer<T> extends AbstractDocumentOutputSerializer<T, JSONWriter, RuntimeException> {
    
    ////// Constants //////
    
    public static final String ATTRIBUTE_KEY_JSON_META_PREFIX = "$";
    public static final String ATTRIBUTE_KEY_JSON_TYPE = ATTRIBUTE_KEY_JSON_META_PREFIX + "Type";
    public static final String ATTRIBUTE_KEY_JSON_TMF_VERSION = ATTRIBUTE_KEY_JSON_META_PREFIX + "TMF_VERSION";
    public static final String ATTRIBUTE_KEY_JSON_ID = ATTRIBUTE_KEY_ID;
    public static final String ATTRIBUTE_KEY_JSON_REF = ATTRIBUTE_KEY_JSON_META_PREFIX + ATTRIBUTE_KEY_REF;
    public static final String ATTRIBUTE_VALUE_JSON_REF_PREFIX = "#";
    public static final Set<String> ATTRIBUTE_KEYS_JSON_META = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        ATTRIBUTE_KEY_JSON_TYPE,        // $Type
        ATTRIBUTE_KEY_JSON_TMF_VERSION, // $TMF_VERSION
        ATTRIBUTE_KEY_JSON_ID,          // id [or $id ??] TODO: determine whether to prefix
        ATTRIBUTE_KEY_JSON_REF          // $ref
    )));
    
    
    ////// Constructor //////
    
    public AbstractDocumentJsonSerializer(Codec<T> _codec) { super(_codec); }
    
    
    ////// Instance Methods //////
    
    @Override
    public <C> AbstractDocumentOutputSerializer<C, JSONWriter, RuntimeException> getAnalogousSerializer(Codec<C> otherCodec) {
        return otherCodec.jsonSerializer;
    }
    
    @Override
    protected void serializeTopLevelObject(AbstractDocumentOutputContext<JSONWriter> context, T obj) {
        String name = this.codec.getRootElementName();
        this.serializeObjectStart(context, name);
        
        // top-level elements need type and version info
        context.writer.key(ATTRIBUTE_KEY_JSON_TYPE);
        context.writer.value(this.codec.getSupportedType().getSimpleName());
        context.writer.key(ATTRIBUTE_KEY_JSON_TMF_VERSION);
        context.writer.value(FactoryLoader.getInstance(TrustmarkFramework.class).getTrustmarkFrameworkVersion());
        
        this.serializeNonRefObjectAttributesAndChildren(context, obj);
        this.serializeObjectEnd(context, name);
    }
    
    @Override
    protected void serializeObjectId(AbstractDocumentOutputContext<JSONWriter> context, String id) {
        context.writer.key(ATTRIBUTE_KEY_JSON_ID);
        context.writer.value(id);
    }
    
    @Override
    protected void serializeObjectRef(AbstractDocumentOutputContext<JSONWriter> context, String ref) {
        context.writer.key(ATTRIBUTE_KEY_JSON_REF);
        context.writer.value(ATTRIBUTE_VALUE_JSON_REF_PREFIX + ref);
    }
    
    @Override
    protected void serializeEmptyObjectStart(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        this.serializeObjectStart(context, name);
    }
    
    @Override
    protected void serializeEmptyObjectEnd(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        this.serializeObjectEnd(context, name);
    }
    
    @Override
    protected void serializeObjectStart(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        context.writer.object();
    }
    
    @Override
    protected void serializeObjectAttributes(AbstractDocumentOutputContext<JSONWriter> context, Map<String, String> attributes) {
        // ignore attributes
    }
    
    @Override
    protected void serializeBeforeObjectChild(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        context.writer.key(name);
    }
    
    @Override
    protected void serializeAfterObjectChild(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        // nothing special on element child end
    }
    
    @Override
    protected void serializeObjectEnd(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        context.writer.endObject();
    }
    
    @Override
    protected void serializeSequenceStart(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        context.writer.array();
    }
    
    @Override
    protected void serializeBeforeSequenceChild(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        // nothing special on sequence child start
    }
    
    @Override
    protected void serializeAfterSequenceChild(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        // nothing special on sequence child end
    }
    
    @Override
    protected void serializeSequenceEnd(AbstractDocumentOutputContext<JSONWriter> context, String name) {
        context.writer.endArray();
    }
    
    @Override
    protected void serializeValue(AbstractDocumentOutputContext<JSONWriter> context, String name, boolean isCdata, String value) {
        context.writer.value(value);
    }
    
    @Override
    protected void serializeInnerLevelNonCodecObject(AbstractDocumentOutputContext<JSONWriter> context, String name, Object object) {
        Object nonCodecJson = AbstractJsonProducer.toJson(object);
        this.serializeNonCodecJson(context, name, nonCodecJson);
    }
    
    private void serializeNonCodecJson(AbstractDocumentOutputContext<JSONWriter> context, String name, Object nonCodecJson) {
        if (nonCodecJson instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)nonCodecJson;
            this.serializeObjectStart(context, name);
            for (String childName : jsonObject.keySet()) {
                Object child = jsonObject.get(childName);
                this.serializeBeforeObjectChild(context, childName);
                this.serializeNonCodecJson(context, childName, child);
                this.serializeAfterObjectChild(context, childName);
            }
            this.serializeObjectEnd(context, name);
        }
        else if (nonCodecJson instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)nonCodecJson;
            this.serializeSequenceStart(context, name);
            final String childName = ""; // this doesn't matter in JSON
            for (Object child : jsonArray) {
                this.serializeBeforeSequenceChild(context, childName);
                this.serializeNonCodecJson(context, childName, child);
                this.serializeAfterSequenceChild(context, childName);
            }
            this.serializeSequenceEnd(context, name);
        }
        else {
            final boolean isCdata = false; // this doesn't matter in JSON
            String stringValue = this.getStringValue(nonCodecJson);
            this.serializeValue(context, name, isCdata, stringValue);
        }
    }
    
}
