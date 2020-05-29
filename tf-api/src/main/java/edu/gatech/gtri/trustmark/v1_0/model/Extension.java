package edu.gatech.gtri.trustmark.v1_0.model;

import java.util.List;

/**
 * A representation of an Extension point in the Trustmark Framework.  Note that for XML, this is simply equivalent to
 * xsd:any, and hence you would expect the data to contain a list of {@link org.w3c.dom.Element} objects for the
 * extension.  Note, however, that this is implementation dependent, and instead it could be a String, DOM4J or JDOM
 * node.  At this level, we don't really care.
 */
public interface Extension {

	public List<Object> getData();

}//end Extension