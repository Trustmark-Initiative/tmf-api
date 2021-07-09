package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by brad on 1/7/16.
 */
public final class NodeJsonProducer implements JsonProducer<Node, JSONObject> {


    @Override
    public Class<Node> getSupportedType() {
        return Node.class;
    }

    @Override
    public Class<JSONObject> getSupportedTypeOutput() {
        return JSONObject.class;
    }

    @Override
    public JSONObject serialize(Node xmlNode) {
        StringWriter inMemoryStringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(inMemoryStringWriter, OutputFormat.createPrettyPrint());
        try {
            writer.write(xmlNode);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            throw new UnsupportedOperationException("Not able to write node to in-memory string!", ioe);
        }

        String inMemoryString = inMemoryStringWriter.toString().trim();
        inMemoryStringWriter = null;

        return XML.toJSONObject(inMemoryString);
    }


}
