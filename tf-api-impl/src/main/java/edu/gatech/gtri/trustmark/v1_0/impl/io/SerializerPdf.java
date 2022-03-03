package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlHelper;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.w3c.tidy.Tidy;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Nicholas Saney on 3/27/17.
 */
public class SerializerPdf extends AbstractSerializer {

    public static final String TEMPLATE_BASE = "/META-INF/tmf/fop/";
    public static final String FOP_FACTORY_CLASSNAME = "org.apache.fop.apps.FopFactory";
    public static final String TRANSFORMER_FACTORY_CLASSNAME = "net.sf.saxon.TransformerFactoryImpl";
    public static final String JTIDY_CLASSNAME = "org.w3c.tidy.Tidy";

    ////// Instance Fields //////
    protected final FopFactory fopFactory;
    protected final TransformerFactory transformerFactory;
    protected final Tidy jTidy;
    protected final ErrorListener errorListener;
    protected final Serializer serializerXml;

    ////// Constructor //////
    public SerializerPdf() {
        super(
                "PDF Serializer",
                "Serializes data into PDF.",
                MediaType.APPLICATION_PDF.getMediaType());

        try {
            // FOP Factory
            try {
                Class.forName(FOP_FACTORY_CLASSNAME);
                // (reuse if you plan to render multiple documents!)
                this.fopFactory = FopFactory.newInstance(new URI("."));
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("PDF libraries not found!  Please provide Apache Formatting Objects Processor version 2.1+ on the classpath.", ex);
            }

            // Transformer Factory
            try {
                //this.transformerFactory = TransformerFactory.newInstance();
                Class.forName(TRANSFORMER_FACTORY_CLASSNAME);
                this.transformerFactory = TransformerFactory.newInstance(TRANSFORMER_FACTORY_CLASSNAME, null);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("XML transformer libraries not found!  Please provide Saxon Home Edition version 9.7+ on the classpath.", ex);
            }

            // JTidy
            try {
                Class.forName(JTIDY_CLASSNAME);
                this.jTidy = new Tidy();
                this.jTidy.setPrintBodyOnly(true);
                this.jTidy.setXmlOut(true);
                this.jTidy.setQuiet(true);
                this.jTidy.setShowWarnings(false);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("HTML Tidy libraries not found!  Please provide JTidy r938+ on the classpath.", ex);
            }

            // Error Listener
            this.errorListener = new ErrorListener() {
                @Override
                public void warning(TransformerException e) throws TransformerException {
                    throw new TransformerException("Warning", e);
                }

                @Override
                public void error(TransformerException e) throws TransformerException {
                    throw new TransformerException("Error", e);
                }

                @Override
                public void fatalError(TransformerException e) throws TransformerException {
                    throw new TransformerException("Fatal", e);
                }
            };

            // Serializer XML
            SerializerFactory serializerFactory = FactoryLoader.getInstance(SerializerFactory.class);
            this.serializerXml = serializerFactory.getXmlSerializer();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }

    @Override
    public void serialize(
            final Trustmark trustmark,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: PDF rendering for Trustmark");
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }

    @Override
    public void serialize(
            final TrustmarkStatusReport trustmarkStatusReport,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: PDF rendering for TrustmarkStatusReport");
    }

    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }


    @Override
    public void serialize(
            final TrustmarkDefinition trustmarkDefinition,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: PDF rendering for TrustmarkDefinition");
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }

    @Override
    public void serialize(
            final TrustInteroperabilityProfile trustInteroperabilityProfile,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: PDF rendering for TrustInteroperabilityProfile");
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }

    @Override
    public void serialize(
            final Agreement agreement,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        this.transformXmlToPdf(agreement, outputStream, model, new XmlPreparer<Agreement>() {
            @Override
            public String getXslFoTemplateName() {
                return "agreement-fo.xsl";
            }

            @Override
            public void writeEntityXml(Serializer serializerXml, Agreement entity, Writer writer) throws IOException {
                serializerXml.serialize(entity, writer);
            }

            @Override
            public List<String> getXpathListForXhtmlNodes() {
                return Arrays.asList(
                        "//tf:Agreement/tf:Title",
                        "//tf:Agreement/tf:Description",
                        "//tf:AgreementNonBindingSection/tf:Title",
                        "//tf:AgreementNonBindingSection/tf:Text",
                        "//tf:AgreementLegalSection/tf:Title",
                        "//tf:AgreementLegalSection/tf:Text",
                        "//tf:AgreementResponsibility/tf:Definition",
                        "//tf:SupplementedTIPSnapshot/tf:SupplementalLegalText",
                        "//tf:AgreementParty//tf:Notes",
                        "//tf:Agreement/tf:SignaturePageText",
                        "//tf:AgreementAttachment/tf:Name",
                        "//tf:AgreementAttachment/tf:Description"
                );
            }
        });
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final Writer writer,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("Cannot serialize PDF using a Writer");
    }

    @Override
    public void serialize(
            final AgreementResponsibilityTemplate agreementResponsibilityTemplate,
            final OutputStream outputStream,
            final Map<Object, Object> model)
            throws IOException {

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED: PDF rendering for AgreementResponsibilityTemplate");
    }

    public <T> void transformXmlToPdf(T entity, OutputStream outputStream, Map model, XmlPreparer<T> xmlPreparer) throws IOException {
        // see examples at:
        // https://github.com/apache/fop/blob/trunk/fop/examples/embedding/java/embedding/ExampleXML2FO.java
        // https://github.com/apache/fop/blob/trunk/fop/examples/embedding/java/embedding/ExampleFO2PDF.java
        // https://github.com/apache/fop/blob/trunk/fop/examples/embedding/java/embedding/ExampleXML2PDF.java

        // Note: Using BufferedOutputStream for performance reasons
        try (OutputStream out = new BufferedOutputStream(outputStream)) {

            //// XML -> XSL-FO ////

            // Setup XSLT
            String xslFoTemplateName = xmlPreparer.getXslFoTemplateName();
            InputStream xsltInputStream = this.getClass().getResourceAsStream(TEMPLATE_BASE + xslFoTemplateName);
            Transformer transformerXslt = this.transformerFactory.newTransformer(new StreamSource(xsltInputStream));

            // Set the value of <param>s in the stylesheet
            for (Object key : model.keySet()) {
                String keyString = String.valueOf(key);
                Object value = model.get(key);
                transformerXslt.setParameter(keyString, value);
            }

            // Setup input for XSLT transformation
            String xmlPrepared = xmlPreparer.prepareXml(this, entity);
            StringReader xmlReader = new StringReader(xmlPrepared);
            Source src = new StreamSource(xmlReader);

            // Setup intermediate output to XSL-FO
            StringWriter xslFoWriter = new StringWriter();
            Result resXslFo = new StreamResult(xslFoWriter);

            // Do XSLT transformation
            transformerXslt.setErrorListener(this.errorListener);
            transformerXslt.transform(src, resXslFo);


            //// XSL-FO -> PDF ////

            // Setup FOP
            Transformer transformerFop = this.transformerFactory.newTransformer(); // identity transformer

            // Setup input for FOP processing
            String xslFo = xslFoWriter.toString();
            StringReader xslFoReader = new StringReader(xslFo);
            Source srcXslFo = new StreamSource(xslFoReader);

            // Setup final output to PDF
            Fop fop = this.fopFactory.newFop(MimeConstants.MIME_PDF, out);
            Result res = new SAXResult(fop.getDefaultHandler());

            // Do FOP processing
            transformerFop.transform(srcXslFo, res);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    ////// Static Inner Classes //////
    public static abstract class XmlPreparer<T> {

        //// Instance Methods - Abstract ////
        public abstract String getXslFoTemplateName();

        public abstract void writeEntityXml(Serializer serializerXml, T entity, Writer writer) throws IOException;

        public abstract List<String> getXpathListForXhtmlNodes();

        //// Instance Methods - Concrete ////
        public String prepareXml(SerializerPdf serializerPdf, T entity) throws IOException {
            StringWriter stringWriter = new StringWriter();
            this.writeEntityXml(serializerPdf.serializerXml, entity, stringWriter);
            String xmlOriginal = stringWriter.toString();
            try {
                Element xmlRoot = XmlHelper.readWithDom4j(xmlOriginal);

                // prepare html as xhtml
                List<String> xpathListForXhtmlNodes = this.getXpathListForXhtmlNodes();
                for (String xpathForXhtmlNodes : xpathListForXhtmlNodes) {
                    List xhtmlNodes = xmlRoot.selectNodes(xpathForXhtmlNodes);
                    for (Object obj : xhtmlNodes) {
                        Node node = (Node) obj;
                        String html = node.getText();
                        String xhtml = prepareHtmlAsXhtml(serializerPdf, html);
                        node.setText(xhtml);
                    }
                }

                return xmlRoot.asXML();
            }
//        catch (IOException ex) {
//            throw ex;
//        }
            catch (Exception ex) {
                throw new IOException(ex);
            }
        }

        //// Static Methods ////
        public static String prepareHtmlAsXhtml(SerializerPdf serializerPdf, String html) throws DocumentException {
            StringWriter stringWriter = new StringWriter();
            StringReader stringReader = new StringReader(html);
            serializerPdf.jTidy.parse(stringReader, stringWriter);
            String xhtml = stringWriter.toString();
            xhtml = "<div>" + xhtml + "</div>";
            return xhtml;
        }
    }
}
