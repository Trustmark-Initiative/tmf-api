package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.html.SerializerHtmlEditor;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkDefinitionResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.StringWriter;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_HTML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestSerializerHtmlEditors extends AbstractTest {

    public static final String TD_SIMPLE = "./src/test/resources/TDs/td-full.xml";

    @Test
    public void testSerializerHtmlEditorResolution() throws Exception {
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getHtmlEditorSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo(TEXT_HTML.getMediaType()));
        assertThat(serializer.getName(), equalTo(SerializerHtmlEditor.class.getCanonicalName()));
        assertThat(serializer.getDescription(), equalTo(SerializerHtmlEditor.class.getCanonicalName()));
        logger.debug("Asserted SerializerHtmlEditors Resolution!");
    }

    @Test
    public void testSerializeSimpleTd() throws Exception {
        String tdFile = TD_SIMPLE;
        logger.debug("Asserting we can serialize TD: " + tdFile);

        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getHtmlEditorSerializer();
        TrustmarkDefinition td = FactoryLoader.getInstance(TrustmarkDefinitionResolver.class).resolve(new File(tdFile));

        StringWriter stringWriter = new StringWriter();
        serializer.serialize(td, stringWriter);
        String htmlOut = stringWriter.toString();

        assertThat(htmlOut, notNullValue());
        assertThat(htmlOut.contains("TD"), equalTo(true));

        IOUtils.writeToFile(new File("./target/td-editor.html"), htmlOut);
        logger.debug("Successfully asserted TD: " + tdFile);
    }
}
