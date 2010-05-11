package com.pyxis.matchers.spring;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.ui.Model;

public class SpringMatchers {

    private SpringMatchers() {}

    /**
     * Checks that a Web MVC {@link org.springframework.ui.Model} contains a certain
     * key-value pair. 
     * @param key the key under which the value to check is stored in the model.
     * @param value expected value stored in the model.
     */
    public static <T> Matcher<? super Model> hasAttribute(final String key, T value) {
        return HasAttribute.hasAttribute(key, value);
    }

    /**
     * Checks that a Web MVC {@link org.springframework.ui.Model} contains a certain
     * key-value pair. The value should be matched by given matcher. 
     * @param key the key under which the value to check is stored in the model.
     * @param valueMatcher matcher used to check the value stored in the model.
     */
    public static <T> Matcher<? super Model> hasAttributeValue(final String key, Matcher<? super T> valueMatcher) {
        return HasAttribute.hasAttributeValue(key, valueMatcher);
    }

    /**
     * Checks that a Web MVC {@link org.springframework.ui.Model} contains some
     * non-null value stored under a specific key.
     * @param key the key under which a non-null value should be stored in the model.
     */
    public static Matcher<? super Model> containsAttribute(final String key) {
        return HasAttribute.containsAttribute(key);
    }

    /**
     * Checks that a string indicates a Web MVC redirection to a given location. 
     * @param location expected location of the redirection. 
     */
    public static Matcher<? super String> isRedirectedTo(String location) {
        return Matchers.equalTo("redirect:" + location);
    }
}
