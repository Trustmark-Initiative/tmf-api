package edu.gatech.gtri.trustmark.v1_0.io.xml;

/**
 * Manages serialization for XML objects.  This is here so you can tie in and specify how the system can serialize your
 * custom XML objects.
 */
public interface XmlManager {


    /**
     * Finds the producer responsible for the given complex object type.
     */
    public XmlProducer findProducer(Class type);

    /**
     * A simple way (around the ServiceLoader based way) of registering a custom {@link XmlProducer} in the system.
     * Note that any producer registered this way will take precedence over a ServiceLoader loaded instance.
     */
    public void register(XmlProducer producer);

    /**
     * Provided to allow you to remove a XmlProducer.  After this call, NO producer will be registered (even the default).
     */
    public void unregister(XmlProducer producer);


    /**
     * A method which will re-parse and reload the default Xml Producer configuration.
     */
    public void reloadDefaults();


}
