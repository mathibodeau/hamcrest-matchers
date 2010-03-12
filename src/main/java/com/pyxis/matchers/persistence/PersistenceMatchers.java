package com.pyxis.matchers.persistence;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.anything;

public class PersistenceMatchers {

    private PersistenceMatchers() {}

    public static <T> Matcher<T> hasField(String field, Matcher value) {
        return HasFieldWithValue.hasField(field, value);
    }

    public static <T> Matcher<T> hasField(String field) {
        return HasFieldWithValue.hasField(field, anything());
    }

    public static <T> Matcher<T> componentEqualTo(T component) {
        return IsComponentEqual.componentEqualTo(component);
    }

    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return SamePersistentFieldsAs.samePersistentFieldsAs(entity);
    }
}
