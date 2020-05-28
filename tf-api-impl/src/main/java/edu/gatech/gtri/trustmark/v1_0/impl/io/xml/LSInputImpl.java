package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import org.w3c.dom.ls.LSInput;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by brad on 12/10/15.
 */
public class LSInputImpl implements LSInput {

    private String baseUri;
    private String systemId;
    private String publicId;
    private String data = null;
    private boolean certifiedText = false;
    private String encoding = null;

    public LSInputImpl(String baseUri, String publicId, String systemId, String fileContents){
        this.data = fileContents;
        this.systemId = systemId;
        this.publicId = publicId;
        this.baseUri = baseUri;
    }


    @Override
    public Reader getCharacterStream() {
        return new StringReader(this.data);
    }
    @Override
    public InputStream getByteStream() {
        return new ByteArrayInputStream(this.data.getBytes());
    }
    @Override
    public String getStringData() {
        return this.data;
    }
    @Override
    public String getSystemId() {
        return this.systemId;
    }
    @Override
    public String getPublicId() {
        return this.publicId;
    }
    @Override
    public String getBaseURI() {
        return this.baseUri;
    }
    @Override
    public void setBaseURI(String baseURI) {
        this.baseUri = baseURI;
    }
    @Override
    public String getEncoding() {
        return this.encoding;
    }
    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    @Override
    public boolean getCertifiedText() {
        return this.certifiedText;
    }
    @Override
    public void setCertifiedText(boolean certifiedText) {
        this.certifiedText = certifiedText;
    }





    @Override
    public void setPublicId(String publicId) {
        throw new UnsupportedOperationException("NOT SUPPORTED");
    }
    @Override
    public void setSystemId(String systemId) {
        throw new UnsupportedOperationException("NOT SUPPORTED");
    }
    @Override
    public void setStringData(String stringData) {
        throw new UnsupportedOperationException("NOT SUPPORTED");
    }
    @Override
    public void setByteStream(InputStream byteStream) {
        throw new UnsupportedOperationException("NOT SUPPORTED");
    }
    @Override
    public void setCharacterStream(Reader characterStream) {
        throw new UnsupportedOperationException("NOT SUPPORTED");
    }

}
