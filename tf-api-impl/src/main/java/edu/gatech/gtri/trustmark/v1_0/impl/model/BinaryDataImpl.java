package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.BinaryData;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Nicholas Saney on 2017-09-04.
 */
public class BinaryDataImpl implements BinaryData {
    
    ////// Instance Fields //////
    protected byte[] data;
    
    ////// Instance Methods //////
    @Override
    public byte[] getBytes() { return Arrays.copyOf(this.data, this.data.length); }
    public void setBytes(byte[] bytes) { this.data = Arrays.copyOf(bytes, bytes.length); }
    
    @Override
    public String getBytesBase64() { return Base64.encodeBase64String(this.data); }
    public void setBytesBase64(String bytesBase64) { this.data = Base64.decodeBase64(bytesBase64); }
    
    @Override
    public InputStream getInputStream() { return new ByteArrayInputStream(this.data); }
    
}
