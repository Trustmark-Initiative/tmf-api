package edu.gatech.gtri.trustmark.v1_0.impl.trust;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinitionRequirement;
import org.gtri.fj.data.List;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

public final class TrustmarkUtility {
    private TrustmarkUtility() {
    }

    /**
     * Return true if a trustmark in the trustmark list satisfies the trustmark definition requirement.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmarkList                  the trustmark list
     * @return true if a trustmark in the trustmark list satisfies the trustmark definition requirement
     */
    public static boolean satisfyingTrustmarkExists(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        return satisfyingTrustmarkList(trustmarkDefinitionRequirement, trustmarkList).isNotEmpty();
    }

    /**
     * Return the trustmarks in the trustmark list that satisfy the trustmark definition requirement.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmarkList                  the trustmark list
     * @return the trustmarks in the trustmark list that satisfy the trustmark definition requirement
     */
    public static List<Trustmark> satisfyingTrustmarkList(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final List<Trustmark> trustmarkList) {

        // the trustmark satisfies the trustmark definition requirement only if
        //   the trustmark definition requirement -> identifier equals the trustmark -> trustmark definition reference -> identifier
        //   the trustmark provider sat

        return trustmarkList.filter(trustmark ->
                trustmarkDefinitionRequirement.getIdentifier().equals(trustmark.getTrustmarkDefinitionReference().getIdentifier()) &&
                        satisfyingProviderExists(trustmarkDefinitionRequirement, trustmark));
    }

    /**
     * Return true if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider.
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmark                      the trustmark
     * @return true if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider.
     */
    public static boolean satisfyingProviderExists(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final Trustmark trustmark) {

        return satisfyingProvider(trustmarkDefinitionRequirement, trustmark).isNotEmpty();
    }

    /**
     * Return
     * <ul>
     *     <li>a list containing the trustmark provider if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider</li>
     *     <li>an empty list if the trustmark definition requirement does specify a provider and the trustmark does not match that provider</li>
     * </ul>
     *
     * @param trustmarkDefinitionRequirement the trustmark definition requirement
     * @param trustmark                      the trustmark
     * @return <ul>
     *             <li>a list containing the trustmark provider if the trustmark definition requirement does not specify a provider OR if the trustmark definition requirement does specify a provider and the trustmark matches that provider</li>
     *             <li>an empty list if the trustmark definition requirement does specify a provider and the trustmark does not match that provider</li>
     *         </ul>
     */
    public static List<Entity> satisfyingProvider(
            final TrustmarkDefinitionRequirement trustmarkDefinitionRequirement,
            final Trustmark trustmark) {

        final List<Entity> trustmarkDefinitionProviderReferenceList =
                fromNull(trustmarkDefinitionRequirement.getProviderReferences())
                        .map(List::iterableList)
                        .orSome(nil())
                        .filter(entity -> entity.getIdentifier() != null);

        return trustmarkDefinitionProviderReferenceList.isEmpty() ?
                list(trustmark.getProvider()) :
                trustmarkDefinitionProviderReferenceList
                        .filter(entity -> entity.getIdentifier().equals(trustmark.getProvider().getIdentifier()));
    }

    public static Document documentFor(final Trustmark trustmark) {

        try {
            // if setSchema worked, we could use this rather than require the client specify the attribute
            // final Source sourceSchemaForTrustmark = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH));
            // final Source sourceSchemaForTrustmark13 = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_3));
            // final Source sourceSchemaForTrustmark14 = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.TRUSTMARK_FRAMEWORK_SCHEMA_PATH_1_4));
            // final Source sourceSchemaForXmlSignature = new StreamSource(XmlHelper.class.getResourceAsStream(XmlConstants.XML_SIG_SCHEMA_PATH));

            // final Source[] sourceSchemaArray = new Source[]{sourceSchemaForTrustmark, sourceSchemaForTrustmark13, sourceSchemaForTrustmark14, sourceSchemaForXmlSignature};
            // final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // schemaFactory.setResourceResolver(new LSResourceResolverImpl());
            // final Schema schema = schemaFactory.newSchema(sourceSchemaArray);

            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            // documentBuilderFactory.setSchema(schema);

            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            final Document document = documentBuilder.parse(new InputSource(new StringReader(trustmark.getOriginalSource())));
            // if setSchema worked, we could use this rather than require the client specify the attribute
            document.getDocumentElement().setIdAttributeNode((Attr) document.getDocumentElement().getAttributes().getNamedItemNS("https://trustmarkinitiative.org/specifications/trustmark-framework/1.4/schema/", ATTRIBUTE_KEY_JSON_ID), true);

            return document;
        } catch (final ParserConfigurationException parserConfigurationException) {
            throw new RuntimeException(parserConfigurationException);
        } catch (final SAXException saxException) {
            throw new RuntimeException(saxException);
        } catch (final IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
