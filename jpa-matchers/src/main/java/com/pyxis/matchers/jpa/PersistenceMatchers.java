package com.pyxis.matchers.jpa;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.anything;

/**
 * A collection of hamcrest matchers to be used in assertions validating 
 * field values of persistent objects. 
 */
public class PersistenceMatchers {

    private PersistenceMatchers() {}

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
     */
    public static <T> Matcher<T> hasField(String field) {
        return HasFieldWithValue.hasField(field, anything());
    }

    /**
     * Checks that a component (aka value object) is equal to another
     * given component.  A null component is considered to be equal to
     * a component with only null persistent field values. Persistent fields
     * of a component are fields that are neither static or transient. 
     * @param component a component with expected state.
     */
    public static <T> Matcher<T> componentEqualTo(T component) {
        return IsComponentEqual.componentEqualTo(component);
    }

    /**
     * Checks that an entity has the same persistent field values
     * than another given entity. Persistent fields of an entity are
     * fields that are neither static or transient.
     * @param entity an entity with expected state. 
     */
    public static <T> Matcher<T> samePersistentFieldsAs(T entity) {
        return SamePersistentFieldsAs.samePersistentFieldsAs(entity);
    }
}
