package com.pyxis.matchers.core;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * A collection of hamcrest matchers to be used with plain java objects. 
 */
public class CoreMatchers {

    /**
     * Checks that a string is empty or contains only blank characters.
     */
    public static Matcher<String> isBlank() {
        return IsBlankString.isBlank();
	}

    /**
     * Checks that a string is equal to the string representation of an expected value.
     * @param expected the object to compare against.
     */
    public static Matcher<String> being(Object expected) {
        return new IsEqual<String>(String.valueOf(expected));
    }

}
