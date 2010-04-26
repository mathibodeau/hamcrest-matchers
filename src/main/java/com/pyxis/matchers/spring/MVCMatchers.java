package com.pyxis.matchers.spring;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.ui.Model;

public class MVCMatchers {

    private MVCMatchers() {}

    public static <T> Matcher<? super Model> hasAttribute(final String key, T value) {
        return HasAttribute.hasAttribute(key, value);
    }

    public static <T> Matcher<? super Model> hasAttributeValue(final String key, Matcher<? super T> valueMatcher) {
        return HasAttribute.hasAttributeValue(key, valueMatcher);
    }

    public static Matcher<? super Model> containsAttribute(final String key) {
        return HasAttribute.containsAttribute(key);
    }

    public static Matcher<? super String> isRedirectedTo(String location) {
        return Matchers.equalTo("redirect:" + location);
    }
}
