package com.pyxis.matchers.spring;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;

public class SpringMatchers {

    private SpringMatchers() {}

    public static Matcher<Map<? extends String, ?>> hasAttribute(String key, Object value) {
        return hasEntry(key, value);
    }

    public static Matcher hasAttribute(String key, Matcher<?> value) {
        return hasEntry(Matchers.equalTo(key), value);
    }
}
