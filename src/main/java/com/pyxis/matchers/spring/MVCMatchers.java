package com.pyxis.matchers.spring;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.ui.Model;

public class MVCMatchers {

    private MVCMatchers() {}

    public static Matcher<? super Model> hasAttribute(final String key, Object value) {
        return HasAttribute.hasAttribute(key, value);
    }

    public static Matcher<? super Model> containsAttribute(final String key) {
        return HasAttribute.containsAttribute(key);
    }

    public static Matcher<? super String> isRedirectedTo(String location) {
        return Matchers.equalTo("redirect:" + location);
    }
}
