package com.pyxis.matchers.spring;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.*;

public class HasAttribute extends FeatureMatcher<Model, Object> {
    private final String key;

    public HasAttribute(String key, Matcher<? super Object> valueMatcher) {
        super(valueMatcher, "a model with \"" + key + "\"", "\"" + key + "\"");
        this.key = key;
    }

    @Override protected Object featureValueOf(Model actual) {
        return actual.asMap().get(key);
    }

    @Factory
    public static Matcher<? super Model> hasAttribute(String key, Object value) {
        return containsAttribute(key, equalTo(value));
    }

    @Factory
    public static Matcher<? super Model> containsAttribute(String key, Matcher<? super Object> valueMatcher) {
        return new HasAttribute(key, valueMatcher);
    }

    public static Matcher<? super Model> containsAttribute(String key) {
        return new HasAttribute(key, not(nullValue()));
    }
}
