package edu.gatech.gtri.trustmark.v1_0.model;

import java.io.InputStream;

/**
 * A standardized container for binary data in TMF-API model objects.
 *
 * @author Nicholas Saney
 */
public interface BinaryData {
    
    /**
     * Returns (a copy of) this container's binary data as a byte array.
     */
    public byte[] getBytes();
    
    /**
     * Returns (a copy of) this container's binary data as a base 64 string.
     */
    public String getBytesBase64();
    
    /**
     * Returns an input stream that returns this container's binary data.
     */
    public InputStream getInputStream();
    
}
