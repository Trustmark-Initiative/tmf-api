package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.impl.util.AbstractTypeSpecificComponentManager;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class CodecManagerImpl extends AbstractTypeSpecificComponentManager<Codec> implements CodecManager {
    @Override
    public Class<? extends Codec> getComponentType() { return Codec.class; }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> getComponent(Class<T> type) { return (Codec<T>)this.findComponent(type); }
}