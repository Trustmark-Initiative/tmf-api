package edu.gatech.gtri.trustmark.v1_0.io;

import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;

import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.none;

public enum MediaType {

    APPLICATION_JSON("application", none(), "json", nil(), none()),
    APPLICATION_PDF("application", none(), "pdf", nil(), none()),
    APPLICATION_XML("application", none(), "xml", nil(), none()),
    APPLICATION_ZIP("application", none(), "zip", nil(), none()),
    TEXT_HTML("text", none(), "html", nil(), none()),
    TEXT_PLAIN("text", none(), "plain", nil(), none()),
    TEXT_XML("text", none(), "xml", nil(), none());

    private final String type;
    private final Option<String> tree;
    private final String subtype;
    private final List<String> suffix;
    private final Option<String> parameter;
    private final String mediaType;

    MediaType(
            final String type,
            final Option<String> tree,
            final String subtype,
            final List<String> suffix,
            final Option<String> parameter) {
        this.type = type;
        this.tree = tree;
        this.subtype = subtype;
        this.suffix = suffix;
        this.parameter = parameter;
        this.mediaType = type + "/" +
                tree.map(treeInner -> treeInner + ".").orSome("") +
                subtype +
                suffix.foldLeft((suffixInner, suffixItem) -> suffixInner + "+" + suffixItem, "") +
                parameter.map(parameterInner -> ";" + parameterInner).orSome("");
    }

    public String getType() {
        return type;
    }

    public Option<String> getTree() {
        return tree;
    }

    public String getSubtype() {
        return subtype;
    }

    public List<String> getSuffix() {
        return suffix;
    }

    public Option<String> getParameter() {
        return parameter;
    }

    public String getMediaType() {
        return mediaType;
    }
}
