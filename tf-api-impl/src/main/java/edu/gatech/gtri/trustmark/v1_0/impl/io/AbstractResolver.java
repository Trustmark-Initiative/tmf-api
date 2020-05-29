package edu.gatech.gtri.trustmark.v1_0.impl.io;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by brad on 12/9/15.
 */
public abstract class AbstractResolver {

    private static final Logger log = Logger.getLogger(AbstractResolver.class);


    /*
     * These two were for an interesting heuristic based approach at determining if the data is JSON or XML.
     */
//    private String collectNonWhitespaceChars(String incoming, int amount){
//        StringWriter stringWriter = new StringWriter();
//        int charCount = 0;
//        int index = 0;
//        while( charCount < amount && index < incoming.length() ){
//            char next = incoming.charAt(index++);
//            if( !Character.isWhitespace(next) )
//                stringWriter.append(next);
//        }
//        return stringWriter.toString();
//    }//end collectNonWhitespaceChars
//
//    private Map<Character, Integer> countCharTypes(String string, List charTypes ){
//        Map<Character, Integer> counts = new HashMap<>();
//
//        List<Character> characterList = (List<Character>) charTypes; // Damn java sucks
//
//        // Initialize the counts.
//        for( int i = 0; i < charTypes.size(); i++ ){
//            counts.put(characterList.get(i), 0);
//        }
//
//        for( int i = 0; i < string.length(); i++ ){
//            Character nextChar = string.charAt(i);
//            if( charTypes.contains(nextChar) ){
//                Integer currentCount = counts.get(nextChar);
//                Integer nextCount = currentCount + 1;
//                counts.put(nextChar, nextCount);
//            }
//        }
//
//        return counts;
//    }//end charTypes()


    /**
     * A relatively simple JSON resolution function.  Simply checks the first letter as valid JSON or not.
     */
    public boolean isJson(String potentialJson) {
        for( int i = 0; i < potentialJson.length(); i++ ){
            char nextChar = potentialJson.charAt(i);
            if( Character.isWhitespace(nextChar) ){
                continue;
            }else{
                return nextChar == '{' || nextChar == '[';
            }
        }
        return false;
    }//end isJson

    /**
     * Simple function to check for XML data.  Merely looks for a '<' at the first non-whitespace character location.
     */
    public boolean isXml(String potentialXml) {
        for( int i = 0; i < potentialXml.length(); i++ ){
            char nextChar = potentialXml.charAt(i);
            if( Character.isWhitespace(nextChar) ){
                continue;
            }else{
                return nextChar == '<';
            }
        }
        return false;
    }//end isXml()


}
