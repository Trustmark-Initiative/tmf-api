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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 12/9/16
 */
public abstract class AbstractHtmlSerializer extends AbstractSerializer {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================

    public static final String TEMPLATE_BASE = "/META-INF/tmf/freemarker/";

    private static final Logger log = LogManager.getLogger(AbstractHtmlSerializer.class);
    private static Configuration freemarker;

    public AbstractHtmlSerializer(String name, String description, String mimeType) {
        super(name, description, mimeType);
        try{
            log.debug("Configuring Freemarker...");

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(new ClassTemplateLoader(SerializerHtml.class, TEMPLATE_BASE));
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(4, 4)); // hard references, soft references

            freemarker = cfg;
        }catch(Throwable t){
            log.error("Error configuring Freemarker Template Engine!", t);
        }
    }


    protected void executeTemplate(Map model, String templateName, Writer writer) throws IOException {
        Template template = freemarker.getTemplate(templateName);
        try {
            log.debug("Executing freemarker template: " + templateName);
            StringWriter transientWriter = new StringWriter();
            template.process(model, transientWriter);
            String output = transientWriter.toString();
            log.debug("Generated HTML: \n"+output);
            writer.write(output);
            writer.flush();
        }catch(TemplateException te){
            log.error("Error executing Freemarker template: "+te);
            throw new RuntimeException("Unexpected error with freemarker template!", te);
        }
    }

    protected String asJsonString(Object object){
        if( object == null )
            return "null";
        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        StringWriter stringWriter = new StringWriter();
        try{
            if( object instanceof TrustmarkDefinition){
                jsonSerializer.serialize((TrustmarkDefinition) object, stringWriter);
            }else if( object instanceof TrustInteroperabilityProfile){
                jsonSerializer.serialize((TrustInteroperabilityProfile) object, stringWriter);
            }
        }catch(Exception e){
            throw new RuntimeException("Unexpected error serializing object!", e);
        }
        return stringWriter.toString();
    }


}/* end AbstractHtmlSerializer */
