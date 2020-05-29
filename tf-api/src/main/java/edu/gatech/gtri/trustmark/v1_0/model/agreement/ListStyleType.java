package edu.gatech.gtri.trustmark.v1_0.model.agreement;

/**
 * The type of styled marker to use for a list.
 *
 * @author Nicholas Saney
 */
public enum ListStyleType {
    UNORDERED_NO_BULLET(false, "none"),
    UNORDERED_CIRCLE_FILLED(false, "disc"),
    UNORDERED_CIRCLE_HOLLOW(false, "circle"),
    UNORDERED_SQUARE_FILLED(false, "square"),
    ORDERED_ARABIC_NUMERALS(true, "decimal"),
    ORDERED_ROMAN_NUMERALS_UPPERCASE(true, "upper-roman"),
    ORDERED_ROMAN_NUMERALS_LOWERCASE(true, "lower-roman"),
    ORDERED_ALPHA_UPPERCASE(true, "upper-alpha"),
    ORDERED_ALPHA_LOWERCASE(true, "lower-alpha");
    
    public final boolean IS_ORDERED;
    public final String CSS_VALUE;
    
    ListStyleType(boolean _isOrdered, String _cssValue) {
        this.IS_ORDERED = _isOrdered;
        this.CSS_VALUE = _cssValue;
    }
}
