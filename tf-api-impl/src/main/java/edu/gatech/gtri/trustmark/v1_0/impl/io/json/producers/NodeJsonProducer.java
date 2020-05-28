package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;

import edu.gatech.gtri.trustmark.v1_0.io.json.JsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Extension;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.XML;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by brad on 1/7/16.
 */
public class NodeJsonProducer extends AbstractJsonProducer implements JsonProducer {


    @Override
    public Class getSupportedType() {
        return Node.class;
    }

    @Override
    public Object serialize(Object instance) {
        if( instance == null || !(instance instanceof Node) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Node xmlNode = (Node) instance;

        StringWriter inMemoryStringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(inMemoryStringWriter, OutputFormat.createPrettyPrint());
        try {
            writer.write(xmlNode);
            writer.flush();
            writer.close();
        }catch(IOException ioe){
            throw new UnsupportedOperationException("Not able to write node to in-memory string!", ioe);
        }

        String inMemoryString = inMemoryStringWriter.toString().trim();
        inMemoryStringWriter = null;

        return XML.toJSONObject(inMemoryString);
    }



}
