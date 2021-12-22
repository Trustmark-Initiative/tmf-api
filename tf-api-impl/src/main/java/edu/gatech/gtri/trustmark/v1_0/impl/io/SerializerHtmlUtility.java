package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import static java.lang.String.format;

public class SerializerHtmlUtility {

    private static final Logger log = LoggerFactory.getLogger(SerializerHtmlUtility.class);
    private static Configuration freemarker = new Configuration(Configuration.VERSION_2_3_23);

    static {
        try {

            log.debug("Configuring Freemarker.");

            freemarker.setDefaultEncoding("UTF-8");
            freemarker.setTemplateLoader(new ClassTemplateLoader(SerializerHtmlUtility.class, "/META-INF/tmf/freemarker/"));
            freemarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            freemarker.setLogTemplateExceptions(false);
            freemarker.setCacheStorage(new freemarker.cache.MruCacheStorage(4, 4)); // hard references, soft references

        } catch (final Throwable throwable) {
            log.error("The system could not configure the freemarker engine.", throwable);
        }
    }

    private SerializerHtmlUtility() {
    }

    public static void process(
            final Map<Object, Object> model,
            final String templateName,
            final Writer writer)
            throws IOException {

        final Template template = freemarker.getTemplate(templateName);

        try {
            log.debug(format("Executing freemarker template: '%s'", templateName));

            final StringWriter stringWriter = new StringWriter();

            template.process(model, stringWriter);

            final String output = stringWriter.toString();

            log.debug(format("Generated HTML: %n%s", output));

            writer.write(output);
            writer.flush();

        } catch (final TemplateException templateException) {

            log.error(format("Error executing freemarker template: %s", templateException.getMessage()));

            throw new RuntimeException("The system could not execute the freemarker template.", templateException);
        }
    }

    public static String serialize(
            final Object object) {

        if (object == null)
            return "null";
        else {
            final Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
            final StringWriter stringWriter = new StringWriter();

            try {
                if (object instanceof TrustmarkDefinition) {
                    serializer.serialize((TrustmarkDefinition) object, stringWriter);
                } else if (object instanceof TrustInteroperabilityProfile) {
                    serializer.serialize((TrustInteroperabilityProfile) object, stringWriter);
                }
            } catch (Exception e) {
                throw new RuntimeException("The system could not serialize the object.", e);
            }

            // TODO: If the object is neither a TrustmarkDefinition nor a TrustInteroperabilityProfile, the method returns an empty string, which may be unexpected.
            return stringWriter.toString();
        }
    }
}
