package com.pyxis.matchers.core;

import static org.hamcrest.Matchers.anything;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * A collection of hamcrest matchers to be used with plain java objects. 
 */
public class CoreMatchers {

	/**
	 * Checks that an object has a field with a value that is matched by given matcher. 
	 * The field does not have to be public or to have a visible accessor.  No method 
	 * of the object will get called. 
	 * @param field the name of a field
	 * @param value a matcher to check for an expected value.
	 */
    public static <T> Matcher<T> hasField(String field, Matcher<T> value) {
        return HasFieldWithValue.hasField(field, value);
    }

	/**
	 * Checks that an object has a field with a specific value. 
	 * The field does not have to be public or to have a visible accessor.  
	 * No method of the object will get called. 
	 * @param field the name of a field
	 * @param value the expected value of the field.
	 */
    public static <T> Matcher<T> hasField(String field) {
        return HasFieldWithValue.hasField(field, anything());
    }

    /**
     * Checks that a string is empty or contains only blank characters.
     */
    public static Matcher<String> isBlank() {
        return IsBlankString.isBlank();
	}

    /**
     * Checks that a string is equal to some expected value.
     * @param expected expected string value.
     */
    public static Matcher<String> being(Object expected) {
        return new IsEqual<String>(String.valueOf(expected));
    }

}
