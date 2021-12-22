package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

public interface JsonDeserializer<T1> {

    T1 deserialize(String string) throws ParseException;
}
