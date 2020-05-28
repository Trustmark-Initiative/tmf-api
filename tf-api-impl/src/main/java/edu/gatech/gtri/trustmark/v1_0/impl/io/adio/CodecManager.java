package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs.Codec;
import edu.gatech.gtri.trustmark.v1_0.util.TypeSpecificComponentManager;

/**
 * Created by Nicholas on 01/25/2017.
 */
public interface CodecManager extends TypeSpecificComponentManager<Codec> {
    
    @Override
    public <T> Codec<T> getComponent(Class<T> type);
    
}
