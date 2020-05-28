package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import java.util.List;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1 {
    public String nonCdataValueNode1;
    public String nonCdataValueNode2;
    public String cdataValueNode1;
    public String cdataValueNode2;
    public ElementNode1 elementNode1;
    public ElementNode2 elementNode2;
    public List<SequenceChild1> sequenceChild1List;
    
    public static class ElementNode1 {
        public String childNonCdataValueNode1;
        public String childNonCdataValueNode2;
        public String childCdataValueNode1;
        public String childCdataValueNode2;
    }
    
    public static class ElementNode2 {
        public String identifier;
        public String childNonCdataValueNode3;
        public String childNonCdataValueNode4;
        public String childCdataValueNode3;
        public String childCdataValueNode4;
    }
    
    public static class SequenceChild1 {
        public String childNonCdataValueNode5;
        public ElementNode2 elementNode2;
    }
}
