package edu.gatech.gtri.trustmark.v1_0.impl.io.html;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public final class SerializerHtmlUtility {

    private static final Configuration freemarker = new Configuration(Configuration.VERSION_2_3_23);

    static {
        freemarker.setDefaultEncoding("UTF-8");
        freemarker.setTemplateLoader(new ClassTemplateLoader(SerializerHtmlUtility.class, "/META-INF/tmf/freemarker/"));
        freemarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarker.setLogTemplateExceptions(false);
        freemarker.setCacheStorage(new freemarker.cache.MruCacheStorage(4, 4)); // hard references, soft references
    }

    private SerializerHtmlUtility() {
    }

    public static void process(
            final Map<Object, Object> model,
            final String templateName,
            final Writer writer)
            throws IOException {

        requireNonNull(model);
        requireNonNull(templateName);
        requireNonNull(writer);

        final Template template = freemarker.getTemplate(templateName);

        try {
            template.process(model, writer);
        } catch (final TemplateException templateException) {
            throw new RuntimeException(templateException);
        }
    }
}
